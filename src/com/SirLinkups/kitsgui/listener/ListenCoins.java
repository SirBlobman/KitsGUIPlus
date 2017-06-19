package com.SirLinkups.kitsgui.listener;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.SirLinkups.kitsgui.config.ConfigDatabase;
import com.SirLinkups.kitsgui.utility.Util;

public class ListenCoins implements Listener {
	@EventHandler
	public void die(PlayerDeathEvent e) {
		Player p = e.getEntity();
		LivingEntity le = p.getKiller();
		if(le instanceof Player) {
			Player k = (Player) le;
			if(!p.equals(k)) {
				ConfigDatabase.addCoin(k);
				String msg = Util.color("&6+1 Coin");
				k.sendMessage(msg);
			}
		}
	}
}