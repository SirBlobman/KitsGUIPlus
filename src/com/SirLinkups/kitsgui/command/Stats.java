package com.SirLinkups.kitsgui.command;
//Professionally made by SirLinkups (Sirblobman and Linkups)
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Stats
  implements CommandExecutor
{
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    Player p = (Player)sender;
    if (cmd.getName().equalsIgnoreCase("stats"))
    {
      if (args.length == 0)
      {
        p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Stats > " + ChatColor.DARK_GRAY + "Specify a player!");
        return true;
      }
      if (args.length == 1)
      {
        Player target = p.getServer().getPlayer(args[0]);
        if (target == null)
        {
          p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Stats > " + ChatColor.DARK_GRAY + "Player not found!");
          
          return true;
        }
        p.sendMessage(ChatColor.GRAY + "" + ChatColor.UNDERLINE + "                          ");
        p.sendMessage("                          ");
        p.sendMessage(ChatColor.GRAY + "        " + target.getName());
        p.sendMessage("                          ");
        p.sendMessage(ChatColor.RED + "      Kills >  " + ChatColor.DARK_GRAY + target.getStatistic(Statistic.PLAYER_KILLS));
        p.sendMessage(ChatColor.RED + "      Deaths >  " + ChatColor.DARK_GRAY + target.getStatistic(Statistic.DEATHS));
        p.sendMessage(ChatColor.GRAY + "" + ChatColor.UNDERLINE + "                          ");
      }
    }
    return true;
  }
}
