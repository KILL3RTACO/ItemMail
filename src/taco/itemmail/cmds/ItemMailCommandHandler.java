package taco.itemmail.cmds;

import org.bukkit.entity.Player;

import taco.itemmail.ItemMailMain;
import taco.itemmail.cmds.im.DeleteCommand;
import taco.itemmail.cmds.im.MailBoxCommand;
import taco.itemmail.cmds.im.MailInfoCommand;
import taco.itemmail.cmds.im.OpenCommand;
import taco.itemmail.cmds.im.SendCommand;
import taco.tacoapi.TacoAPI;
import taco.tacoapi.api.TacoCommandHandler;

public class ItemMailCommandHandler extends TacoCommandHandler {

	public ItemMailCommandHandler() {
		super("im", "ItemMail information/commands", "");
	}

	@Override
	protected void registerCommands() {
		registerCommand(new DeleteCommand());
		registerCommand(new MailBoxCommand());
		registerCommand(new MailInfoCommand());
		registerCommand(new OpenCommand());
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
