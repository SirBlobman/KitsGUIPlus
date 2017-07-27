package com.SirLinkups.kitsgui.utility;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.SirLinkups.kitsgui.Core;
import com.SirLinkups.kitsgui.config.ConfigScoreboard;

public class ScoreboardUtil extends Util implements Runnable {
	private static final BukkitScheduler BS = SERVER.getScheduler();
	private static final ScoreboardManager SM = SERVER.getScoreboardManager();
	
	private static List<Player> DISABLED = newList();
	public static void load() {
		BS.runTaskTimer(Core.INSTANCE, new ScoreboardUtil(), 20L, 200L);
	}
	
	@Override
	public void run() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(DISABLED.contains(p)) continue;
			else update(p);
		}
	}
	
	public static void update(Player p) {
		List<String> list = ConfigScoreboard.getLines();
		String title = ConfigScoreboard.title();
		Scoreboard sb = SM.getNewScoreboard();
		Objective o = sb.registerNewObjective("kitsgui", "dummy");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		o.setDisplayName(title);
		for(String s : list) {
			int i = (list.size() - list.indexOf(s));
			String f = format(s, p);
			if(f.length() > 40) f = f.substring(0, 40);
			Score sc = o.getScore(f);
			sc.setScore(i);
		}
		p.setScoreboard(sb);
	}
}