package com.SirLinkups.kitsgui.command;

import static com.SirLinkups.kitsgui.utility.KitsUtil.newItem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.SirLinkups.kitsgui.utility.Util;

public class CommandKit implements CommandExecutor, Listener {
	private static final String TITLE = Util.color("&1&lKits");
	private static final ItemStack AIR = newItem(Material.AIR);
	private static final ItemStack SKULL = newItem(Material.SKULL_ITEM, 1, 0, "");
	private static final ItemStack BARS = newItem(Material.IRON_FENCE, 1, 0, "");
	private static final ItemStack KANGAROO_KIT = newItem(Material.FIREWORK, 1, 0, "&b&lKangaroo");
	private static final ItemStack SWORDSMAN_KIT = newItem(Material.STONE_SWORD, 1, 0, "&b&lSwordsman");
	private static final ItemStack ARCHER_KIT = newItem(Material.BOW, 1, 0, "&b&lArcher");
	private static final ItemStack SNOWMAN_KIT = newItem(Material.SNOW_BALL, 1, 0, "&b&lSnowman");
	private static final ItemStack FISHERMAN_KIT = newItem(Material.FISHING_ROD, 1, 0, "&b&lFisherman");
	private static final ItemStack NINJA_KIT = newItem(Material.NETHER_STAR, 1, 0, "&b&lNinja");
	private static final ItemStack ENDERMAN_KIT = newItem(Material.ENDER_PEARL, 1, 0, "&b&lEnderman");
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		String c = cmd.getName().toLowerCase();
		if(c.equals("kit") || c.equals("kits")) {
			if(cs instanceof Player) {
				Player p = (Player) cs;
			} else {
				String error = "You are not a Player";
				cs.sendMessage(error);
				return true;
			}
		} else return false;
	}
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		
	}
	
	private Inventory gui() {
		Inventory i = Bukkit.createInventory(null, 54, "Kits");
		ItemStack[] inv = new ItemStack[] {
			SKULL, BARS, BARS, BARS, BARS, BARS, BARS, BARS, SKULL,
			BARS, KANGAROO_KIT, SWORDSMAN_KIT, ARCHER_KIT, SNOWMAN_KIT, 
		}
	}
}