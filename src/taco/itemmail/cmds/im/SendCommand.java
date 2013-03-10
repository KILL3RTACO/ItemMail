package taco.itemmail.cmds.im;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import taco.itemmail.ItemMailMain;
import taco.itemmail.Permission;
import taco.itemmail.mail.ItemMail;
import taco.tacoapi.TacoAPI;
import taco.tacoapi.api.TacoCommand;

public class SendCommand extends TacoCommand {

	public SendCommand() {
		super("send", new String[]{}, "<player> [item[:damage]] [amount]", "Send ItemMail", Permission.SEND_MAIL);
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(args.length == 0){
			ItemMailMain.plugin.chat.sendPlayerMessage(player, "&cMust at least specify a player to send to");
			return;
		}
		String receiver = args[0], enchants = "", name = "";
		Player p = ItemMailMain.plugin.getServer().getPlayer(receiver);
		if(p != null) receiver = p.getName();
		int id = 0, damage = 0, amount = 1;
		ItemStack items = null;
		if(args.length == 1){
			items = player.getItemInHand();
			if(items == null || items.getAmount() == 0){
				ItemMailMain.plugin.chat.sendPlayerMessage(player, "&cYou are not holding anything");
				return;
			}
			id = items.getTypeId();
			damage = items.getDurability();
			amount = items.getAmount();
			if(items.getEnchantments().size() > 0)
				enchants = ItemMailMain.plugin.getCodeFromEnchantments(items.getEnchantments());
			if(items.getItemMeta().hasDisplayName())
				name = items.getItemMeta().getDisplayName();
		}else if(args.length == 2){
			amount = 1;
			items = TacoAPI.getItemUtils().createItemStack(args[1], amount);
			if(items == null){
				ItemMailMain.plugin.chat.sendPlayerMessage(player, "&e" + args[1] + " &cnot recognized");
				return;
			}
			id = items.getTypeId();
			damage = items.getDurability();
			if(items.getEnchantments().size() > 0)
				enchants = ItemMailMain.plugin.getCodeFromEnchantments(items.getEnchantments());
			if(items.getItemMeta().hasDisplayName())
				name = items.getItemMeta().getDisplayName();
		}else{
			if(TacoAPI.getChatUtils().isNum(args[2])){
				amount = Integer.parseInt(args[2]);
				items = TacoAPI.getItemUtils().createItemStack(args[1], amount);
				if(items == null){
					ItemMailMain.plugin.chat.sendPlayerMessage(player, "&e" + args[1] + " &c not recognized");
					return;
				}
				id = items.getTypeId();
				damage = items.getDurability();
				if(items.getEnchantments().size() > 0)
					enchants = ItemMailMain.plugin.getCodeFromEnchantments(items.getEnchantments());
				if(items.getItemMeta().hasDisplayName())
					name = items.getItemMeta().getDisplayName();
			}else{
				ItemMailMain.plugin.chat.sendPlayerMessage(player, "&e" + args[2] + " &cis not a valid number");
			}
		}
		if(id == 0){
			ItemMailMain.plugin.chat.sendPlayerMessage(player, "&cYou cannot send air");
			return;
		}
		//comment out when testing
//		if(player.getName().equalsIgnoreCase(receiver)){
//			ItemMailMain.plugin.chat.sendPlayerMessage(player, "&cYou cannot send ItemMail to yourself");
//			return;
//		}
		String display = ItemMailMain.plugin.getDisplayString(items, name);
		if(TacoAPI.getPlayerAPI().getInventoryAPI().takeItems(player, items)){
			ItemMail im = new ItemMail(player.getName(), receiver, id, damage, amount, name, enchants);
			im.send();
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
