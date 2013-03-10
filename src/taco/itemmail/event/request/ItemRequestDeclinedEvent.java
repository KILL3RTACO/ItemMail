package taco.itemmail.event.request;

import org.bukkit.entity.Player;

import taco.itemmail.ItemMailMain;
import taco.itemmail.event.AbstractMailEvent;
import taco.itemmail.request.ItemRequest;

public class ItemRequestDeclinedEvent extends AbstractMailEvent {

	public ItemRequestDeclinedEvent(ItemRequest mail) {
		super(mail);
	}
	
	public ItemRequest getMail(){
		return (ItemRequest) super.getMail();
	}
	
	public Player getPlayer(){
		return ItemMailMain.plugin.getServer().getPlayer(mail.getReceiver());
	}

}
