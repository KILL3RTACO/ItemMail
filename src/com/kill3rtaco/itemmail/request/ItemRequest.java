package com.kill3rtaco.itemmail.request;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kill3rtaco.itemmail.AbstractMail;
import com.kill3rtaco.itemmail.ItemMailMain;
import com.kill3rtaco.itemmail.cmds.im.SendCommand;
import com.kill3rtaco.itemmail.event.request.ItemRequestAcceptedEvent;
import com.kill3rtaco.itemmail.event.request.ItemRequestDeclinedEvent;
import com.kill3rtaco.itemmail.event.request.ItemRequestSentEvent;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.serialization.SingleItemSerialization;
import com.kill3rtaco.tacoapi.database.DatabaseException;

public class ItemRequest extends AbstractMail {

	public ItemRequest(int id) throws DatabaseException {
		super(id);
	}
	
	public ItemRequest(String sender, String receiver, ItemStack items){
		super(sender, receiver, items);
		this.id = -1;
		this.sent = null;
	}
	
	public void accept(){
		ItemRequestAcceptedEvent event = new ItemRequestAcceptedEvent(this);
		ItemMailMain.plugin.getServer().getPluginManager().callEvent(event);
		if(!event.isCancelled()){
			String sql = "UPDATE `itemmail` SET `read`=? WHERE `id`=?";
			TacoAPI.getDB().write(sql, 1, id);
			String pseudoArgs = "" + sender + " " + getItemTypeId() + ":" + getItemDamage() + " " + getItemAmount();
			Player player = ItemMailMain.plugin.getServer().getPlayer(receiver);
			new SendCommand().onPlayerCommand(player, pseudoArgs.split(" "));
		}
	}
	
	public void decline(){
		ItemRequestDeclinedEvent event = new ItemRequestDeclinedEvent(this);
		ItemMailMain.plugin.getServer().getPluginManager().callEvent(event);
		if(!event.isCancelled()){
			String sql = "UPDATE `itemmail` SET `read`=? WHERE `id`=?";
			TacoAPI.getDB().write(sql, 1, id);
		}
	}
	
	public void send(){
		ItemRequestSentEvent event = new ItemRequestSentEvent(this);
		if(!event.isCancelled()){
			String sql = "INSERT INTO `itemmail` (`sender`, `receiver`, `item`, `type`, `notes`) VALUES (?, ?, ?, ?, ?)";
			TacoAPI.getDB().write(sql, sender, receiver, SingleItemSerialization.serializeItemAsString(items), "request", "");
		}
	}

}
