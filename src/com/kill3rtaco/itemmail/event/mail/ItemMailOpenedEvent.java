package com.kill3rtaco.itemmail.event.mail;

import org.bukkit.entity.Player;

import com.kill3rtaco.itemmail.ItemMailMain;
import com.kill3rtaco.itemmail.event.AbstractMailEvent;
import com.kill3rtaco.itemmail.mail.ItemMail;


public class ItemMailOpenedEvent extends AbstractMailEvent {

	public ItemMailOpenedEvent(ItemMail mail) {
		super(mail);
	}
	
	public ItemMail getMail(){
		return (ItemMail) super.getMail();
	}
	
	public Player getPlayer(){
		return ItemMailMain.plugin.getServer().getPlayer(mail.getReceiver());
	}

}
