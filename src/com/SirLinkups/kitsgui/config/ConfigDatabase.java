package com.SirLinkups.kitsgui.config;

import java.io.File;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import com.SirLinkups.kitsgui.utility.Util;

public class ConfigDatabase extends Config {
	private static final File USERS = new File(FOLDER, "users");
	
	private static File file(OfflinePlayer op) {
		UUID uuid = op.getUniqueId();
		String id = uuid.toString();
		String f = id + ".yml";
		File file = new File(USERS, f);
		return file;
	}
	
	public static YamlConfiguration load(OfflinePlayer op) {
		try {
			File file = file(op);
			YamlConfiguration config = load(file);
			defaults(op, config);
			return config;
		} catch(Throwable ex) {
			String name = op.getName();
			String error = "Failed to load data for " + name + ":";
			Util.print(error, ex.getMessage());
			return null;
		}
	}
	
	public static void save(YamlConfiguration config, File file) {
		try {Config.save(config, file);} 
		catch(Exception ex) {
			String error = "Failed to save data in " + file + ":";
			Util.print(error, ex.getMessage());
		}
	}
	
	private static void defaults(OfflinePlayer op, YamlConfiguration config) {
		String username = op.getName();
		int coins = 0;
		
		set(config, "username", username, true);
		set(config, "coins", coins, false);
		File file = file(op);
		save(config, file);
	}
	
	private static void set(YamlConfiguration config, String path, Object value, boolean force) {
		Object o = config.get(path);
		boolean n = (o == null);
		if(n || force) config.set(path, value);
	}
	
	public static int coins(OfflinePlayer op) {
		YamlConfiguration config = load(op);
		int coins = config.getInt("coins");
		return coins;
	}
	
	public static void addCoin(OfflinePlayer op) {
		YamlConfiguration config = load(op);
		int coins = coins(op);
		int n = coins + 1;
		set(config, "coins", n, true);
		save(config, file(op));
	}
}