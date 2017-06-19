package com.SirLinkups.kitsgui;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.SirLinkups.kitsgui.command.CommandKit;

public class Core extends JavaPlugin {
	@Override
	public void onEnable() {
		getCommand("kit").setExecutor(new CommandKit());
		Bukkit.getPluginManager().registerEvents(new CommandKit(), this);
	}
	
	@Override
	public void onDisable() {
		
	}
}