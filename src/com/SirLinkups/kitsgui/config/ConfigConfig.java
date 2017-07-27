package com.SirLinkups.kitsgui.config;

import com.SirLinkups.kitsgui.utility.Util;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigConfig extends Config {
    private static final File FILE = new File(FOLDER, "worlds.yml");
    private static YamlConfiguration config = new YamlConfiguration();
    
    public static YamlConfiguration load() {
        try {
            if(!FILE.exists()) save();
            config = load(FILE);
            defaults();
            return config;
        } catch(Throwable ex) {
            String error = "Failed to load config!";
            Util.print(error);
            return null;
        }
    }
    
    public static void save() {
        try {
            if(!FILE.exists()) {
                FOLDER.mkdirs();
                FILE.createNewFile();
            } config.save(FILE);
        } catch(Throwable ex) {
            String error = "Failed to save config!";
            Util.print(error);
        }
    }
    
    public static int AFTER_KILL_REGENERATION_TIME = 2;
    private static void defaults() {
        AFTER_KILL_REGENERATION_TIME = get("options.after kill.regeneration time", 2);
        save();
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T get(String path, T defaultValue) {
        Object o = config.get(path);
        Class<?> clazz = defaultValue.getClass();
        if(o != null && clazz.isInstance(o)) {
            T t = (T) clazz.cast(o);
            return t;
        } else {
            config.set(path, defaultValue);
            return defaultValue;
        }
    }
}