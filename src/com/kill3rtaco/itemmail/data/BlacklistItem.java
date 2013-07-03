package com.kill3rtaco.itemmail.data;

public class BlacklistItem {

	private int id, itemId, itemDamage;
	private String alias, blacklister;
	
	public BlacklistItem(int id, int itemId, int itemDamage, String alias, String blacklister) {
		this.id = id;
		this.itemId = itemId;
		this.itemDamage = itemDamage;
		this.alias = alias;
		this.blacklister = blacklister;
	}
	
	public int getId(){
		return id;
	}
	
	public int getItemId(){
		return itemId;
	}
	
	public int getItemDamage(){
		return itemDamage;
	}
	
	public String getAlias(){
		return alias;
	}
	
	public String getBlacklister(){
		return blacklister;
	}

}
