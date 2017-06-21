package com.SirLinkups.kitsgui;

import com.SirLinkups.kitsgui.command.CommandKit;
import com.SirLinkups.kitsgui.command.CommandMain;
import com.SirLinkups.kitsgui.command.CommandShop;
import com.SirLinkups.kitsgui.listener.ListenCoins;
import com.SirLinkups.kitsgui.special.DonorItem;
import com.SirLinkups.kitsgui.utility.ScoreboardUtil;
import com.SirLinkups.kitsgui.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Core extends JavaPlugin {
	private static final Server SERVER = Bukkit.getServer();
	private static final PluginManager PM = SERVER.getPluginManager();
	
	public static Core INSTANCE;
	public static File FOLDER;
	
	@Override
	public void onEnable() {
		INSTANCE = this;
		FOLDER = getDataFolder();
		ScoreboardUtil.load();
		getCommand("kit").setExecutor(new CommandKit());
		getCommand("kit").setPermissionMessage(Util.color("&cYou don't have the permission &bkitsgui.kits"));
		getCommand("kitsguiplus").setExecutor(new CommandMain());
		getCommand("kitshop").setExecutor(new CommandShop());
		PM.registerEvents(new CommandKit(), this);
		PM.registerEvents(new CommandShop(), this);
		PM.registerEvents(new ListenCoins(), this);
		PM.registerEvents(new DonorItem(), this);
	}
	
	@Override
	public void onDisable() {
		
	}
}