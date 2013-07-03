package com.kill3rtaco.itemmail.mail;

import java.util.HashMap;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.kill3rtaco.itemmail.AbstractMail;
import com.kill3rtaco.itemmail.ItemMailMain;
import com.kill3rtaco.itemmail.event.mail.ItemMailOpenedEvent;
import com.kill3rtaco.itemmail.event.mail.ItemMailSentEvent;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.database.DatabaseException;
import com.kill3rtaco.tacoapi.database.QueryResults;

/**
 * Represents mail sent to a player. An ItemMail object holds all the same values as an AbstractMail object does,
 * along with an enchantment code and custom display name.
 * @author KILL3RTACO
 * @see {@link AbstractMail}
 *
 */
public class ItemMail extends AbstractMail {

	protected String itemName, itemEc;
	
	public ItemMail(int id) throws DatabaseException {
		super(id);
		String sql = "SELECT `item_name`, `item_ec` FROM `im_data` WHERE `id`=? LIMIT 1";
		QueryResults query = TacoAPI.getDB().read(sql, id);
		if(query != null && query.hasRows()){
			itemName = query.getString(0, "item_name");
			itemEc = query.getString(0, "item_ec");
		}
	}
	
	public ItemMail(String sender, String receiver, int itemId, int itemDamage, int itemAmount, String itemName, String itemEc){
		//to from id:damage amount
		super(sender, receiver, itemId, itemDamage, itemAmount);
		this.itemName = itemName;
		this.itemEc = itemEc;
	}
	
	/**
	 * Gets the items associated with this ItemMail. This includes the enchantments denoted by its enchantment code, 
	 * along with a custom display name if one is defined.
	 * @return The items with enchantments and display name included
	 */
	public ItemStack getItems(){
		ItemStack items = super.getItems();
		HashMap<Enchantment, Integer> enchants = ItemMailMain.plugin.getEnchantmentsFromCode(itemEc);
		items.addEnchantments(enchants);
		ItemMeta meta = items.getItemMeta();
		meta.setDisplayName(itemName);
		if(!itemName.equalsIgnoreCase("")) items.setItemMeta(meta);
		return items;
	}
	
	/**
	 * Gets ItemStack's custom display name, if any. 
	 * @return The ItemStack's display name, if any. Otherwise ""
	 */
	public String getItemDisplayName(){
		return itemName;
	}
	
	/**
	 * Gets this mail's display string for its contents. For instance, If the items had a display name of Excalibur
	 * and the item type is DIAMOND_SWORD, then the display would be
	 * <pre>
	 * <code><i>Excalibur</i> (Diamond Sword)</code></pre>
	 * @return
	 */
	public String getItemDisplay(){
		return ItemMailMain.plugin.getDisplayString(getItems());
	}
	
	/**
	 * Gets the ItemStack's enchantment code, if any. This code can be used in parallel with ChestShop.
	 * @return The enchantment code, if any. Otherwise ""
	 */
	public String getEnchantmentCode(){
		return itemEc;
	}
	
	public boolean hasEnchantments(){
		return ItemMailMain.plugin.getEnchantmentsFromCode(itemEc).size() > 0;
	}
	
	public void open(){
		ItemMailOpenedEvent event = new ItemMailOpenedEvent(this);
		ItemMailMain.plugin.getServer().getPluginManager().callEvent(event);
		if(!event.isCancelled()){
			String sql = "UPDATE `im_data` SET `read`=? WHERE `id`=? LIMIT 1";
			TacoAPI.getDB().write(sql, 1, mailId);
		}
	}
	
	public void send(){
		ItemMailSentEvent event = new ItemMailSentEvent(this);
		ItemMailMain.plugin.getServer().getPluginManager().callEvent(event);
		if(!event.isCancelled()){
			String sql = "INSERT INTO `im_data` (`sender`, `receiver`, `item_id`, `item_damage`, `item_amount`," +
					" `item_name`, `item_ec`, `type`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			TacoAPI.getDB().write(sql, sender, receiver, itemId, itemDamage, itemAmount, itemName, itemEc, "mail");
		}
	}

}
