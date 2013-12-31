package com.kill3rtaco.itemmail.cmds.im;

import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.kill3rtaco.itemmail.ItemMailConstants;
import com.kill3rtaco.itemmail.ItemMailMain;
import com.kill3rtaco.itemmail.mail.ItemMail;
import com.kill3rtaco.itemmail.mail.ItemMailBox;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommand;
import com.kill3rtaco.tacoapi.json.JSONException;
import com.kill3rtaco.tacoapi.json.JSONObject;
import com.kill3rtaco.tacoapi.util.ItemUtils.DisplayName;

public class MailInfoCommand extends TacoCommand {

	public MailInfoCommand() {
		super("mail-info", new String[]{"mi"}, "[id]", "View detailed ItemMail information", ItemMailConstants.P_VIEW_MAIL_INFO);
		
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
		int amount = im.getItemAmount();
		if(dn != null) name = dn.getName();
		String friendlyName;
		if(im.getItemDisplayName().length() > 0)
			friendlyName = "&2&o" + im.getItemDisplayName() + " &r&a(&2" + name + "&a)";
		else
			friendlyName = "&2" + name;
		String matId = "&2" + im.getItemTypeId() + (im.getItemDamage() > 0 ? "&7:&2" + im.getItemDamage() : "");
		String time = TacoAPI.getChatUtils().getFriendlyTimestamp(im.getSendDate());
		String header = TacoAPI.getChatUtils().createHeader('1', "&9ItemMail Information");
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, header);
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&3Mail ID&7: &b" + im.getMailId() + " &8[" + (id) + "]");
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&6Sent by&7: &2" + im.getSender());
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&6Sent on&7: &2" + time);
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&3Material ID&7: " + matId);
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&6Contains&7: &2" + amount + " " + friendlyName);
		if(im.getRawData().has("enchantments")){
			ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&6Enchantments&7: "
					+ ItemMailMain.plugin.getEnchantmentString(im.getEnchantmentCode()) + " "
					+ "&a(&2" + im.getEnchantmentCode() + "&a)");
		}
		if(im.getRawData().has("armor-meta")){
			Color c = ((LeatherArmorMeta) im.getItems().getItemMeta()).getColor();
			String hex = "#" + Integer.toHexString(c.asRGB() & 0xffffff);
//			hex += Integer.parseInt(c.getRed() + "" , 16);
//			hex += Integer.parseInt(c.getGreen() + "" , 16);
//			hex += Integer.parseInt(c.getBlue() + "" , 16);
			ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&6Armor Color&7: &2" + hex  + " " +
					String.format("&a(&2%d&a, &2%d&a, &2%d&a)", c.getRed(), c.getGreen(), c.getBlue()));
		}
		if(im.getRawData().has("book-meta")){
			JSONObject bookMeta;
			try {
				bookMeta = im.getRawData().getJSONObject("book-meta");
				if(bookMeta.has("enchantments")){
					String display = ItemMailMain.plugin.getEnchantmentString(bookMeta.getString("enchantments"));
					ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&6Enchantment(s)&7: " + display);
				}else{
					if(bookMeta.has("title")){
						String title = bookMeta.getString("title");
						String author = bookMeta.getString("author");
						ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&6Title&7: &2" + title + " &aby &2" + author);
					}
				}
				if(bookMeta.has("pages")){
					ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&6Pages&7: " + bookMeta.getJSONArray("pages").length());
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&8To open: &7/im open " + id);
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&8To delete: &7/im delete " + id);
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
