package com.kill3rtaco.itemmail;

import java.sql.Timestamp;

import org.bukkit.inventory.ItemStack;

import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.serialization.SingleItemSerialization;
import com.kill3rtaco.tacoapi.database.DatabaseException;
import com.kill3rtaco.tacoapi.database.QueryResults;
import com.kill3rtaco.tacoapi.json.JSONException;
import com.kill3rtaco.tacoapi.json.JSONObject;

/**
 * Represents an abstract version of a mail type object.
 * @author KILL3RTACO
 *
 */
public abstract class AbstractMail {

	protected int id;
	protected String sender, receiver;
	protected ItemStack items;
	protected Timestamp sent;
	protected JSONObject rawData;
	
	/**
	 * Constructs an AbstractMail object using an id as a parameter. This is the id used to specify this specific
	 * AbstractMail. This constructor loads the sender and receiver, as well as the item id, damage and amount and
	 * the time it was sent
	 * @param id the ID to load from
	 * @throws DatabaseException 
	 * @throws SQLException
	 * @see {@link com.kill3rtaco.itemmail.mail.ItemMail ItemMail}
	 */
	public AbstractMail(int id) throws DatabaseException {
		String sql = "SELECT * FROM `itemmail` WHERE `id`=? LIMIT 1";
		QueryResults query = TacoAPI.getDB().read(sql, id);
		if(query != null && query.hasRows()){
			this.id = id;
			this.sender = query.getString(0, "sender");
			this.receiver = query.getString(0, "receiver");
			try {
				this.rawData = new JSONObject(query.getString(0, "item"));
				this.items = SingleItemSerialization.getItem(rawData);
			} catch (JSONException e) {
				e.printStackTrace();
				this.items = null;
			}
			this.sent = query.getTimestamp(0, "time");
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
	public AbstractMail(String sender, String receiver, ItemStack items){
		this.id = -1;
		this.sender = sender;
		this.receiver = receiver;
		this.items = items;
		this.sent = null;
		this.rawData = null;
	}
	
	public abstract void send();
	
	/**
	 * Gets the MailID of this object
	 * @return The MailID
	 */
	public int getMailId(){
		return id;
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
		return items;
	}
	
	/**
	 * Gets the item id associated with this object.
	 * @return The item id
	 */
	public int getItemTypeId(){
		return items.getTypeId();
	}
	
	/**
	 * Gets the item damage associated with this object
	 * @return the item damage/durability
	 */
	public int getItemDamage(){
		return items.getDurability();
	}
	
	/**
	 * Gets the amount of items associated with this object
	 * @return The amount of items
	 */
	public int getItemAmount(){
		return items.getAmount();
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
	
	/**
	 * Get raw data
	 * @return The raw JSONObject data
	 */
	public JSONObject getRawData(){
		return rawData;
	}
	
}
