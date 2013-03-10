package taco.itemmail.request;

import org.bukkit.entity.Player;

import taco.itemmail.AbstractMailBox;
import taco.tacoapi.TacoAPI;
import taco.tacoapi.database.DatabaseException;
import taco.tacoapi.database.QueryResults;

public class ItemRequestBox extends AbstractMailBox<ItemRequest> {

	public ItemRequestBox(Player owner) {
		super(owner, "ItemRequest");
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
