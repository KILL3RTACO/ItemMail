package com.kill3rtaco.itemmail.data;

import java.util.ArrayList;

import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.database.Database;
import com.kill3rtaco.tacoapi.database.DatabaseException;
import com.kill3rtaco.tacoapi.database.QueryResults;

public class ItemMailBlacklist {
	
	private Database data = TacoAPI.getDB();
	private ArrayList<BlacklistItem> items;
	
	public ItemMailBlacklist(){
		createTables();
		loadBlacklist();
	}
	
	private void createTables(){
		String sql = "CREATE TABLE IF NOT EXISTS `im_blacklist` (`id` INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
				"`item_id` INT(10) NOT NULL, `item_damage` INT(10) NOT NULL, " +
				"`alias` VARCHAR(30) UNIQUE NOT NULL, `blacklister` VARCHAR(20) NOT NULL)";
		data.write(sql);
	}
	
	private void loadBlacklist(){
		items = new ArrayList<BlacklistItem>();
		String sql = "SELECT * FROM `im_blacklist`";
		QueryResults query = data.read(sql);
		try {
			for(int i=0; i<query.rowCount(); i++){
				int id = query.getInteger(i, "id");
				int itemId = query.getInteger(i, "item_id");
				int itemDamage = query.getInteger(i, "item_damage");
				String alias = query.getString(i, "alias");
				String blacklister = query.getString(i, "blacklister");
				items.add(new BlacklistItem(id, itemId, itemDamage, alias, blacklister));
			}
		} catch (DatabaseException e){
			e.printStackTrace();
		}
	}
	
	public BlacklistItem getBlacklistItem(String alias){
		for(BlacklistItem bi : items){
			if(bi.getAlias().equalsIgnoreCase(alias)){
				return bi;
			}
		}
		return null;
	}

}
