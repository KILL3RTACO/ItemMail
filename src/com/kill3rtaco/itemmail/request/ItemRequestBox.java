package com.kill3rtaco.itemmail.request;

import org.bukkit.entity.Player;

import com.kill3rtaco.itemmail.AbstractMailBox;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.database.DatabaseException;
import com.kill3rtaco.tacoapi.database.QueryResults;

public class ItemRequestBox extends AbstractMailBox<ItemRequest> {

	public ItemRequestBox(Player owner) {
		super(owner, "ItemRequest","&7[&9%id&7] &6From&7: &2%from &6Requested Items&7: &2%items");
	}

	@Override
	public void reload() {
		unopened.clear();
		String sql = "SELECT `id` FROM `im_data` WHERE `receiver` = ? AND `type` = ? AND `read` = ? LIMIT 100";
		QueryResults query = TacoAPI.getDB().read(sql, owner.getName(), "request", 0);
		if(query != null && query.hasRows()){
			for(int i=0; i<query.rowCount(); i++){
				try {
					int id = query.getInteger(i, "id");
					ItemRequest im = new ItemRequest(id);
					unopened.add(im);
				} catch (DatabaseException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
