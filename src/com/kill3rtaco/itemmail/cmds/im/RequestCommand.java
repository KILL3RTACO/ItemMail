package com.kill3rtaco.itemmail.cmds.im;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kill3rtaco.itemmail.ItemMailMain;
import com.kill3rtaco.itemmail.Permission;
import com.kill3rtaco.itemmail.request.ItemRequest;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommand;

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
		if(items.getTypeId() == 0){
			ItemMailMain.plugin.chat.sendPlayerMessage(player, "&cYou cannot ask for air");
			return;
		}
		//comment out when testing
		if(player.getName().equalsIgnoreCase(receiver)){
			ItemMailMain.plugin.chat.sendPlayerMessage(player, "&cYou cannot send ItemMail to yourself");
			return;
		}
		int id = items.getTypeId(), damage = items.getDurability();
		amount = items.getAmount();
		ItemRequest request = new ItemRequest(player.getName(), receiver, id, damage, amount);
		request.send();
		String friendlyName = ItemMailMain.plugin.getDisplayString(new ItemStack(id, amount, (short) damage));
		ItemMailMain.plugin.chat.sendPlayerMessage(player, "&aYou requested &2" + receiver + " &ato send you &2" + amount + " " + friendlyName);
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
