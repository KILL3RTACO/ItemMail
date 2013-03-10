package taco.itemmail.cmds.im;

import org.bukkit.entity.Player;

import taco.itemmail.ItemMailMain;
import taco.itemmail.Permission;
import taco.itemmail.request.ItemRequestBox;
import taco.tacoapi.TacoAPI;
import taco.tacoapi.api.TacoCommand;

public class RequestBoxCommand extends TacoCommand {

	public RequestBoxCommand() {
		super("rb", new String[]{"requestbox"}, "[page]", "View your ItemRequestBox", Permission.VIEW_REQUESTBOX);
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
