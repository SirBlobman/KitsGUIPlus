package com.SirLinkups.kitsgui.command;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class CommandCreateKit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender cs, Command c, String label, String[] args) {
        if(cs instanceof Player) {
            Player p = (Player) cs;
            String cmd = c.getName().toLowerCase();
            if(cmd.equals("createkit")) {
                String msg = "This command is not fully coded yet!";
                p.sendMessage(msg);
                return true;
            } else return false;
        } else {
            String error = "You are not a Player";
            cs.sendMessage(error);
            return true;
        }
    }
}