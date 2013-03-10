package taco.itemmail;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import taco.itemmail.cmds.ItemMailCommandHandler;
import taco.tacoapi.TacoAPI;
import taco.tacoapi.api.TacoPlugin;
import taco.tacoapi.util.ItemUtils.DisplayName;
import taco.tacoapi.util.ItemUtils.EnchantDisplayName;

public class ItemMailMain extends TacoPlugin {

	public static ItemMailMain plugin;
	public static ItemMailDatabase data;
	
	@Override
	public void onStart() {
		plugin = this;
		data = new ItemMailDatabase();
		startMetrics();
		registerEvents(new ItemMailListener());
		registerCommand(new ItemMailCommandHandler());
		chat.out("Enabled");
	}

	@Override
	public void onStop() {
		
	}
	
	public HashMap<Enchantment, Integer> getEnchantmentsFromCode(String code){
		HashMap<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
		if(code.length() == 0){
			return enchants;
		}
		String nums = Long.parseLong(code, 32) + "";
		System.out.println(nums);
		for(int i=0; i<nums.length(); i+=3){
			int enchantId = Integer.parseInt(nums.substring(i, i+2));
			int enchantLevel = Integer.parseInt(nums.charAt(i+2) + "");
			Enchantment ench = Enchantment.getById(enchantId);
			enchants.put(ench, enchantLevel);
		}
		return enchants;
	}
	
	public String getCodeFromEnchantments(Map<Enchantment, Integer> enchants){
		String enchantAndLevels = "";
		for(Enchantment e : enchants.keySet()){
			int level = enchants.get(e);
			String id = e.getId() + "";
			if(e.getId() < 10) id = "0" + id;
			id = id + (level + "");
			enchantAndLevels = enchantAndLevels + id;
		}
		long nums = Long.parseLong(enchantAndLevels);
		return Long.toString(nums, 32);
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
		return getEnchantmentString(getEnchantmentsFromCode(enchantCode));
	}
	
	public String getDisplayString(ItemStack items){
		return getDisplayString(items, items.getItemMeta().getDisplayName());
	}
	
	public String getDisplayString(ItemStack items, String displayName){
		String display;
		if(displayName.length() > 0){
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
