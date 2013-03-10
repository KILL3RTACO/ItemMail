package taco.itemmail.cmds.im;

import org.bukkit.entity.Player;

import taco.itemmail.ItemMailMain;
import taco.itemmail.Permission;
import taco.itemmail.mail.ItemMail;
import taco.itemmail.mail.ItemMailBox;
import taco.tacoapi.TacoAPI;
import taco.tacoapi.api.TacoCommand;
import taco.tacoapi.util.ItemUtils.DisplayName;

public class MailInfoCommand extends TacoCommand {

	public MailInfoCommand() {
		super("mail-info", new String[]{"mi"}, "[id]", "View detailed ItemMail information", Permission.VIEW_MAIL_INFO);
		
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
				ItemMailMain.plugin.chat.sendPlayerMessage(player, "&e" + args[0] + " &cis not a valid number");
				return;
			}
		}
		ItemMail im = mb.get(id - 1);
		if(im == null){
			ItemMailMain.plugin.chat.sendPlayerMessage(player, "&cThat mail doesn't exist");
			return;
		}
		String name = TacoAPI.getChatUtils().toProperCase(im.getItems().getType().name().replaceAll("_", " "));
		DisplayName dn = DisplayName.getDisplayName(im.getItemTypeId(), im.getItemDamage());
		if(dn != null) name = dn.getName();
		String friendlyName;
		if(im.getItemDisplayName().length() > 0)
			friendlyName = "&2&o" + im.getItemDisplayName() + " &r&a(&2" + name + "&a)";
		else
			friendlyName = "&2" + name;
		String matId = "&2" + im.getItemTypeId() + (im.getItemDamage() > 0 ? "&7:&2" + im.getItemDamage() : "");
		String time = TacoAPI.getChatUtils().getFriendlyTimestamp(im.getSendDate());
		String header = TacoAPI.getChatUtils().createHeader('1', "&9ItemMail Informtaion");
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, header);
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&3Mail ID&7: &b" + im.getMailId());
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&6Sent by&7: &2" + im.getSender());
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&6Sent on&7: &2" + time);
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&6Contains&7: &2 " + friendlyName);
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&3Material ID&7: " + matId);
		if(im.hasEnchantments()){
			ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&6Enchantments&7: "
					+ ItemMailMain.plugin.getEnchantmentString(im.getEnchantmentCode()) + " "
					+ "&a(&2" + im.getEnchantmentCode() + "&a)");
		}
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
