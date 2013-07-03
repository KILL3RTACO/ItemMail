package com.kill3rtaco.itemmail.cmds.im;

import org.bukkit.entity.Player;

import com.kill3rtaco.itemmail.ItemMailMain;
import com.kill3rtaco.itemmail.Permission;
import com.kill3rtaco.itemmail.mail.ItemMail;
import com.kill3rtaco.itemmail.mail.ItemMailBox;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class DeleteCommand extends TacoCommand {

	public DeleteCommand() {
		super("delete", new String[]{"del", "rm", "d"}, "[id/*]", "Delete ItemMail", Permission.DELETE_MAIL);
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		ItemMailBox mb = new ItemMailBox(player);
		int id = mb.getUnreadCount();
		if(mb.isEmpty()){
			ItemMailMain.plugin.chat.sendPlayerMessage(player, "&cYour ItemMailBox is empty");
			return;
		}
		if(args.length > 0){
			if(TacoAPI.getChatUtils().isNum(args[0])){
				id = Integer.parseInt(args[0]);
			}else{
				if(args[0].equalsIgnoreCase("*")){
					int count = mb.getUnreadCount();
					mb.deleteAll();
					ItemMailMain.plugin.chat.sendPlayerMessage(player,
							"&2" + count + " &aItemMail deleted");
					return;
				}
				ItemMailMain.plugin.chat.sendPlayerMessage(player, "&e" + args[0] + " &cis not a valid number");
				return;
			}
		}
		ItemMail im = mb.get(id - 1);
		if(im == null){
			ItemMailMain.plugin.chat.sendPlayerMessage(player, "&cThat mail doesn't exist");
			return;
		}
		mb.delete(id - 1);
		ItemMailMain.plugin.chat.sendPlayerMessage(player, "&21 &aItemMail deleted");
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
