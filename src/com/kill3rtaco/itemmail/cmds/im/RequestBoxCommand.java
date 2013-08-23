package com.kill3rtaco.itemmail.cmds.im;

import org.bukkit.entity.Player;

import com.kill3rtaco.itemmail.ItemMailConstants;
import com.kill3rtaco.itemmail.ItemMailMain;
import com.kill3rtaco.itemmail.request.ItemRequestBox;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class RequestBoxCommand extends TacoCommand {

	public RequestBoxCommand() {
		super("rb", new String[]{"requestbox"}, "[page]", "View your ItemRequestBox", ItemMailConstants.P_VIEW_REQUESTBOX);
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		ItemRequestBox rb = new ItemRequestBox(player);
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
		rb.showElementsAtPage(page);
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
