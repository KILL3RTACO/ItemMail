package com.kill3rtaco.itemmail.mail;

import org.bukkit.entity.Player;

import com.kill3rtaco.itemmail.AbstractMailBox;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.database.DatabaseException;
import com.kill3rtaco.tacoapi.database.QueryResults;

/**
 * Represents an mailbox. This particular mailbox holds ItemMail. Just like an
 * {@link AbstractMailBox AbstractMailBox}, it is reloaded automatically.
 * @author KILL3RTACO
 * @see AbstractMailBox
 *
 */
public class ItemMailBox extends AbstractMailBox<ItemMail> {

	public ItemMailBox(Player owner) {
		super(owner, "ItemMail", "&7[&9%id&7] &6From&7: &2%from &6Contents&7: &2%items");
	}

	@Override
	public void reload() {
		unopened.clear();
		String sql = "SELECT `id` FROM `itemmail` WHERE `receiver` = ? AND `type` = ? AND `read` = ? LIMIT 100";
		QueryResults query = TacoAPI.getDB().read(sql, owner.getName(), "mail", 0);
		if(query != null && query.hasRows()){
			for(int i=0; i<query.rowCount(); i++){
				try {
					int id = query.getInteger(i, "id");
					ItemMail im = new ItemMail(id);
					if(im.getItems() == null) continue;
					unopened.add(im);
				} catch (DatabaseException e) {
					e.printStackTrace();
					break;
				}
			}
		}
	}

}
