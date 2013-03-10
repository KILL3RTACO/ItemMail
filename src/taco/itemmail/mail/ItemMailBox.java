package taco.itemmail.mail;

import org.bukkit.entity.Player;

import taco.itemmail.AbstractMailBox;
import taco.tacoapi.TacoAPI;
import taco.tacoapi.database.DatabaseException;
import taco.tacoapi.database.QueryResults;

/**
 * Represents an mailbox. This particular mailbox holds ItemMail. Just like an
 * {@link AbstractMailBox AbstractMailBox}, it is reloaded automatically.
 * @author KILL3RTACO
 * @see AbstractMailBox
 *
 */
public class ItemMailBox extends AbstractMailBox<ItemMail> {

	public ItemMailBox(Player owner) {
		super(owner, "ItemMail");
	}

	@Override
	public void reload() {
		unopened.clear();
		String sql = "SELECT `id` FROM `im_data` WHERE `receiver` = ? AND `type` = ? AND `read` = ? LIMIT 100";
		QueryResults query = TacoAPI.getDB().read(sql, owner.getName(), "mail", 0);
		if(query != null && query.hasRows()){
			for(int i=0; i<query.rowCount(); i++){
				try {
					int id = query.getInteger(i, "id");
					ItemMail im = new ItemMail(id);
					unopened.add(im);
				} catch (DatabaseException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
