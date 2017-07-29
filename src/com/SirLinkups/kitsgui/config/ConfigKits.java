package com.SirLinkups.kitsgui.config;

import com.SirLinkups.kitsgui.config.serializable.*;
import com.SirLinkups.kitsgui.utility.Util;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;

public class ConfigKits extends Config {
    private static final File KITS_FOLDER = new File(FOLDER, "kits");
    private static List<Kit> KIT_LIST = Util.newList();
    private static List<DonorKit> DONOR_LIST = Util.newList();
    
    public static void loadKits() {
        try {
            KIT_LIST.clear();
            if(!KITS_FOLDER.exists()) {
                FOLDER.mkdirs();
                KITS_FOLDER.mkdirs();
            }
            
            for(File file : KITS_FOLDER.listFiles()) {
                String name = file.getName();
                if(name.endsWith(".kit.yml")) {
                    YamlConfiguration config = load(file);
                    Object o = config.get("kit");
                    if(o == null || !(o instanceof Kit)) continue;
                    else {
                        Kit kit = (Kit) o;
                        if(kit instanceof DonorKit) {
                            DonorKit dk = (DonorKit) kit;
                            DONOR_LIST.add(dk);
                        } else KIT_LIST.add(kit);
                    }
                } else continue;
            }
        } catch(Throwable ex) {
            String error = "Failed to load kits:";
            Util.print(error);
            ex.printStackTrace();
        }
    }
    
    public static void saveKit(String name, ItemStack icon, List<ItemStack> items) {
        try {
            Kit kit = new Kit(name, icon, items);
            File file = new File(KITS_FOLDER, name + ".kit.yml");
            YamlConfiguration config = new YamlConfiguration();
            config.set("kit", kit);
            save(config, file);
        } catch(Throwable ex) {
            String error = "Failed to save kit '" + name + "':";
            Util.print(error);
            ex.printStackTrace();
        }
    }
    
    public static void saveDonorKit(String name, ItemStack icon, List<ItemStack> items, int price) {
        try {
            DonorKit kit = new DonorKit(name, icon, items, price);
            File file = new File(KITS_FOLDER, name + ".kit.yml");
            YamlConfiguration config = new YamlConfiguration();
            config.set("kit", kit);
            save(config, file);
        } catch(Throwable ex) {
            String error = "Failed to save kit '" + name + "':";
            Util.print(error);
            ex.printStackTrace();
        }
    }
    
    public static List<Kit> getKits() {return KIT_LIST;}
    public static List<DonorKit> getDonorKits() {return DONOR_LIST;}
    public static List<String> getStringKits() {
        List<Kit> kits = getKits();
        List<String> list = Util.newList();
        for(Kit k : kits) {
            String name = k.getName();
            list.add(name);
        } return list;
    }
    
    public static List<String> getStringDonorKits() {
        List<DonorKit> kits = getDonorKits();
        List<String> list = Util.newList();
        for(DonorKit dk : kits) {
            String name = dk.getName();
            list.add(name);
        } return list;
    }
    
    public static boolean doesKitExist(String name) {
        List<String> list = getStringKits();
        return list.contains(name);
    }
    
    public static boolean doesDonorKitExist(String name) {
        List<String> list = getStringDonorKits();
        return list.contains(name);
    }
    
    public static Kit getKit(String name) {
        if(doesKitExist(name)) {
            for(Kit k : getKits()) {
                String name1 = k.getName();
                if(name.equals(name1)) return k;
                else continue;
            }
        } return null;
    }
    
    public static DonorKit getDonorKit(String name) {
        if(doesDonorKitExist(name)) {
            for(DonorKit dk : getDonorKits()) {
                String name1 = dk.getName();
                if(name.equals(name1)) return dk;
                else continue;
            }
        } return null;
    }
}