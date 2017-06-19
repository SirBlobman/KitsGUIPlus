package com.SirLinkups.kitsgui.command;

import static com.SirLinkups.kitsgui.utility.KitsUtil.newItem;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

import com.SirLinkups.kitsgui.utility.KitsUtil;
import com.SirLinkups.kitsgui.utility.Util;

import net.md_5.bungee.api.ChatColor;

public class CommandKit implements CommandExecutor, Listener {
	private static Map<Player, Long> cooldown = Util.newMap();
	
	//Gui Related
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
	
	//Kit Related
	private static final ItemStack KANGAROO_FIREWORK = newItem(Material.FIREWORK, 1, 0, "&2Ultra Jump");
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		String c = cmd.getName().toLowerCase();
		if(c.equals("kit")) {
			if(cs instanceof Player) {
				Player p = (Player) cs;
				p.openInventory(gui());
				return true;
			} else {
				String error = "You are not a Player";
				cs.sendMessage(error);
				return true;
			}
		} else return false;
	}
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		HumanEntity he = e.getWhoClicked();
		if(he instanceof Player) {
			Player p = (Player) he;
			Inventory i = e.getClickedInventory();
			String name = i.getName();
			if(name.equals(TITLE)) {
				e.setCancelled(true);
				ItemStack is = e.getCurrentItem();
				if(!KitsUtil.air(is)) {
					PlayerInventory pi = p.getInventory();
					if(is.equals(KANGAROO_KIT)) {
						p.closeInventory();
						ItemStack helm = newItem(Material.IRON_HELMET, 1);
						ItemStack ches = newItem(Material.IRON_CHESTPLATE, 1);
						ItemStack legs = newItem(Material.IRON_LEGGINGS, 1);
						
						ItemStack boot = newItem(Material.IRON_BOOTS, 1, 0, "Kangaroo Feet");
						boot.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 32767);
						
						ItemStack sword = newItem(Material.STONE_SWORD);
						ItemStack soup = newItem(Material.MUSHROOM_SOUP, 24);
						ItemStack[] add = new ItemStack[] {helm, ches, legs, boot, sword, soup, KANGAROO_FIREWORK};
						pi.clear();
						pi.addItem(add);
						String msg = Util.color("&cYou selected the Kangaroo kit");
						p.sendMessage(msg);
					} else if(is.equals(SWORDSMAN_KIT)) {
						ItemStack helm = newItem(Material.IRON_HELMET, 1);
						ItemStack ches = newItem(Material.IRON_CHESTPLATE, 1);
						ItemStack legs = newItem(Material.IRON_LEGGINGS, 1);
						ItemStack boot = newItem(Material.IRON_BOOTS, 1);
						ItemStack sword = newItem(Material.STONE_SWORD, 1);
						sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
						ItemStack soup = newItem(Material.MUSHROOM_SOUP, 12);
						ItemStack[] add = new ItemStack[] {helm, ches, legs, boot, sword, soup};
						pi.clear();
						pi.addItem(add);
						String msg = Util.color("&cYou selected the Swordsman kit");
						p.sendMessage(msg);
					} else if(is.equals(ARCHER_KIT)) {
						
					} else if(is.equals(SNOWMAN_KIT)) {
						
					} else if(is.equals(FISHERMAN_KIT)) {
						
					} else if(is.equals(NINJA_KIT)) {
						
					} else if(is.equals(ENDERMAN_KIT)) {
						
					}
				} 
			}
		}
	}
	
	@EventHandler
	public void click(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Action a = e.getAction();
		if(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
			ItemStack is = e.getItem();
			if(!KitsUtil.air(is)) {
				if(is.equals(KANGAROO_FIREWORK)) {
					e.setCancelled(true);
					boolean use = cooldown(p);
					if(use) {
						Vector v = p.getVelocity();
						Vector va = new Vector(0, 2, 0);
						v.add(va);
						p.setVelocity(v);
						p.setFallDistance(-5.0F);
						String msg = "You used Kangaroo Jump!";
						p.sendMessage(msg);
						addCooldown(p);
					}
				}
			}
		}
	}
	
	private Inventory gui() {
		Inventory i = Bukkit.createInventory(null, 54, TITLE);
		ItemStack[] inv = new ItemStack[] {
			SKULL, BARS, BARS, BARS, BARS, BARS, BARS, BARS, SKULL,
			BARS, KANGAROO_KIT, SWORDSMAN_KIT, ARCHER_KIT, SNOWMAN_KIT, FISHERMAN_KIT, NINJA_KIT, ENDERMAN_KIT, BARS,
			BARS, AIR, AIR, AIR, AIR, AIR, AIR, AIR, BARS,
			BARS, AIR, AIR, AIR, AIR, AIR, AIR, AIR, BARS,
			BARS, AIR, AIR, AIR, AIR, AIR, AIR, AIR, BARS,
			SKULL, BARS, BARS, BARS, BARS, BARS, BARS, BARS, SKULL
		};
		i.setContents(inv);
		return i;
	}
	
	private boolean cooldown(Player p) {
		if(cooldown.containsKey(p)) {
			long l = cooldown.get(p);
			long c = System.currentTimeMillis();
			long t = l - c;
			int time = (int) (t / 1000L);
			if(time <= 0) {
				cooldown.remove(p);
				return true;
			}
			else {
				String error = String.format("You must wait %1s seconds before using another special ability!", time);
				p.sendMessage(error);
				return false;
			}
		} else return true;
	}
	
	private void addCooldown(Player p) {
		long c = System.currentTimeMillis();
		long l = c + (50 * 1000L);
		cooldown.put(p, l);
	}
}