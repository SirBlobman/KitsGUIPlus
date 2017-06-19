package com.SirLinkups.kitsgui.utility;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KitsUtil extends Util {
	public static boolean air(ItemStack is) {
		if(is == null) return true;
		Material mat = is.getType();
		boolean air = (mat == Material.AIR);
		return air;
	}
	
	public static ItemStack newItem(Material mat) {
		ItemStack is = new ItemStack(mat);
		return is;
	}
	
	public static ItemStack newItem(Material mat, int amount) {
		ItemStack is = newItem(mat);
		if(!air(is)) is.setAmount(amount);
		return is;
	}
	
	public static ItemStack newItem(Material mat, int amount, int durability) {
		ItemStack is = newItem(mat, amount);
		if(!air(is)) {
			short data = (short) durability;
			is.setDurability(data);
		}
		return is;
	}
	
	public static ItemStack newItem(Material mat, int amount, int data, String disp, String... lore) {
		ItemStack is = newItem(mat, amount, data);
		String name = color(disp);
		List<String> list = newList(color(lore));
		if(!air(is)) {
			ItemMeta meta = is.getItemMeta();
			meta.setDisplayName(name);
			meta.setLore(list);
			meta.addItemFlags(ItemFlag.values());
			is.setItemMeta(meta);
		}
		return is;
	}
	
	public static ItemStack newLeather(EquipmentSlot es, int red, int green, int blue, String name) {
		Material mat = Material.AIR;
		switch(es) {
		case HEAD: {mat = Material.LEATHER_HELMET; break;}
		case CHEST: {mat = Material.LEATHER_CHESTPLATE; break;}
		case LEGS: {mat = Material.LEATHER_LEGGINGS; break;}
		case FEET: {mat = Material.LEATHER_BOOTS; break;}
		default: {mat = Material.AIR; break;}
		}
		
		if(mat != Material.AIR) {
			ItemStack is = newItem(mat, 1, 0, name);
			ItemMeta meta = is.getItemMeta();
			LeatherArmorMeta l = (LeatherArmorMeta) meta;
			Color c = Color.fromRGB(red, green, blue);
			l.setColor(c);
			is.setItemMeta(l);
			return is;
		} else return newItem(Material.LEATHER);
	}
	
	public static ItemStack newPotion(PotionEffectType pet, int duration, int amplifier) {
		ItemStack is = newItem(Material.POTION, 1);
		ItemMeta meta = is.getItemMeta();
		PotionMeta pm = (PotionMeta) meta;
		PotionEffect pe = new PotionEffect(pet, duration, amplifier);
		pm.setMainEffect(pet);
		pm.addCustomEffect(pe, true);
		is.setItemMeta(pm);
		return is;
	}
}