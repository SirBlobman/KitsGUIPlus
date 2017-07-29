package com.SirLinkups.kitsgui.command;

import com.SirLinkups.kitsgui.config.ConfigKits;
import com.SirLinkups.kitsgui.utility.*;

import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;

import java.util.List;

public class CommandCreateKit implements CommandExecutor {
    @Override /*Usage: /createkit <name> [price]*/
    public boolean onCommand(CommandSender cs, Command c, String label, String[] args) {
        if(cs instanceof Player) {
            Player p = (Player) cs;
            PlayerInventory pi = p.getInventory();
            String cmd = c.getName().toLowerCase();
            if(cmd.equals("createkit")) {
                if(args.length > 0) {
                    String name = args[0];
                    ItemStack icon = pi.getItemInHand();
                    if(KitsUtil.air(icon)) icon = new ItemStack(Material.STONE_SWORD);
                    
                    ItemStack[] ii = pi.getContents();
                    List<ItemStack> items = Util.newList(ii);
                    if(items.isEmpty()) {
                        String error = "You silly noob, kits are for items! (NOT AIR)";
                        p.sendMessage(error);
                        return true;
                    }
                    
                    int price = 0;
                    if(args.length > 1) {
                        String pr = args[1];
                        try {price = Integer.parseInt(pr);}
                        catch(Throwable ex) {price = 0;}
                    }
                    
                    if(price > 0) ConfigKits.saveDonorKit(name, icon, items, price);
                    else ConfigKits.saveKit(name, icon, items);
                    
                    String msg = "Created a kit called '" + name + "' with a price of " + price + " coins!";
                    p.sendMessage(msg);
                    return true;
                } else return false;
            } else return false;
        } else {
            String error = "You are not a Player";
            cs.sendMessage(error);
            return true;
        }
    }
}