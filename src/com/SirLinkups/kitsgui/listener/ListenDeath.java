package com.SirLinkups.kitsgui.listener;

import com.SirLinkups.kitsgui.config.*;
import com.SirLinkups.kitsgui.utility.Util;

import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.*;

public class ListenDeath implements Listener {
	@EventHandler
	public void die(PlayerDeathEvent e) {
		Player p = e.getEntity();
		LivingEntity le = p.getKiller();
		if(le instanceof Player) {
			Player k = (Player) le;
			if(!p.equals(k)) {
			    PotionEffectType REGEN = PotionEffectType.REGENERATION;
			    int time = (20 * ConfigConfig.AFTER_KILL_REGENERATION_TIME);
			    if(time > 0) {
			        PotionEffect pe = new PotionEffect(REGEN, time, 250);
			        k.addPotionEffect(pe);
			    }
				ConfigDatabase.addCoin(k);
				String msg = Util.color("&6+1 Coin");
				k.sendMessage(msg);
			}
		}
	}
}