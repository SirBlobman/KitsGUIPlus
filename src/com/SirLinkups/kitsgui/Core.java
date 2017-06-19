package com.SirLinkups.kitsgui;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.SirLinkups.kitsgui.command.CommandKit;
import com.SirLinkups.kitsgui.utility.ScoreboardUtil;
import com.SirLinkups.kitsgui.utility.Util;

public class Core extends JavaPlugin {
	public static Core INSTANCE;
	public static File FOLDER;
	
	@Override
	public void onEnable() {
		INSTANCE = this;
		FOLDER = getDataFolder();
		ScoreboardUtil.load();
		getCommand("kit").setExecutor(new CommandKit());
		getCommand("kit").setPermissionMessage(Util.color("&cYou don't have the permission &bkitsgui.kits"));
		Bukkit.getPluginManager().registerEvents(new CommandKit(), this);
	}
	
	@Override
	public void onDisable() {
		
	}
}