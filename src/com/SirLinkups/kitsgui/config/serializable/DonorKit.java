package com.SirLinkups.kitsgui.config.serializable;

import org.bukkit.inventory.ItemStack;

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
}