package taco.itemmail;

import java.sql.Timestamp;

import org.bukkit.inventory.ItemStack;

import taco.tacoapi.TacoAPI;
import taco.tacoapi.database.DatabaseException;
import taco.tacoapi.database.QueryResults;

/**
 * Represents an abstract version of a mail type object.
 * @author KILL3RTACO
 *
 */
public abstract class AbstractMail {

	protected String sender, receiver;
	protected int mailId, itemId, itemDamage, itemAmount;
	protected Timestamp sent;
	
	/**
	 * Constructs an AbstractMail object using an id as a parameter. This is the id used to specify this specific
	 * AbstractMail. This constructor loads the sender and receiver, as well as the item id, damage and amount and
	 * the time it was sent
	 * @param id the ID to load from
	 * @throws DatabaseException 
	 * @throws SQLException
	 * @see {@link taco.itemmail.mail.ItemMail ItemMail}
	 */
	public AbstractMail(int id) throws DatabaseException {
		String sql = "SELECT * FROM `im_data` WHERE `id`=? LIMIT 1";
		QueryResults query = TacoAPI.getDB().read(sql, id);
		if(query != null && query.hasRows()){
			mailId = id;
			sender = query.getString(0, "sender");
			receiver = query.getString(0, "receiver");
			itemId = query.getInteger(0, "item_id");
			itemDamage = query.getInteger(0, "item_damage");
			itemAmount = query.getInteger(0, "item_amount");
			sent = query.getTimestamp(0, "time_sent");
		}
	}
	
	/**
	 * Constructs an AbstractMail using the given parameters. This constructor is used for constructing an
	 * AbstractMail that has not been sent but will be later using send().
	 * @param sender The sender
	 * @param receiver The receiver
	 * @param itemId The id of the items
	 * @param itemDamage The damage value (durability) of the items
	 * @param itemAmount The amount of the items
	 */
	public AbstractMail(String sender, String receiver, int itemId, int itemDamage, int itemAmount){
		this.mailId = -1;
		this.sender = sender;
		this.receiver = receiver;
		this.itemId = itemId;
		this.itemDamage = itemDamage;
		this.itemAmount = itemAmount;
		this.sent = null;
	}
	
	public abstract void send();
	
	/**
	 * Gets the MailID of this object
	 * @return The MailID
	 */
	public int getMailId(){
		return mailId;
	}
	
	/**
	 * Gets the sender of this object
	 * @return The sender
	 */
	public String getSender(){
		return sender;
	}
	
	/**
	 * Gets the receiver of this object
	 * @return The receiver
	 */
	public String getReceiver(){
		return receiver;
	}
	
	/**
	 * Gets the ItemStack associated with this object. This is the same as invoking
	 * {@code new ItemStack(getItemTypeId(), getItemDamage(), getItemAmount())}
	 * @return The items associated
	 */
	public ItemStack getItems(){
		return new ItemStack(itemId, itemAmount, (short) itemDamage);
	}
	
	/**
	 * Gets the item id associated with this object.
	 * @return The item id
	 */
	public int getItemTypeId(){
		return itemId;
	}
	
	/**
	 * Gets the item damage associated with this object
	 * @return the item damage/durability
	 */
	public int getItemDamage(){
		return itemDamage;
	}
	
	/**
	 * Gets the amount of items associated with this object
	 * @return The amount of items
	 */
	public int getItemAmount(){
		return itemAmount;
	}
	
	/**
	 * Gets the date this was sent
	 * @return The date sent, if it has been sent
	 */
	public Timestamp getSendDate(){
		return sent;
	}
	
	/**
	 * Get whether this mail has been sent or not
	 * @return true if has been sent, false otherwise
	 */
	public boolean hasBeenSent(){
		return sent != null;
	}
	
}
