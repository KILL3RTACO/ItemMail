package com.kill3rtaco.itemmail.cmds.im;

import org.bukkit.entity.Player;

import com.kill3rtaco.itemmail.ItemMailConstants;
import com.kill3rtaco.itemmail.ItemMailMain;
import com.kill3rtaco.itemmail.request.ItemRequest;
import com.kill3rtaco.itemmail.request.ItemRequestBox;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class DeclineCommand extends TacoCommand {

	public DeclineCommand() {
		super("decline", new String[]{"dec"}, "[id/*]", "Decline ItemRequests", ItemMailConstants.P_DECLINE_REQUEST);
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		ItemRequestBox rb = new ItemRequestBox(player);
		int id = rb.getUnreadCount();
		if(rb.isEmpty()){
			ItemMailMain.plugin.chat.sendPlayerMessage(player, "&cYour ItemMailBox is empty");
			return;
		}
		if(args.length > 0){
			if(TacoAPI.getChatUtils().isNum(args[0])){
				id = Integer.parseInt(args[0]);
			}else{
				if(args[0].equalsIgnoreCase("*")){
					int count = rb.getUnreadCount();
					rb.deleteAll();
					ItemMailMain.plugin.chat.sendPlayerMessage(player,
							"&2" + count + " &aItemRequest" + (count > 1 ? "s" : "") + " deleted");
					return;
				}
				ItemMailMain.plugin.chat.sendPlayerMessage(player, "&e" + args[0] + " &cis not a valid number");
				return;
			}
		}
		ItemRequest im = rb.get(id - 1);
		if(im == null){
			ItemMailMain.plugin.chat.sendPlayerMessage(player, "&cThat request doesn't exist");
			return;
		}
		rb.delete(id - 1);
		ItemMailMain.plugin.chat.sendPlayerMessage(player, "&21 &aItemRequest deleted");
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
