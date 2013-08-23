package com.kill3rtaco.itemmail.cmds.im;

import org.bukkit.entity.Player;

import com.kill3rtaco.itemmail.ItemMailConstants;
import com.kill3rtaco.itemmail.ItemMailMain;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class ConvertCommand extends TacoCommand {

	public ConvertCommand() {
		super("convert", new String[]{}, "", "Convert old ItemMail data to the new format", ItemMailConstants.P_CONVERT_DATA);
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		ItemMailMain.data.convertData(player);
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		ItemMailMain.data.convertData(null);
		return true;
	}

}
