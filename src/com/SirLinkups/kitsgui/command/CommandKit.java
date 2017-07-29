package com.SirLinkups.kitsgui.command;

import static com.SirLinkups.kitsgui.utility.KitsUtil.*;

import com.SirLinkups.kitsgui.Core;
import com.SirLinkups.kitsgui.config.ConfigKits;
import com.SirLinkups.kitsgui.config.serializable.Kit;
import com.SirLinkups.kitsgui.utility.*;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.*;
import org.bukkit.util.Vector;

import java.util.*;

public class CommandKit implements CommandExecutor, Listener {
    public static List<Player> HAS_KIT = Util.newList();
    private static List<Player> HULK_SMASH = Util.newList();

    //Gui Related
    private static final String TITLE = Util.color("&1&lKits");
    private static Map<Player, Integer> PAGE = Util.newMap();
    private static final ItemStack BACK = newItem(Material.ARROW, 1, 0, "&fPrevious Page");
    private static final ItemStack NEXT = newItem(Material.FEATHER, 1, 0, "&fNext Page");
    private static final ItemStack KANGAROO_FIREWORK = newItem(Material.FIREWORK, 1, 0, "&2Ultra Jump");
    private static final ItemStack SNOWMAN_SNOWBALL = newItem(Material.SNOW_BALL, 16, 0, "&bUltra Snowball");
    private static final ItemStack HULK_SWORD = newItem(Material.STONE_SWORD, 1, 0, "&2&lHulk Sword");
    private static final ItemStack VIPER_WOOL = newItem(Material.WOOL, 1, 15, "&8&lPoisonous Wool", "&a&oRight click to poison enemies..."); 
    private static final ItemStack IRONMAN_FEATHER = newItem(Material.FEATHER, 1, 0, "&fRocket Boosters", "&a&oRight click to fly for 10 seconds...");
    
    @Override /*Usage: /kits <page>*/
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        String c = cmd.getName().toLowerCase();
        if(c.equals("kit")) {
            if(cs instanceof Player) {
                Player p = (Player) cs;
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
            } else {
                String error = "You are not a Player";
                cs.sendMessage(error);
                return true;
            }
        } else return false;
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
                    if(!air(is)) {
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
                                String kit = Util.strip(disp);
                                if(ConfigKits.doesKitExist(kit)) {
                                    Kit k = ConfigKits.getKit(kit);
                                    ItemStack[] ii = k.getItemsAsArray();
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
                                    p.sendMessage("You selected a kit called '" + k.getName() + "'.");
                                    HAS_KIT.add(p);
                                }
                            }
                        }
                    } 
                }
            }
        }
    }

    @EventHandler
    public void click(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Location l = p.getLocation();
        World w = l.getWorld();
        Action a = e.getAction();
        if(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
            ItemStack is = e.getItem();
            if(!air(is)) {
                if(equal(is, KANGAROO_FIREWORK)) {
                    e.setCancelled(true);
                    boolean use = cooldown(p);
                    if(use) {
                        Vector v = p.getVelocity();
                        Vector va = new Vector(0, 2, 0);
                        v.add(va);
                        p.setVelocity(v);
                        p.setFallDistance(-5.0F);
                        String msg = "You used Kangaroo Jump!";
                        p.sendMessage(msg);
                        addCooldown(p);
                    }
                } else if(equal(is, SNOWMAN_SNOWBALL)) {
                    e.setCancelled(true);
                    boolean use = cooldown(p);
                    if(use) {
                        int am = is.getAmount();
                        is.setAmount(am - 1);
                        Location eye = p.getEyeLocation();
                        Vector v = eye.toVector();
                        Vector d = eye.getDirection();
                        Vector m = d.multiply(2);
                        Vector ad = v.add(m);
                        Location s = ad.toLocation(w, eye.getYaw(), eye.getPitch());
                        w.spawn(s, Fireball.class);
                        addCooldown(p);
                    }
                } else if(equal(is, VIPER_WOOL)) {
                    e.setCancelled(true);
                    boolean use = cooldown(p);
                    if(use) {
                        List<Entity> ent = p.getNearbyEntities(5, 5, 5);
                        for(Entity en : ent) {
                            if(en instanceof LivingEntity) {
                                LivingEntity le = (LivingEntity) en;
                                PotionEffectType P = PotionEffectType.POISON;
                                PotionEffectType B = PotionEffectType.BLINDNESS;
                                PotionEffect pe = new PotionEffect(P, 9, 5);
                                PotionEffect be = new PotionEffect(B, 9, 5);
                                le.addPotionEffect(pe);
                                le.addPotionEffect(be);
                            }
                        }
                        addCooldown(p);
                    }
                } else if(equal(is, IRONMAN_FEATHER)) {
                    e.setCancelled(true);
                    boolean use = cooldown(p);
                    if(use) {
                        p.setAllowFlight(true);
                        p.setFlying(true);
                        Bukkit.getScheduler().runTaskLater(Core.INSTANCE, new Runnable() {
                            public void run() {
                                p.setAllowFlight(false);
                                p.setFlying(false);
                                String msg = "Critical battery... shutting off boosters...";
                                p.sendMessage(msg);
                            }
                        }, 200L);
                        p.sendMessage("Woooosh!");
                        addCooldown(p);
                    }
                }
            }
        }
    }

    @EventHandler
    public void hulk(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        if(e.isSneaking()) {HULK_SMASH.add(p);}
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void hulk(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if(p.isSneaking() && p.isOnGround() && HULK_SMASH.contains(p)) {
            PlayerInventory pi = p.getInventory();
            ItemStack is = pi.getItemInHand();
            if(!air(is) && is.equals(HULK_SWORD)) {
                if(cooldown(p)) {
                    PotionEffectType pet = PotionEffectType.DAMAGE_RESISTANCE;
                    PotionEffect pe = new PotionEffect(pet, 2, 255);
                    Location l = p.getLocation();
                    World w = l.getWorld();
                    p.addPotionEffect(pe);
                    w.createExplosion(l.getX(), l.getY(), l.getZ(), 5.0F, false, false);
                    HULK_SMASH.remove(p);
                    addCooldown(p);
                } else HULK_SMASH.remove(p);
            } else HULK_SMASH.remove(p);
        }
    }

    @EventHandler
    public void die(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if(HAS_KIT.contains(p)) HAS_KIT.remove(p);
    }
    
    private Inventory gui(Player p, int page) {
        ConfigKits.loadKits();
        List<String> kits = ConfigKits.getStringKits();
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
                Kit k = ConfigKits.getKit(s);
                ItemStack icon = k.getIcon();
                if(KitsUtil.air(icon)) icon = new ItemStack(Material.STONE_SWORD);
                i.setItem(j, icon);
                j++;
            }
            return i;
        }
    }

    private Inventory blank(int page, int end) {
        List<String> list = ConfigKits.getStringKits();
        Inventory i = Bukkit.createInventory(null, 45, TITLE);
        
        if(page != 1) i.setItem(36, BACK);
        if(list.size() > end) i.setItem(44, NEXT);
        return i;
    }
}