package com.SirLinkups.kitsgui;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.SirLinkups.kitsgui.command.CommandKit;
import com.SirLinkups.kitsgui.utility.Util;

public class Core extends JavaPlugin {
	@Override
	public void onEnable() {
		getCommand("kit").setExecutor(new CommandKit());
		getCommand("kit").setPermissionMessage(Util.color("&cYou don't have the permission &bkitsgui.kits"));
		Bukkit.getPluginManager().registerEvents(new CommandKit(), this);
	}
	
	@Override
	public void onDisable() {
		
	}
}