package com.SirLinkups.kitsgui.config.serializable;

import com.SirLinkups.kitsgui.config.ConfigDatabase;
import com.SirLinkups.kitsgui.utility.Util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class DonorKit extends Kit {
    private final int price;
    public DonorKit(String name, ItemStack icon, List<ItemStack> items, int price) {
        super(name, icon, items);
        this.price = price;
    }
    
    public DonorKit(Map<String, Object> map) {
        super(map);
        this.price = (int) map.get("price");
    }
    
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        map.put("price", price);
        return map;
    }
    
    public int getPrice() {return price;}
    public boolean hasPaid(Player p) {
        String name = getName();
        List<String> list = ConfigDatabase.bought(p);
        return list.contains(name);
    }
    
    public ItemStack getIcon(Player p) {
        ItemStack is = getIcon();
        ItemMeta meta = is.getItemMeta();
        String lore = Util.color(hasPaid(p) ? "&5Bought" : "&6Price: &c" + price + " &6coins");
        meta.setLore(Util.newList(lore));
        is.setItemMeta(meta);
        return is;
    }
}