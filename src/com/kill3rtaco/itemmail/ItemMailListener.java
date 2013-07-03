package com.kill3rtaco.itemmail;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.kill3rtaco.itemmail.event.mail.ItemMailSentEvent;
import com.kill3rtaco.itemmail.event.request.ItemRequestSentEvent;
import com.kill3rtaco.itemmail.mail.ItemMailBox;
import com.kill3rtaco.itemmail.request.ItemRequestBox;


public class ItemMailListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		String message;
		ItemMailBox mb = new ItemMailBox(event.getPlayer());
		int unread = mb.getUnreadCount();
		if(unread == 0)	message = "&aYour mailbox is empty";
		else message = "&aYou have &2" + unread + " &aunopened ItemMail";
		ItemMailMain.plugin.chat.sendPlayerMessage(event.getPlayer(), message);
		ItemRequestBox rb = new ItemRequestBox(event.getPlayer());
		unread = rb.getUnreadCount();
		if(unread == 0) message = "&aYour requestbox is empty";
		else message = "&aYou have &2" + unread + " &aunopened ItemRequest" + (unread > 1 ? "s" : "");
		ItemMailMain.plugin.chat.sendPlayerMessage(event.getPlayer(), message);
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onItemMailSend(ItemMailSentEvent event){
		Player receiver = ItemMailMain.plugin.getServer().getPlayer(event.getMail().getReceiver());
		if(receiver != null && receiver.isOnline()){
			ItemMailMain.plugin.chat.sendPlayerMessage(receiver, "&aYou have new ItemMail! Use &2/im mail-info &ato see what it is");
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onRequestSend(ItemRequestSentEvent event){
		Player receiver = ItemMailMain.plugin.getServer().getPlayer(event.getMail().getReceiver());
		if(receiver != null && receiver.isOnline()){
			ItemMailMain.plugin.chat.sendPlayerMessage(receiver, "&aYou have a new ItemRequest! Use &2/im request-info &ato see what it is");
		}
	}
	
}
