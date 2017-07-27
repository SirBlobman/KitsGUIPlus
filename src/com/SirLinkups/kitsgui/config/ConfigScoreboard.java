package com.SirLinkups.kitsgui.config;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import com.SirLinkups.kitsgui.utility.Util;

public class ConfigScoreboard extends Config {
	private static final File FILE = new File(FOLDER, "scoreboard.yml");
	private static YamlConfiguration config = YamlConfiguration.loadConfiguration(FILE);
	
	public static YamlConfiguration load() {
		try {
			config = load(FILE);
			defaults();
			return config;
		} catch(Throwable ex) {
			String error = "Failed to load 'scoreboard.yml': ";
			Util.print(error, ex.getCause());
			return null;
		}
	}
	
	public static void save() {
		try{save(config, FILE);}
		catch(Throwable ex) {
			String error = "Failed to save 'scoreboard.yml': ";
			Util.print(error, ex.getCause());
		}
	}
	
	private static void defaults() {
		List<String> list = Util.newList(
			"&8&m---------------------",
			"&b&lKills &f&l> &7%kills%",
			"&b&lLevel &f&l> &7%level%",
			"&b&lLevel Up&f&l> &7%next_level%",
			"&b&lDeaths &f&l> &7%deaths%",
			"&b&lKDR &f&l> &7%kdr%"
		);
		set("title", "&b&lKitsGui+", false);
		set("lines", list, false);
		save();
	}
	
	private static void set(String path, Object value, boolean force) {
		Object o = config.get(path);
		boolean n = (o == null);
		if(force || n) config.set(path, value);
	}
	
	public static List<String> getLines() {
		load();
		List<String> list = Util.newList();
		List<String> ss = config.getStringList("lines");
		for(String s : ss) {
			String c = Util.color(s);
			list.add(c);
		}
		return list;
	}
	
	public static String title() {
		String title = config.getString("title");
		String c = Util.color(title);
		return c;
	}
}