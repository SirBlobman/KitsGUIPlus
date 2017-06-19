package com.SirLinkups.kitsgui.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.SirLinkups.kitsgui.utility.Util;

public class CommandMain implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		String c = cmd.getName().toLowerCase();
		if(c.equals("kitsguiplus")) {
			String[] msg = Util.color(
				"&7&m------------------------------",
				"&c            KitsGui+",
				"",
				"&6This feature is coming soon!",
				"&7&m------------------------------"
			);
			cs.sendMessage(msg);
			return true;
		} else return false;
	}
}