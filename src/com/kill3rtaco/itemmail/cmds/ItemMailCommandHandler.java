package com.kill3rtaco.itemmail.cmds;

import org.bukkit.entity.Player;

import com.kill3rtaco.itemmail.ItemMailMain;
import com.kill3rtaco.itemmail.cmds.im.AcceptCommand;
import com.kill3rtaco.itemmail.cmds.im.DeclineCommand;
import com.kill3rtaco.itemmail.cmds.im.DeleteCommand;
import com.kill3rtaco.itemmail.cmds.im.MailBoxCommand;
import com.kill3rtaco.itemmail.cmds.im.MailInfoCommand;
import com.kill3rtaco.itemmail.cmds.im.OpenCommand;
import com.kill3rtaco.itemmail.cmds.im.RequestBoxCommand;
import com.kill3rtaco.itemmail.cmds.im.RequestCommand;
import com.kill3rtaco.itemmail.cmds.im.RequestInfoCommand;
import com.kill3rtaco.itemmail.cmds.im.SendCommand;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommandHandler;

public class ItemMailCommandHandler extends TacoCommandHandler {

	public ItemMailCommandHandler() {
		super("im", "ItemMail information/commands", "");
	}

	@Override
	protected void registerCommands() {
		registerCommand(new AcceptCommand());
		registerCommand(new DeclineCommand());
		registerCommand(new DeleteCommand());
		registerCommand(new MailBoxCommand());
		registerCommand(new MailInfoCommand());
		registerCommand(new OpenCommand());
		registerCommand(new RequestBoxCommand());
		registerCommand(new RequestCommand());
		registerCommand(new RequestInfoCommand());
		registerCommand(new SendCommand());
	}

	@Override
	protected boolean onConsoleCommand() {
		return false;
	}

	@Override
	protected void onPlayerCommand(Player player) {
		String header = TacoAPI.getChatUtils().createHeader('1', "&9ItemMail Information");
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, header);
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&8Type '/im ?' for help");
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&3Plugin Version&7:&5 " + ItemMailMain.plugin.getDescription().getVersion());
		ItemMailMain.plugin.chat.sendPlayerMessageNoHeader(player, "&3Author&7: &cKILL3RTACO");
	}

}
