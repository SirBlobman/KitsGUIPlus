package com.SirLinkups.kitsgui.listener;

import static com.SirLinkups.kitsgui.utility.KitsUtil.*;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class DonorItem implements Listener {
	public static final ItemStack THOR_AXE = newItem(Material.IRON_AXE, 1, 0, "&6&lMj\u00F6lnir", "&a&oRight click to spawn lightning");
	
	@EventHandler
	public void use(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Action a = e.getAction();
		if(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
			ItemStack is = e.getItem();
			if(!air(is)) {
				if(equal(is, THOR_AXE)) {
					boolean use = cooldown(p);
					if(use) {
						Location l = p.getTargetBlock((Set<Material>) null, 200).getLocation();
						World w = l.getWorld();
						w.strikeLightning(l);
						addCooldown(p);
					}
				}
			}
		}
	}
}