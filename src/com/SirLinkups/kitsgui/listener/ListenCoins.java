package com.SirLinkups.kitsgui.listener;

import java.util.Map;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.SirLinkups.kitsgui.utility.Util;

public class ListenCoins implements Listener {
	private static Map<Player, Integer> COINS = Util.newMap();
	
	@EventHandler
	public void die(PlayerDeathEvent e) {
		Player p = e.getEntity();
		LivingEntity le = p.getKiller();
		if(le instanceof Player) {
			Player k = (Player) le;
			if(!p.equals(k)) {
				int c = 0;
				if(COINS.containsKey(k)) c = COINS.get(k);
				int n = c + 1;
				COINS.put(k, n);
				k.sendMessage(Util.color("&6+1 Coin"));
			}
		}
	}
	
	public static int coins(Player p) {
		if(COINS.containsKey(p)) {
			int c = COINS.get(p);
			return c;
		} else return 0;
	}
}