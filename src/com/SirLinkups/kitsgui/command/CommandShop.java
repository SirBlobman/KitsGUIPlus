package com.SirLinkups.kitsgui.command;

import static com.SirLinkups.kitsgui.utility.KitsUtil.newItem;

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

import com.SirLinkups.kitsgui.utility.KitsUtil;
import com.SirLinkups.kitsgui.utility.Util;

public class CommandShop implements CommandExecutor, Listener {
	private static final String TITLE = Util.color("&1&lKit Shop");
	private static final ItemStack AIR = newItem(Material.AIR);
	private static final ItemStack SKULL = newItem(Material.SKULL_ITEM, 1, 0, "");
	private static final ItemStack BARS = newItem(Material.IRON_FENCE, 1, 0, "");
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(cs instanceof Player) {
			Player p = (Player) cs;
			String c = cmd.getName().toLowerCase();
			if(c.equals("kitshop")) {
				Inventory i = gui();
				p.openInventory(i);
				return true;
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
						
					}
				}
			}
		}
	}
	
	private Inventory gui() {
		Inventory i = Bukkit.createInventory(null, 27, TITLE);
		ItemStack[] inv = new ItemStack[] {
			SKULL, BARS, BARS, BARS, BARS, BARS, BARS, BARS, SKULL,
			BARS, AIR, AIR, AIR, AIR, AIR, AIR, AIR, BARS,
			SKULL, BARS, BARS, BARS, BARS, BARS, BARS, BARS, SKULL,
		};
		i.setContents(inv);
		return i;
	}
}