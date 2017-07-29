package com.SirLinkups.kitsgui.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.SirLinkups.kitsgui.Core;

class Config {
	protected static final File FOLDER = Core.FOLDER;
	
	protected static YamlConfiguration load(File file) throws IOException, InvalidConfigurationException {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if(!file.exists()) save(config, file);
		config.load(file);
		return config;
	}
	
	protected static void save(YamlConfiguration config, File file) throws IOException, IllegalArgumentException {
		if(!file.exists()) {
			File folder = file.getParentFile();
			folder.mkdirs();
			file.createNewFile();
		} config.save(file);
	}
}