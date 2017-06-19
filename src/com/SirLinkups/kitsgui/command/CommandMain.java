package com.SirLinkups.kitsgui.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.SirLinkups.kitsgui.config.ConfigScoreboard;
import com.SirLinkups.kitsgui.utility.Util;

public class CommandMain implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		String c = cmd.getName().toLowerCase();
		if(c.equals("kitsguiplus")) {
			int len = args.length;
			if(len == 0) {
				String[] msg = Util.color(
						"&7&m------------------------------",
						"&c            KitsGui+",
						"",
						"&6/kgp - Main Command",
						"&6/kgp reload - Reload the config",
						"&6/kit - Opens the Kit GUI",
						"&7&m------------------------------"
					);
					cs.sendMessage(msg);
					return true;
			} else {
				String sub = args[0].toLowerCase();
				if(sub.equals("reload")) {
					ConfigScoreboard.save();
					ConfigScoreboard.load();
					String msg = "Reloaded the configs";
					cs.sendMessage(msg);
					return true;
				} else return Bukkit.dispatchCommand(cs, "kgp");
			}
		} else return false;
	}
}