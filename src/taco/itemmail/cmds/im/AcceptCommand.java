package taco.itemmail.cmds.im;

import org.bukkit.entity.Player;

import taco.itemmail.ItemMailMain;
import taco.itemmail.Permission;
import taco.itemmail.request.ItemRequest;
import taco.itemmail.request.ItemRequestBox;
import taco.tacoapi.TacoAPI;
import taco.tacoapi.api.TacoCommand;

public class AcceptCommand extends TacoCommand {

	public AcceptCommand() {
		super("accept", new String[]{}, "[id]", "Accept ItemRequests", Permission.ACCEPT_REQUEST);
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
				ItemMailMain.plugin.chat.sendPlayerMessage(player, "&e" + args[0] + " &cis not a valid number");
				return;
			}
		}
		ItemRequest request = rb.get(id - 1);
		if(request == null){
			ItemMailMain.plugin.chat.sendPlayerMessage(player, "&cThat request doesn't exist");
			return;
		}
		request.accept();
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
