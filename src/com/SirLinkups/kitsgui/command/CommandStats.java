package com.SirLinkups.kitsgui.command;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirLinkups.kitsgui.utility.Util;

public class CommandStats implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(cs instanceof Player) {
			Player p = (Player) cs;
			String c = cmd.getName().toLowerCase();
			if (c.equals("stats")) {
				int len = args.length;
				if(len == 0) {
					String error = Util.color("&c&lStats > &8You must specify a Player!");
					p.sendMessage(error);
					return true;
				} else {
					String target = args[0];
					Player t = Bukkit.getPlayer(target);
					if(t == null) {
						String error = Util.color("&c&lStats > &8Player not found!");
						p.sendMessage(error);
						return true;
					} else {
						String name = t.getName();
						String[] msg = Util.color(
								"&7&n                          ",
								" ",
								"&7" + name,
								" ",
								"&c    Kills > &8" + t.getStatistic(Statistic.PLAYER_KILLS),
								"&c    Deaths > &8" + t.getStatistic(Statistic.DEATHS),
								"&7&n                          "
								);
						p.sendMessage(msg);
						return true;
					}
				}
			} else return false;
		} else {
			String error = "You are not a Player";
			cs.sendMessage(error);
			return true;
		}
	}
}