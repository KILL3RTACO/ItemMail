package com.kill3rtaco.itemmail.cmds.im;

import org.bukkit.entity.Player;

import com.kill3rtaco.itemmail.ItemMailMain;
import com.kill3rtaco.itemmail.Permission;
import com.kill3rtaco.itemmail.mail.ItemMailBox;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class MailBoxCommand extends TacoCommand {

	public MailBoxCommand() {
		super("mb", new String[]{"mailbox"}, "[page]", "View your mailbox", Permission.VIEW_MAILBOX);
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		ItemMailBox mb = new ItemMailBox(player);
		int page = 0;
		if(args.length == 0){
			page = 1;
		}else{
			if(TacoAPI.getChatUtils().isNum(args[0])){
				 page = Integer.parseInt(args[0]);
			}else{
				ItemMailMain.plugin.chat.sendPlayerMessage(player, "&e" + args[0] + " &cis not a valid number");
				return;
			}
		}
		mb.showElementsAtPage(page);
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
