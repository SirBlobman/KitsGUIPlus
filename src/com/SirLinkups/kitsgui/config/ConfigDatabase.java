package com.SirLinkups.kitsgui.config;

import com.SirLinkups.kitsgui.config.serializable.DonorKit;
import com.SirLinkups.kitsgui.utility.Util;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

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
		List<String> bought = Util.newList();
		
		set(config, "username", username, true);
		set(config, "coins", coins, false);
		set(config, "bought kits", bought, false);
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
		int coins = coins(op);
		int n = coins + 1;
		setCoins(op, n);
	}
	
	public static void setCoins(OfflinePlayer op, int amount) {
		YamlConfiguration config = load(op);
		set(config, "coins", amount, true);
		save(config, file(op));
	}
	
	public static void buyKit(OfflinePlayer op, DonorKit dk) {
		int coins = coins(op);
		int price = dk.getPrice();
		if(coins >= price) {
			int n = coins - price;
			setCoins(op, n);
			if(op.isOnline()) {
				Player p = op.getPlayer();
				p.closeInventory();
				p.sendMessage("You just bought the kit '" + dk.getName() + "'.");
			}
			YamlConfiguration config = load(op);
			List<String> list = config.getStringList("bought kits");
			String name = dk.getName();
			list.add(name);
			set(config, "bought kits", list, true);
			save(config, file(op));
		} else {
			if(op.isOnline()) {
				Player p = op.getPlayer();
				p.closeInventory();
				String error = "You don't have enough coins!";
				p.sendMessage(error);
			}
			return;
		}
	}
	
	public static List<String> bought(OfflinePlayer op) {
		YamlConfiguration config = load(op);
		List<String> ss = config.getStringList("bought kits");
		return ss;
	}
}