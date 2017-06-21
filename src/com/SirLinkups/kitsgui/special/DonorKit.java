package com.SirLinkups.kitsgui.special;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import static com.SirLinkups.kitsgui.utility.KitsUtil.*;

public enum DonorKit {
	THOR(
		"Thor", 50,
		newItem(Material.WOOD_AXE, 1, 0, "&b&lThor"),
		newItem(Material.CHAINMAIL_HELMET, 1),
		newItem(Material.CHAINMAIL_CHESTPLATE, 1),
		newItem(Material.CHAINMAIL_LEGGINGS, 1),
		newItem(Material.CHAINMAIL_BOOTS, 1),
		DonorItem.THOR_AXE
	);

	private String name;
	private int cost;
	private ItemStack icon;
	private ItemStack[] items;
	private DonorKit(String name, int cost, ItemStack icon, ItemStack... items) {
		this.name = name;
		this.icon = icon;
		this.cost = cost;
		this.items = items;
	}

	public String getName() {return name;}
	public int getCoinCost() {return cost;}
	public ItemStack[] getItems() {return items;}
	public ItemStack getIcon() {return icon;}
}