package taco.itemmail;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import taco.itemmail.event.mail.ItemMailSentEvent;
import taco.itemmail.mail.ItemMailBox;

public class ItemMailListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		String message;
		ItemMailBox mb = new ItemMailBox(event.getPlayer());
		int unread = mb.getUnreadCount();
		if(unread == 0)	message = "&aYour inbox is empty";
		else message = "&aYou have &2" + unread + " &aunopened ItemMail";
		ItemMailMain.plugin.chat.sendPlayerMessage(event.getPlayer(), message);
		//TODO requestbox equivalent
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onItemMailSend(ItemMailSentEvent event){
		Player receiver = ItemMailMain.plugin.getServer().getPlayer(event.getMail().getReceiver());
		if(receiver != null && receiver.isOnline()){
			ItemMailMain.plugin.chat.sendPlayerMessage(receiver, "&aYou have new ItemMail");
		}
	}
	
}
