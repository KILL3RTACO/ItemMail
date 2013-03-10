package taco.itemmail.cmds.im;

import org.bukkit.entity.Player;

import taco.itemmail.ItemMailMain;
import taco.itemmail.Permission;
import taco.itemmail.mail.ItemMailBox;
import taco.tacoapi.TacoAPI;
import taco.tacoapi.api.TacoCommand;

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
