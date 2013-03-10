package taco.itemmail.cmds.im;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import taco.itemmail.ItemMailMain;
import taco.itemmail.Permission;
import taco.itemmail.request.ItemRequest;
import taco.tacoapi.TacoAPI;
import taco.tacoapi.api.TacoCommand;

public class RequestCommand extends TacoCommand {

	public RequestCommand() {
		super("request", new String[]{"req"}, "<player> <item[:damage]> [amount]", "Request ItemMail", Permission.SEND_REQUEST);
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(args.length < 2){
			ItemMailMain.plugin.chat.sendPlayerMessage(player, "&cMust at least specify player and item");
			return;
		}
		String receiver = args[0];
		Player p = ItemMailMain.plugin.getServer().getPlayer(receiver);
		if(p != null) receiver = p.getName();
		int amount = 1;
		if(args.length == 3){
			if(TacoAPI.getChatUtils().isNum(args[2])){
				amount = Integer.parseInt(args[2]);
			}else{
				ItemMailMain.plugin.chat.sendPlayerMessage(player, "&e " + args[2] + " &cis not a valid number");
				return;
			}
		}
		ItemStack items = TacoAPI.getItemUtils().createItemStack(args[1], amount);
		if(items == null){
			ItemMailMain.plugin.chat.sendPlayerMessage(player, "&e " + args[1] + " &cnot recognized");
			return;
		}
		int id = items.getTypeId(), damage = items.getDurability();
		amount = items.getAmount();
		ItemRequest request = new ItemRequest(player.getName(), receiver, id, damage, amount);
		request.send();
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
