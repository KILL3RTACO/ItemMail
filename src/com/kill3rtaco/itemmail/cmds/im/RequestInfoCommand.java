package com.kill3rtaco.itemmail.cmds.im;

import org.bukkit.entity.Player;

import com.kill3rtaco.itemmail.ItemMailMain;
import com.kill3rtaco.itemmail.Permission;
import com.kill3rtaco.itemmail.request.ItemRequest;
import com.kill3rtaco.itemmail.request.ItemRequestBox;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommand;
import com.kill3rtaco.tacoapi.util.ItemUtils.DisplayName;

public class RequestInfoCommand extends TacoCommand {

	public RequestInfoCommand() {
		super("request-info", new String[]{"ri"}, "[id]", "View detailed ItemRequest information", Permission.VIEW_REQUEST_INFO);
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		ItemRequestBox rb = new ItemRequestBox(player);
		int id = rb.getUnreadCount();
		if(rb.isEmpty()){
			ItemMailMain.plugin.chat.sendPlayerMessage(player, "&cYour ItemRequestBox is empty");
			return;
		}
		if(args.length > 0){
			if(TacoAPI.getChatUtils().isNum(args[0])){
				id = Integer.parseInt(args[0]);
			}else{
				ItemMailMain.plugin.chat.sendPlayerMessage(player, "&e" + args[0] + " &cis not a valid nurber");
				return;
			}
		}
		ItemRequest request = rb.get(id - 1);
		if(request == null){
			ItemMailMain.plugin.chat.sendPlayerMessage(player, "&cThat mail doesn't exist");
			return;
		}
		int amount = request.getItemAmount();
		String name = TacoAPI.getChatUtils().toProperCase(request.getItems().getType().name().replaceAll("_", " "));
		DisplayName dn = DisplayName.getDisplayName(request.getItemTypeId(), request.getItemDamage());
		if(dn != null) name = dn.getName();
		String friendlyName  = "&2" + name;
		String matId = "&2" + request.getItemTypeId() + (request.getItemDamage() > 0 ? "&7:&2" + request.getItemDamage() : "");
		String time = TacoAPI.getChatUtils().getFriendlyTimestamp(request.getSendDate());
		String header = TacoAPI.getChatUtils().createHeader('1', "&9ItemRequest Informtaion");
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, header);
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&3Mail ID&7: &b" + request.getMailId());
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&6Sent by&7: &2" + request.getSender());
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&6Sent on&7: &2" + time);
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&6Items Requested&7: &2" + amount + " "  + friendlyName);
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&3Material ID&7: " + matId);
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&8To accept: &7/im accept " + id);
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&8To decline: &7/im decline " + id);
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
