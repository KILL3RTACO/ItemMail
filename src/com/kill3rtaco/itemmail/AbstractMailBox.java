package com.kill3rtaco.itemmail;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.entity.Player;

import com.kill3rtaco.itemmail.mail.ItemMail;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.util.ItemUtils.DisplayName;
import com.kill3rtaco.tacoapi.util.PageBuilder;

/**
 * Represents a mailbox. AbstractMailBoxes are reloaded automatically whenever they are accessed. It is
 * possible to iterate through all the mail it holds using an advanced for-loop.
 * @author KILL3RTACO
 *
 * @param <M> The type of mail to hold
 */
public abstract class AbstractMailBox<M extends AbstractMail> implements Iterable<M> {

	protected ArrayList<M> unopened;
	protected Player owner;
	protected String type, format;
	
	public AbstractMailBox(Player owner, String type, String format){
		this.owner = owner;
		this.unopened = new ArrayList<M>();
		this.type = type;
		this.format = format;
		reload();
	}
	
	public abstract void reload();
	
	/**
	 * Get a specific item in this mailbox
	 * @param index The index to use
	 * @return The element found, if it was found.
	 */
	public M get(int index){
		reload();
		if(index >= 0 && index < unopened.size()){
			return unopened.get(index);
		}else{
			return null;
		}
	}
	
	/**
	 * Shows the contents of this mailbox at a desired page
	 * @param page The page to show
	 */
	public void showElementsAtPage(int page){
		reload();
		PageBuilder mail = new PageBuilder("&9" + type + "Box", "&1");
		int count = 1;
		for(M m : unopened){
			String display = "";
			if(m instanceof ItemMail){
				ItemMail im = (ItemMail) m;
				if(im.getItemDisplayName() != null && im.getItemDisplayName().length() > 0){
					display += "&2&o" + im.getItemDisplayName() + "&r";
					DisplayName dn = DisplayName.getDisplayName(im.getItemTypeId(), im.getItemDamage());
					if(dn != null) display += " &a(&2" + dn.getName() + "&a)";
					else display += " &a(&2" + TacoAPI.getChatUtils().toProperCase(im.getItems().getType().name().replaceAll("_", " ")) + "&a)";
				}else{
					DisplayName dn = DisplayName.getDisplayName(im.getItemTypeId(), im.getItemDamage());
					if(dn != null) display += " &a(&2" + dn.getName() + "&a)";
					else display += TacoAPI.getChatUtils().toProperCase(im.getItems().getType().name().replaceAll("_", " "));
				}
			}else{
				DisplayName dn = DisplayName.getDisplayName(m.getItemTypeId(), m.getItemDamage());
				if(dn != null) display += dn.getName();
				else display += TacoAPI.getChatUtils().toProperCase(m.getItems().getType().name().replaceAll("_", " "));
			}
//			String line = "&7[&9" + (count++) + "&7] &6From&7: &2" + m.getSender() + " &6Contents&7: &2" + m.getItemAmount() + " " + display;
			String line = format
					.replaceAll("%id", "" + count++)
					.replaceAll("%from", m.getSender())
					.replaceAll("%items", m.getItemAmount() + " " + display);
			mail.append(line);
		}
		if(mail.hasNoPages()){
			ItemMailMain.plugin.chat.sendPlayerMessage(owner, "&cYour inbox is empty");
			return;
		}
		if(mail.hasPage(page)){
			mail.showPage(owner, page);
		}else{
			ItemMailMain.plugin.chat.sendPlayerMessage(owner, "&cPage &e" + page + " &cdoesn't exist");
		}
	}
	
	/**
	 * Gets the amount of unread mail in this mailbox
	 * @return The amount of unread mail
	 */
	public int getUnreadCount(){
		return unopened.size();
	}

	@Override
	public Iterator<M> iterator() {
		reload();
		return unopened.iterator();
	}
	
	/**
	 * Tests whether this mailbox is empty or not
	 * @return true if empty
	 */
	public boolean isEmpty(){
		return unopened.size() == 0;
	}
	
	public void delete(int index){
		M mail = get(index);
		String sql = "UPDATE `itemmail` SET `read`=? WHERE `id`=? LIMIT 1";
		TacoAPI.getDB().write(sql, 1, mail.getMailId());
	}
	
	public void deleteAll(){
		for(M mail : unopened){
			String sql = "UPDATE `itemmail` SET `read`=? WHERE `id`=? LIMIT 1";
			TacoAPI.getDB().write(sql, 1, mail.getMailId());
		}
	}
	
}
