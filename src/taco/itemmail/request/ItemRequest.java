package taco.itemmail.request;

import org.bukkit.entity.Player;

import taco.itemmail.AbstractMail;
import taco.itemmail.ItemMailMain;
import taco.itemmail.cmds.im.SendCommand;
import taco.itemmail.event.request.ItemRequestAcceptedEvent;
import taco.itemmail.event.request.ItemRequestDeclinedEvent;
import taco.itemmail.event.request.ItemRequestSentEvent;
import taco.tacoapi.TacoAPI;
import taco.tacoapi.database.DatabaseException;

public class ItemRequest extends AbstractMail {

	public ItemRequest(int id) throws DatabaseException {
		super(id);
	}
	
	public ItemRequest(String sender, String receiver, int itemId, int itemDamage, int itemAmount){
		super(sender, receiver, itemId, itemDamage, itemAmount);
		this.mailId = -1;
		this.sent = null;
	}
	
	public void accept(){
		ItemRequestAcceptedEvent event = new ItemRequestAcceptedEvent(this);
		ItemMailMain.plugin.getServer().getPluginManager().callEvent(event);
		if(!event.isCancelled()){
			String sql = "UPDATE `im_data` SET `read`=? WHERE `id`=?";
			TacoAPI.getDB().write(sql, 1, mailId);
			String pseudoArgs = "" + sender + " " + getItemTypeId() + ":" + getItemDamage() + " " + getItemAmount();
			Player player = ItemMailMain.plugin.getServer().getPlayer(receiver);
			new SendCommand().onPlayerCommand(player, pseudoArgs.split(" "));
		}
	}
	
	public void decline(){
		ItemRequestDeclinedEvent event = new ItemRequestDeclinedEvent(this);
		ItemMailMain.plugin.getServer().getPluginManager().callEvent(event);
		if(!event.isCancelled()){
			String sql = "UPDATE `im_data` SET `read`=? WHERE `id`=?";
			TacoAPI.getDB().write(sql, 1, mailId);
		}
	}
	
	public void send(){
		ItemRequestSentEvent event = new ItemRequestSentEvent(this);
		if(!event.isCancelled()){
			String sql = "INSERT INTO `im_data` (`sender`, `receiver`, `item_id`, `item_damage`, `item_amount`, `type`) VALUES (?, ?, ?, ?, ?, ?)";
			TacoAPI.getDB().write(sql, sender, receiver, itemId, itemDamage, itemAmount, "request");
		}
	}

}
