package com.kill3rtaco.itemmail.data;

import java.io.File;

import com.kill3rtaco.tacoapi.api.TacoConfig;

public class ItemMailConfig extends TacoConfig {

	public ItemMailConfig(File file) {
		super(file);
	}

	@Override
	protected void setDefaults() {
		addDefaultValue("itemmail.econ.send-tax", 0);
		addDefaultValue("itemmail.econ.open-tax", 0);
		addDefaultValue("itemmail.econ.tax-receiver", "Server");
	}
	
	public int getSendTax(){
		return getInt("itemmail.econ.send-tax");
	}
	
	public int getOpenTax(){
		return getInt("itemmail.econ.open-tax");
	}
	
	public String getTaxReceiver(){
		return getString("itemmail.econ.tax-receiver");
	}

}
