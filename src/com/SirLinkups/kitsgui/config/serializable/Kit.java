package com.SirLinkups.kitsgui.config.serializable;

import com.SirLinkups.kitsgui.utility.*;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Kit implements ConfigurationSerializable {
    private final String name;
    private final ItemStack icon;
    private final List<ItemStack> items;
    public Kit(String name, ItemStack icon, List<ItemStack> items) {
        this.name = name;
        this.icon = icon;
        this.items = items;
    }
    
    @SuppressWarnings("unchecked")
    public Kit(Map<String, Object> map) {
        this.name = (String) map.get("name");
        ItemStack icon = (ItemStack) map.get("icon");
        if(KitsUtil.air(icon)) this.icon = new ItemStack(Material.STONE_SWORD);
        else this.icon = icon;
        this.items = (List<ItemStack>) map.get("items");
    }
    
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Util.newMap();
        map.put("name", name);
        map.put("icon", icon);
        map.put("items", items);
        return map;
    }
    
    public String getName() {return name;}
    public List<ItemStack> getItems() {return items;}
    public ItemStack[] getItemsAsArray() {return items.toArray(new ItemStack[0]);}
    public ItemStack getIcon() {
        ItemStack is = icon.clone();
        is.setAmount(1);
        ItemMeta meta = is.getItemMeta();
        if(meta.hasLore()) meta.setLore(Util.newList());
        String name = Util.format("&2%1s", getName());
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.values());
        is.setItemMeta(meta);
        return is;
    }
    
    
}