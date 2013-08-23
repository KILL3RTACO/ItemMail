package com.kill3rtaco.itemmail.cmds.im;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kill3rtaco.itemmail.ItemMailConstants;
import com.kill3rtaco.itemmail.ItemMailMain;
import com.kill3rtaco.itemmail.mail.ItemMail;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class SendCommand extends TacoCommand {

	public SendCommand() {
		super("send", new String[]{}, "<player> [item[:damage]] [amount]", "Send ItemMail", ItemMailConstants.P_SEND_REQUEST);
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(args.length == 0){
			ItemMailMain.plugin.chat.sendPlayerMessage(player, "&cMust at least specify a player to send to");
			return;
		}
		String receiver = args[0];
		Player p = ItemMailMain.plugin.getServer().getPlayer(receiver);
		if(p != null) receiver = p.getName();
		ItemStack items = null;
		if(args.length == 1){
			items = player.getItemInHand();
			if(items == null || items.getAmount() == 0){
				ItemMailMain.plugin.chat.sendPlayerMessage(player, "&cYou are not holding anything");
				return;
			}
		}else if(args.length == 2){
			items = TacoAPI.getItemUtils().createItemStack(args[1], 1);
			if(items == null){
				ItemMailMain.plugin.chat.sendPlayerMessage(player, "&e" + args[1] + " &cnot recognized");
				return;
			}
		}else{
			if(TacoAPI.getChatUtils().isNum(args[2])){
				int amount = Integer.parseInt(args[2]);
				items = TacoAPI.getItemUtils().createItemStack(args[1], amount);
				if(items == null){
					ItemMailMain.plugin.chat.sendPlayerMessage(player, "&e" + args[1] + " &c not recognized");
					return;
				}
			}else{
				ItemMailMain.plugin.chat.sendPlayerMessage(player, "&e" + args[2] + " &cis not a valid number");
			}	
		}
		if(items.getTypeId() == 0){
			ItemMailMain.plugin.chat.sendPlayerMessage(player, "&cYou cannot send air");
			return;
		}
		//comment out when testing
		if(player.getName().equalsIgnoreCase(receiver)){
			ItemMailMain.plugin.chat.sendPlayerMessage(player, "&cYou cannot send ItemMail to yourself");
			return;
		}
		String name = "";
		if(items.getItemMeta().hasDisplayName()){
			name = items.getItemMeta().getDisplayName();
		}
		String display = ItemMailMain.plugin.getDisplayString(items, name);
		if(TacoAPI.getPlayerAPI().getInventoryAPI().takeItems(player, items)){
			ItemMail.sendUnsafely(player.getName(), receiver, items, "");
			ItemMailMain.plugin.chat.sendPlayerMessage(player, "&aYou sent &2" + items.getAmount() + " " + display + " &ato &2" + receiver);
		}else{
			ItemMailMain.plugin.chat.sendPlayerMessage(player, "&cYou don't have &e" + items.getAmount() + " " + display);
		}
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
