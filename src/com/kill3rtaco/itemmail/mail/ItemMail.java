package com.kill3rtaco.itemmail.mail;

import org.bukkit.inventory.ItemStack;
import com.kill3rtaco.itemmail.AbstractMail;
import com.kill3rtaco.itemmail.ItemMailMain;
import com.kill3rtaco.itemmail.event.mail.ItemMailOpenedEvent;
import com.kill3rtaco.itemmail.event.mail.ItemMailSentEvent;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.serialization.EnchantmentSerialization;
import com.kill3rtaco.tacoapi.api.serialization.SingleItemSerialization;
import com.kill3rtaco.tacoapi.database.DatabaseException;

/**
 * Represents mail sent to a player. An ItemMail object holds all the same values as an AbstractMail object does,
 * along with an enchantment code and custom display name.
 * @author KILL3RTACO
 * @see {@link AbstractMail}
 *
 */
public class ItemMail extends AbstractMail {
	
	public ItemMail(int id) throws DatabaseException {
		super(id);
	}
	
	/**
	 * Gets ItemStack's custom display name, if any. 
	 * @return The ItemStack's display name, if any. Otherwise ""
	 */
	public String getItemDisplayName(){
		if(items == null) return "";
		if(items.getItemMeta() == null || !items.getItemMeta().hasDisplayName()){
			return "";
		}else{
			return items.getItemMeta().getDisplayName();
		}
	}
	
	/**
	 * Gets this mail's display string for its contents. For instance, If the items had a display name of Excalibur
	 * and the item type is DIAMOND_SWORD, then the display would be
	 * <pre>
	 * <code><i>Excalibur</i> (Diamond Sword)</code></pre>
	 * @return
	 */
	public String getItemDisplay(){
		return ItemMailMain.plugin.getDisplayString(items);
	}
	
	/**
	 * Gets the ItemStack's enchantment code, if any.
	 * @return The enchantment code, if any. Otherwise ""
	 */
	public String getEnchantmentCode(){
		if(!hasEnchantments()) return "";
		return EnchantmentSerialization.serializeEnchantments(items.getEnchantments());
	}
	
	public boolean hasEnchantments(){
		return !items.getEnchantments().isEmpty();
	}
	
	public void open(){
		ItemMailOpenedEvent event = new ItemMailOpenedEvent(this);
		ItemMailMain.plugin.getServer().getPluginManager().callEvent(event);
		if(!event.isCancelled()){
			String sql = "UPDATE `itemmail` SET `read`=? WHERE `id`=? LIMIT 1";
			TacoAPI.getDB().write(sql, 1, id);
		}
	}
	
	public void send(){
		ItemMailSentEvent event = new ItemMailSentEvent(this);
		ItemMailMain.plugin.getServer().getPluginManager().callEvent(event);
		if(!event.isCancelled()){
			sendUnsafely(sender, receiver, items, "");
		}
	}
	
	public static void sendUnsafely(String sender, String receiver, ItemStack items){
		sendUnsafely(sender, receiver, items, "Sent via ItemMail.sendUnsafely()");
	}
	
	public static void sendUnsafely(String sender, String receiver, ItemStack items, String notes){
		String sql = "INSERT INTO `itemmail` (`sender`, `receiver`, `item`, `type`, `notes`) VALUES(?, ?, ?, ?, ?)";
		String item = SingleItemSerialization.serializeItemAsString(items);
		TacoAPI.getDB().write(sql, sender, receiver, item, "mail", notes);
	}

}
