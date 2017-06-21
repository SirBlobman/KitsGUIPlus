package com.SirLinkups.kitsgui.command;

import static com.SirLinkups.kitsgui.utility.KitsUtil.*;
import static com.SirLinkups.kitsgui.command.CommandKit.HAS_KIT;

import com.SirLinkups.kitsgui.config.ConfigDatabase;
import com.SirLinkups.kitsgui.special.DonorKit;
import com.SirLinkups.kitsgui.utility.KitsUtil;
import com.SirLinkups.kitsgui.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CommandShop implements CommandExecutor, Listener {
	private static final String TITLE = Util.color("&1&lKit Shop");
	private static final ItemStack AIR = newItem(Material.AIR);
	private static final ItemStack SKULL = newItem(Material.SKULL_ITEM, 1, 0, "&f");
	private static final ItemStack BARS = newItem(Material.IRON_FENCE, 1, 0, "&f");

	private static final ItemStack THOR_KIT = DonorKit.THOR.getIcon();
	private static final ItemStack THOR_KIT_NOT_PAID = addLore(THOR_KIT, "&6Cost: &e50 Coins");
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(cs instanceof Player) {
			Player p = (Player) cs;
			String c = cmd.getName().toLowerCase();
			if(c.equals("kitshop")) {
				if(HAS_KIT.contains(p)) {
					String error = "You already have a kit!";
					p.sendMessage(error);
					return true;
				} else {
					p.openInventory(gui(p));
					return true;
				}
			} else return false;
		} else {
			String error = "You are not a Player!";
			cs.sendMessage(error);
			return true;
		}
	}
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		HumanEntity he = e.getWhoClicked();
		if(he instanceof Player) {
			Player p = (Player) he;
			Inventory i = e.getClickedInventory();
			if(i != null) {
				String name = i.getName();
				if(name != null && name.equals(TITLE)) {
					e.setCancelled(true);
					ItemStack is = e.getCurrentItem();
					if(!KitsUtil.air(is)) {
						PlayerInventory pi = p.getInventory();
						if(equal(is, THOR_KIT_NOT_PAID)) ConfigDatabase.buyKit(p, DonorKit.THOR);
						else if(equal(is, THOR_KIT)) {
							p.closeInventory();
							ItemStack[] add = DonorKit.THOR.getItems();
							pi.clear();
							pi.addItem(add);
							
							String msg = Util.color("&cYou selected the Thor kit");
							p.sendMessage(msg);
							HAS_KIT.add(p);
						}
					}
				}
			}
		}
	}
	
	private Inventory gui(Player p) {
		Inventory i = Bukkit.createInventory(null, 27, TITLE);
		boolean bthor = ConfigDatabase.bought(p, DonorKit.THOR);
		ItemStack thor;
		if(bthor) {thor = THOR_KIT;} else {thor = THOR_KIT_NOT_PAID;}
		ItemStack[] inv = new ItemStack[] {
			SKULL, BARS, BARS, BARS, BARS, BARS, BARS, BARS, SKULL,
			BARS, thor, AIR, AIR, AIR, AIR, AIR, AIR, BARS,
			SKULL, BARS, BARS, BARS, BARS, BARS, BARS, BARS, SKULL,
		};
		i.setContents(inv);
		return i;
	}
}