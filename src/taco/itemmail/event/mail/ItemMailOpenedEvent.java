package taco.itemmail.event.mail;

import org.bukkit.entity.Player;

import taco.itemmail.ItemMailMain;
import taco.itemmail.event.AbstractMailEvent;
import taco.itemmail.mail.ItemMail;

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
