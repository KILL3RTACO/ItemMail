package com.kill3rtaco.itemmail.event.request;

import org.bukkit.entity.Player;

import com.kill3rtaco.itemmail.ItemMailMain;
import com.kill3rtaco.itemmail.event.AbstractMailEvent;
import com.kill3rtaco.itemmail.request.ItemRequest;


public class ItemRequestSentEvent extends AbstractMailEvent {

	public ItemRequestSentEvent(ItemRequest mail) {
		super(mail);
	}
	
	public ItemRequest getMail(){
		return (ItemRequest) super.getMail();
	}
	
	public Player getPlayer(){
		return ItemMailMain.plugin.getServer().getPlayer(mail.getSender());
	}

}
