package com.SirLinkups.kitsgui;

import com.SirLinkups.kitsgui.command.*;
import com.SirLinkups.kitsgui.config.ConfigConfig;
import com.SirLinkups.kitsgui.listener.ListenDeath;
import com.SirLinkups.kitsgui.special.DonorItem;
import com.SirLinkups.kitsgui.utility.*;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.event.Listener;
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
		ConfigConfig.load();
		getCommand("createkit").setExecutor(new CommandCreateKit());
		getCommand("kit").setExecutor(new CommandKit());
		getCommand("kit").setPermissionMessage(Util.color("&cYou don't have the permission &bkitsgui.kits"));
		getCommand("kitsguiplus").setExecutor(new CommandMain());
		getCommand("kitshop").setExecutor(new CommandShop());
		PM.registerEvents(new CommandKit(), this);
		PM.registerEvents(new CommandShop(), this);
		PM.registerEvents(new ListenDeath(), this);
		PM.registerEvents(new DonorItem(), this);
	}
	
	public void command(String cmd, CommandExecutor ce) {
        PluginCommand pc = getCommand(cmd);
        if(pc != null) {
            if(ce != null) {
                pc.setExecutor(ce);
                if(ce instanceof TabCompleter) {
                    TabCompleter tc = (TabCompleter) ce;
                    pc.setTabCompleter(tc);
                }
                
                if(ce instanceof Listener) {
                    Listener l = (Listener) ce;
                    PM.registerEvents(l, this);
                }
            } else {
                String error = Util.format("The command '%1s' cannot have a NULL executor", cmd);
                Util.print(error);
            }
        } else {
            String error = Util.format("The command '%1s' is not inside of the 'plugin.yml' of CombatLogX", cmd);
            Util.print(error);
        }
    }
}