package com.kill3rtaco.itemmail;

import java.util.Map;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import com.kill3rtaco.itemmail.cmds.ItemMailCommandHandler;
import com.kill3rtaco.itemmail.data.ItemMailDatabase;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoPlugin;
import com.kill3rtaco.tacoapi.api.serialization.EnchantmentSerialization;
import com.kill3rtaco.tacoapi.util.ItemUtils.DisplayName;
import com.kill3rtaco.tacoapi.util.ItemUtils.EnchantDisplayName;

public class ItemMailMain extends TacoPlugin {

	public static ItemMailMain plugin;
	public static ItemMailDatabase data;
	
	@Override
	public void onStart() {
		if(TacoAPI.getDB() != null){
			plugin = this;
			data = new ItemMailDatabase();
			startMetrics();
			registerEvents(new ItemMailListener());
			registerCommand(new ItemMailCommandHandler());
		}else{
			chat.outSevere("Could not enable ItemMail; could not establish a connection to the MySQL database");
		}
	}

	@Override
	public void onStop() {
		
	}
	
	public String getEnchantmentString(Map<Enchantment, Integer> enchants){
		String result = "";
		int count = 0;
		for(Enchantment e : enchants.keySet()){
			count++;
			EnchantDisplayName name = EnchantDisplayName.getDisplayName(e.getId());
			result += "&a" +  name.getName() + " &2" + enchants.get(e);
			if(count < enchants.size())
				result +="&7, ";
		}
		return result;
	}
	
	public String getEnchantmentString(String enchantCode){
		return getEnchantmentString(EnchantmentSerialization.getEnchantments(enchantCode));
	}
	
	public String getDisplayString(ItemStack items){
		return getDisplayString(items, items.getItemMeta().getDisplayName());
	}
	
	public String getDisplayString(ItemStack items, String displayName){
		String display;
		if(displayName != null && displayName.length() > 0){
			display = "&o" + displayName;
			DisplayName dn = DisplayName.getDisplayName(items.getTypeId(), items.getDurability());
			if(dn != null) display += " &r&a(&2" + dn.getName().replaceAll("_", " ") + "&a)";
			else display += " &r&a(&2" + TacoAPI.getChatUtils().toProperCase(items.getType().name().replaceAll("_", " ")) + "&a)";
		}else{
			DisplayName dn = DisplayName.getDisplayName(items.getTypeId(), items.getDurability());
			if(dn != null) display = TacoAPI.getChatUtils().toProperCase(dn.getName().replaceAll("_", " "));
			else display = TacoAPI.getChatUtils().toProperCase(items.getType().name().replaceAll("_", " "));
		}
		return display;
	}

}
