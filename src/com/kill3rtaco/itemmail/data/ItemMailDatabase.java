package com.kill3rtaco.itemmail.data;

import java.sql.Timestamp;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.kill3rtaco.itemmail.ItemMailMain;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.serialization.EnchantmentSerialization;
import com.kill3rtaco.tacoapi.api.serialization.SingleItemSerialization;
import com.kill3rtaco.tacoapi.database.Database;
import com.kill3rtaco.tacoapi.database.DatabaseException;
import com.kill3rtaco.tacoapi.database.QueryResults;

public class ItemMailDatabase {

	private Database data = TacoAPI.getDB();
	
	public ItemMailDatabase() {
		createTables();
	}
	
	private void createTables(){
		String sql = "CREATE TABLE IF NOT EXISTS `itemmail` (" +
				" `id` INT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY," +
				" `sender` VARCHAR(25) NOT NULL," +
				" `receiver` VARCHAR(25) NOT NULL," +
				" `item` TEXT NOT NULL," +
				" `type` VARCHAR(16) NOT NULL," +
				" `read` INT(1) NOT NULL DEFAULT '0'," +
				" `notes` TEXT NOT NULL," +
				" `time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)" +
				" COMMENT='ItemMail Data Table; New TacoSerialization Used'";
		data.write(sql);
	}
	
	public void convertData(final Player converter){
		new BukkitRunnable(){

			@Override
			public void run() {
				String sql = "SELECT * FROM `im_data` WHERE `read`=?";
				QueryResults query = data.read(sql, 0);
				if(query != null && query.hasRows()){
					try {
						for(int i=0; i<query.rowCount(); i++){
							String sender = query.getString(i, "sender");
							String receiver = query.getString(i, "receiver");
							int id = query.getInteger(i, "item_id");
							int damage = query.getInteger(i, "item_damage");
							int amount = query.getInteger(i, "item_amount");
							String ec = query.getString(i, "item_ec");
							String name = query.getString(i, "item_name");
							String type = query.getString(i, "type");
							Timestamp time = query.getTimestamp(i, "time_sent");
							ItemStack items = new ItemStack(id, amount, (short) damage);
							items.addUnsafeEnchantments(EnchantmentSerialization.convertAndGetEnchantments(ec));
							if(!name.isEmpty()){
								ItemMeta meta = items.getItemMeta();
								meta.setDisplayName(name);
								items.setItemMeta(meta);
							}
							String serializedItems = SingleItemSerialization.serializeItemAsString(items);
							String sql2 = "INSERT INTO `itemmail`" +
									" (`sender`, `receiver`, `item`, `type`, `read`, `notes`, `time`)" +
									" VALUES(?, ?, ?, ?, ?, ?, ?)";
							data.write(sql2, sender, receiver, serializedItems, type, 0, "Converted from old format", time);
							
						}
						if(converter == null){
							ItemMailMain.plugin.chat.out("&aData converted");
						}else{
							ItemMailMain.plugin.chat.sendPlayerMessage(converter, "&aData converted");
						}
					} catch (DatabaseException e){
						if(converter == null){
							ItemMailMain.plugin.chat.out("&cAn error occurred while converting th data");
						}else{
							ItemMailMain.plugin.chat.sendPlayerMessage(converter, "&cAn error occurred while converting th data");
							
						}
						e.printStackTrace();
						return;
					}
				}
			}
			
		}.runTaskAsynchronously(ItemMailMain.plugin);
	}

}
