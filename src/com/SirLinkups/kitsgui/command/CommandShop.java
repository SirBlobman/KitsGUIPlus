package com.SirLinkups.kitsgui.command;

import static com.SirLinkups.kitsgui.command.CommandKit.HAS_KIT;
import static com.SirLinkups.kitsgui.utility.KitsUtil.*;

import com.SirLinkups.kitsgui.config.*;
import com.SirLinkups.kitsgui.config.serializable.*;
import com.SirLinkups.kitsgui.utility.*;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class CommandShop implements CommandExecutor, Listener {
    private static Map<Player, Integer> PAGE = Util.newMap();
    private static final String TITLE = Util.color("&1&lKit Shop");
    private static final ItemStack BACK = newItem(Material.ARROW, 1, 0, "&fPrevious Page");
    private static final ItemStack NEXT = newItem(Material.FEATHER, 1, 0, "&fNext Page");

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if(cs instanceof Player) {
            Player p = (Player) cs;
            String c = cmd.getName().toLowerCase();
            if(c.equals("kitshop")) {
                if(HAS_KIT.contains(p)) {
                    String error = "You already have a kit!";
                    p.sendMessage(error);
                    return true;
                } else {
                    int page = 1;
                    if(args.length > 0) {
                        try {
                            page = Integer.parseInt(args[0]);
                            if(page < 1) page = 1;
                        }
                        catch(Throwable ex) {page = 1;}
                    } else page = 1;

                    Inventory gui = gui(p, page);
                    p.openInventory(gui);
                    return true;
                }
            } else return false;
        } else {
            String error = "You are not a Player!";
            cs.sendMessage(error);
            return true;
        }
    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        HumanEntity he = e.getWhoClicked();
        if(he instanceof Player) {
            Player p = (Player) he;
            Inventory i = e.getClickedInventory();
            if(i != null) {
                String name = i.getName();
                if(name != null && name.equals(TITLE)) {
                    e.setCancelled(true);
                    ItemStack is = e.getCurrentItem();
                    if(!KitsUtil.air(is)) {
                        if(is.equals(BACK)) {
                            int page = PAGE.get(p);
                            page -= 1;
                            if(page < 1) page = 1;
                            String cmd = Util.format("kits %1s", page);
                            p.closeInventory();
                            p.performCommand(cmd);
                            PAGE.put(p, page);
                        } else if(is.equals(NEXT)) {
                            int page = PAGE.get(p);
                            page += 1;
                            String cmd = Util.format("kits %1s", page);
                            p.closeInventory();
                            p.performCommand(cmd);
                            PAGE.put(p, page); 
                        } else if(is.hasItemMeta()) {
                            ItemMeta meta = is.getItemMeta();
                            if(meta.hasDisplayName()) {
                                String disp = meta.getDisplayName();
                                String dname = Util.strip(disp);
                                if(ConfigKits.doesDonorKitExist(dname)) {
                                    DonorKit dk = ConfigKits.getDonorKit(dname);
                                    if(dk.hasPaid(p)) {
                                        ItemStack[] ii = dk.getItemsAsArray();
                                        PlayerInventory pi = p.getInventory();
                                        pi.clear();
                                        for(ItemStack item : ii) {
                                            Map<Integer, ItemStack> map = pi.addItem(item);
                                            for(ItemStack drop : map.values()) {
                                                World w = p.getWorld();
                                                Location l = p.getLocation();
                                                w.dropItem(l, drop);
                                            }
                                        }
                                        p.closeInventory();
                                        p.sendMessage("You selected a kit called '" + dk.getName() + "'.");
                                        HAS_KIT.add(p);
                                    } else ConfigDatabase.buyKit(p, dk);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private Inventory gui(Player p, int page) {
        ConfigKits.loadKits();
        List<String> kits = ConfigKits.getStringDonorKits();
        int end = (page * 27);
        int start = end - 27;
        Inventory i = blank(page, end);
        if(kits.isEmpty()) return i;
        else {
            if(kits.size() < start) return gui(p, 1);
            if(kits.size() < end) end = kits.size();
            List<String> list = kits.subList(start, end);
            int j = 0;
            for(String s : list) {
                DonorKit dk = ConfigKits.getDonorKit(s);
                ItemStack icon = dk.getIcon(p);
                if(KitsUtil.air(icon)) icon = new ItemStack(Material.STONE_SWORD);
                i.setItem(j, icon);
                j++;
            }
            return i;
        }
    }

    private Inventory blank(int page, int end) {
        List<String> list = ConfigKits.getStringDonorKits();
        Inventory i = Bukkit.createInventory(null, 45, TITLE);

        if(page != 1) i.setItem(36, BACK);
        if(list.size() > end) i.setItem(44, NEXT);
        return i;
    }
}