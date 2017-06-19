package com.SirLinkups.kitsgui.utility;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Kits {
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
	
	public static ItemStack newItem(Material mat, int amount, int data, String disp) {
		
	}
}