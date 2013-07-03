package com.kill3rtaco.itemmail.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.kill3rtaco.itemmail.AbstractMail;


public abstract class AbstractMailEvent extends Event implements Cancellable {

	protected AbstractMail mail;
	private static HandlerList handlers = new HandlerList();
	protected boolean cancelled = false;
	
	public AbstractMailEvent(AbstractMail mail) {
		this.mail = mail;
	}

	public AbstractMail getMail(){
		return mail;
	}
	
	public abstract Player getPlayer();
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList(){
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}

}
