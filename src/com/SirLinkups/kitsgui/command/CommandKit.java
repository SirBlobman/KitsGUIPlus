package com.SirLinkups.kitsgui.command;

import static com.SirLinkups.kitsgui.utility.KitsUtil.*;

import com.SirLinkups.kitsgui.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.entity.Horse.Color;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Map;

public class CommandKit implements CommandExecutor, Listener {
	private static Map<Player, Long> cooldown = Util.newMap();
	private static List<Player> HAS_KIT = Util.newList();
	private static List<Player> HULK_SMASH = Util.newList();
	
	//Gui Related
	private static final String TITLE = Util.color("&1&lKits");
	private static final ItemStack AIR = newItem(Material.AIR);
	private static final ItemStack SKULL = newItem(Material.SKULL_ITEM, 1, 0, "");
	private static final ItemStack BARS = newItem(Material.IRON_FENCE, 1, 0, "");
	private static final ItemStack KANGAROO_KIT = newItem(Material.FIREWORK, 1, 0, "&b&lKangaroo");
	private static final ItemStack SWORDSMAN_KIT = newItem(Material.STONE_SWORD, 1, 0, "&b&lSwordsman");
	private static final ItemStack ARCHER_KIT = newItem(Material.BOW, 1, 0, "&b&lArcher");
	private static final ItemStack SNOWMAN_KIT = newItem(Material.SNOW_BALL, 1, 0, "&b&lSnowman");
	private static final ItemStack FISHERMAN_KIT = newItem(Material.FISHING_ROD, 1, 0, "&b&lFisherman");
	private static final ItemStack NINJA_KIT = newItem(Material.NETHER_STAR, 1, 0, "&b&lNinja");
	private static final ItemStack ENDERMAN_KIT = newItem(Material.ENDER_PEARL, 1, 0, "&b&lEnderman");
	private static final ItemStack STRAFE_KIT = newItem(Material.CHAINMAIL_CHESTPLATE, 1, 0, "&b&lStrafe");
	private static final ItemStack HULK_KIT = newItem(Material.STAINED_CLAY, 1, 13, "&b&lHulk");
	private static final ItemStack CACTUS_KIT = newItem(Material.CACTUS, 1, 0, "&b&lCactus");
	private static final ItemStack HORSEMAN_KIT = newSpawnEgg(EntityType.HORSE, "&b&lHorseman");
	private static final ItemStack TROLL_KIT = newHead("Troll", "&b&lTroll");
	private static final ItemStack VIPER_KIT = newItem(Material.WOOL, 1, 15, "&b&lViper");
	
	//Kit Related
	private static final ItemStack KANGAROO_FIREWORK = newItem(Material.FIREWORK, 1, 0, "&2Ultra Jump");
	private static final ItemStack SNOWMAN_SNOWBALL = newItem(Material.SNOW_BALL, 16, 0, "&bUltra Snowball");
	private static final ItemStack HULK_SWORD = newItem(Material.STONE_SWORD, 1, 0, "&2&lHulk Sword");
	private static final ItemStack VIPER_WOOL = newItem(Material.WOOL, 1, 15, "&8&lPoisonous Wool", "&a&oRight click to poison enemies..."); 
	
	@Override
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
					p.openInventory(gui());
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
						PlayerInventory pi = p.getInventory();
						if(equal(is, KANGAROO_KIT)) {
							p.closeInventory();
							ItemStack helm = newItem(Material.IRON_HELMET, 1);
							ItemStack ches = newItem(Material.IRON_CHESTPLATE, 1);
							ItemStack legs = newItem(Material.IRON_LEGGINGS, 1);	
							ItemStack boot = newItem(Material.IRON_BOOTS, 1, 0, "Kangaroo Feet");
							boot.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 32767);
							ItemStack sword = newItem(Material.STONE_SWORD);
							ItemStack soup = newItem(Material.MUSHROOM_SOUP, 24);
							
							ItemStack[] add = new ItemStack[] {helm, ches, legs, boot, sword, soup, KANGAROO_FIREWORK};
							pi.clear();
							pi.addItem(add);
							String msg = Util.color("&cYou selected the Kangaroo kit");
							p.sendMessage(msg);
							HAS_KIT.add(p);
						} else if(equal(is, SWORDSMAN_KIT)) {
							p.closeInventory();
							ItemStack helm = newItem(Material.IRON_HELMET, 1);
							ItemStack ches = newItem(Material.IRON_CHESTPLATE, 1);
							ItemStack legs = newItem(Material.IRON_LEGGINGS, 1);
							ItemStack boot = newItem(Material.IRON_BOOTS, 1);
							ItemStack sword = newItem(Material.STONE_SWORD, 1);
							sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
							ItemStack soup = newItem(Material.MUSHROOM_SOUP, 12);
							
							ItemStack[] add = new ItemStack[] {helm, ches, legs, boot, sword, soup};
							pi.clear();
							pi.addItem(add);
							String msg = Util.color("&cYou selected the Swordsman kit");
							p.sendMessage(msg);
							HAS_KIT.add(p);
						} else if(equal(is, ARCHER_KIT)) {
							p.closeInventory();
							ItemStack helm = newItem(Material.CHAINMAIL_HELMET, 1);
							ItemStack ches = newItem(Material.CHAINMAIL_CHESTPLATE, 1);
							ItemStack legs = newItem(Material.CHAINMAIL_LEGGINGS, 1);
							ItemStack boot = newItem(Material.CHAINMAIL_BOOTS, 1);
							ItemStack sword = newItem(Material.STONE_SWORD, 1);
							ItemStack bow = newItem(Material.BOW, 1);
							bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 2);
							bow.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
							ItemStack arrow = newItem(Material.ARROW, 1);
							ItemStack soup = newItem(Material.MUSHROOM_SOUP, 24);
							
							ItemStack[] add = new ItemStack[] {helm, ches, legs, boot, sword, bow, soup, arrow};
							pi.clear();
							pi.addItem(add);
							String msg = Util.color("&cYou selected the Archer kit");
							p.sendMessage(msg);
							HAS_KIT.add(p);
						} else if(equal(is, SNOWMAN_KIT)) {
							p.closeInventory();
							ItemStack helm = newItem(Material.IRON_HELMET, 1);
							ItemStack ches = newItem(Material.IRON_CHESTPLATE, 1);
							ItemStack legs = newItem(Material.IRON_LEGGINGS, 1);
							ItemStack boot = newItem(Material.CHAINMAIL_BOOTS, 1);
							ItemStack axe = newItem(Material.IRON_AXE, 1);
							
							ItemStack[] add = new ItemStack[] {helm, ches, legs, boot, axe, SNOWMAN_SNOWBALL};
							pi.clear();
							pi.addItem(add);
							String msg = Util.color("&cYou selected the Snowman kit");
							p.sendMessage(msg);
							HAS_KIT.add(p);
						} else if(equal(is, FISHERMAN_KIT)) {
							p.closeInventory();
							ItemStack helm = newItem(Material.IRON_HELMET, 1);
							ItemStack ches = newItem(Material.IRON_CHESTPLATE, 1);
							ItemStack legs = newItem(Material.IRON_LEGGINGS, 1);
							ItemStack boot = newItem(Material.IRON_BOOTS, 1);
							ItemStack soup = newItem(Material.MUSHROOM_SOUP, 24);
							ItemStack pole = newItem(Material.FISHING_ROD, 1);
							
							ItemStack[] add = new ItemStack[] {helm, ches, legs, boot, soup, pole};
							pi.clear();
							pi.addItem(add);
							String msg = Util.color("&cYou selected the Fisherman kit");
							p.sendMessage(msg);
							HAS_KIT.add(p);
						} else if(equal(is, NINJA_KIT)) {
							p.closeInventory();
							ItemStack helm = newItem(Material.IRON_HELMET, 1);
							ItemStack ches = newItem(Material.IRON_CHESTPLATE, 1);
							ItemStack legs = newItem(Material.IRON_LEGGINGS, 1);
							ItemStack boot = newItem(Material.IRON_BOOTS, 1);
							ItemStack star = newItem(Material.NETHER_STAR, 1);
							star.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
							ItemStack soup = newItem(Material.MUSHROOM_SOUP, 20);
							
							ItemStack[] add = new ItemStack[] {helm, ches, legs, boot, soup, star};
							pi.clear();
							pi.addItem(add);
							String msg = Util.color("&cYou selected the Ninja kit");
							p.sendMessage(msg);
							HAS_KIT.add(p);
						} else if(equal(is, ENDERMAN_KIT)) {
							p.closeInventory();
							ItemStack helm = newItem(Material.CHAINMAIL_HELMET, 1);
							ItemStack ches = newItem(Material.CHAINMAIL_CHESTPLATE, 1);
							ItemStack legs = newItem(Material.CHAINMAIL_LEGGINGS, 1);
							ItemStack boot = newItem(Material.CHAINMAIL_BOOTS, 1);
							ItemStack soup = newItem(Material.MUSHROOM_SOUP, 24);
							ItemStack pearl = newItem(Material.ENDER_PEARL, 16);
							ItemStack axe = newItem(Material.IRON_AXE, 1);
							
							ItemStack[] add = new ItemStack[] {helm, ches, legs, boot, axe, soup, pearl};
							pi.clear();
							pi.addItem(add);
							String msg = Util.color("&cYou selected the Enderman kit");
							p.sendMessage(msg);
							HAS_KIT.add(p);
						} else if(equal(is, STRAFE_KIT)) {
							p.closeInventory();
							ItemStack helm = newItem(Material.IRON_HELMET, 1);
							ItemStack ches = newItem(Material.CHAINMAIL_CHESTPLATE, 1);
							ItemStack legs = newItem(Material.IRON_LEGGINGS, 1);
							ItemStack boot = newItem(Material.IRON_BOOTS, 1);
							ItemStack sword = newItem(Material.STONE_SWORD, 1);
							ItemStack soup = newItem(Material.MUSHROOM_SOUP, 24);
							
							ItemStack[] add = new ItemStack[] {helm, ches, legs, boot, sword, soup};
							pi.clear();
							pi.addItem(add);
							String msg = Util.color("&cYou selected the Swordsman kit");
							p.sendMessage(msg);
							
							PotionEffectType fast = PotionEffectType.SPEED;
							PotionEffect pe = new PotionEffect(fast, Integer.MAX_VALUE, 1);
							p.addPotionEffect(pe, true);
							HAS_KIT.add(p);
						} else if(equal(is, HULK_KIT)) {
							p.closeInventory();
							ItemStack helm = newLeather(EquipmentSlot.HEAD, 21, 130, 2, "Hulk Helmet");
							ItemStack ches = newLeather(EquipmentSlot.CHEST, 21, 130, 2, "Hulk Chestplate");
							ItemStack legs = newItem(Material.CHAINMAIL_LEGGINGS, 1);
							ItemStack boot = newItem(Material.IRON_BOOTS, 1);
							ItemStack soup = newItem(Material.MUSHROOM_SOUP, 24);
							
							ItemStack[] add = new ItemStack[] {helm, ches, legs, boot, HULK_SWORD, soup};
							pi.clear();
							pi.addItem(add);
							String msg = Util.color("&cYou selected the Hulk kit");
							p.sendMessage(msg);
							HAS_KIT.add(p);
						} else if(equal(is, CACTUS_KIT)) {
							p.closeInventory();
							ItemStack helm = newLeather(EquipmentSlot.HEAD, 0, 255, 0, "&2Cactus Helmet");
							ItemStack ches = newLeather(EquipmentSlot.CHEST, 0, 255, 0, "&2Cactus Chestplate");
							ItemStack legs = newLeather(EquipmentSlot.LEGS, 0, 255, 0, "&2Cactus Leggings");
							ItemStack boot = newLeather(EquipmentSlot.FEET, 0, 255, 0, "&2Cactus Boots");
							helm.addUnsafeEnchantment(Enchantment.THORNS, 5);
							ches.addUnsafeEnchantment(Enchantment.THORNS, 5);
							legs.addUnsafeEnchantment(Enchantment.THORNS, 5);
							boot.addUnsafeEnchantment(Enchantment.THORNS, 5);
							ItemStack sword = newItem(Material.WOOD_SWORD, 1);
							
							ItemStack[] add = new ItemStack[] {helm, ches, legs, boot, sword};
							pi.clear();
							pi.addItem(add);
							String msg = Util.color("&cYou selected the Cactus kit");
							p.sendMessage(msg);
							HAS_KIT.add(p);
						} else if(equal(is, HORSEMAN_KIT)) {
							p.closeInventory();
							Location l = p.getLocation();
							World w = l.getWorld();
							Horse h = w.spawn(l, Horse.class);
							h.setJumpStrength(20.0D);
							h.setMaxHealth(200.0D);
							HorseInventory hi = h.getInventory();
							hi.setSaddle(newItem(Material.SADDLE, 1));
							hi.setArmor(newItem(Material.DIAMOND_BARDING, 1));
							h.setHealth(200.0D);
							h.setColor(Color.BLACK);
							h.setOwner(p);
							h.setPassenger(p);
							ItemStack sword = newItem(Material.WOOD_SWORD, 1);
							
							pi.clear();
							pi.addItem(sword);
							String msg = Util.color("&cYou selected the Horseman kit");
							p.sendMessage(msg);
							HAS_KIT.add(p);
						} else if(equal(is, TROLL_KIT)) {
							p.closeInventory();		
							ItemStack mask = newHead("Troll", "&fTroll Mask");
							ItemStack web = newItem(Material.WEB, 64);
							ItemStack creeper = newSpawnEgg(EntityType.CREEPER, 16);
							ItemStack lava = newItem(Material.LAVA_BUCKET, 1);
							
							ItemStack[] add = new ItemStack[] {mask, web, creeper, lava};
							pi.clear();
							pi.addItem(add);
							String msg = Util.color("&cYou selected the Troll kit");
							p.sendMessage(msg);
							HAS_KIT.add(p);
						} else if(equal(is, VIPER_KIT)) {
							p.closeInventory();
							ItemStack helm = newLeather(EquipmentSlot.HEAD, 0, 0, 0, "&0Viper Helmet");
							ItemStack ches = newLeather(EquipmentSlot.CHEST, 0, 0, 0, "&0Viper Chestplate");
							ItemStack legs = newLeather(EquipmentSlot.LEGS, 0, 0, 0, "&0Viper Leggings");
							ItemStack boot = newLeather(EquipmentSlot.FEET, 0, 0, 0, "&0Viper Boots");
							
							ItemStack[] add = new ItemStack[] {helm, ches, legs, boot, VIPER_WOOL};
							pi.clear();
							pi.addItem(add);
							String msg = Util.color("&cYou selected the Viper kit");
							p.sendMessage(msg);		
							HAS_KIT.add(p);	
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
	
	private Inventory gui() {
		Inventory i = Bukkit.createInventory(null, 54, TITLE);
		ItemStack[] inv = new ItemStack[] {
			SKULL, BARS, BARS, BARS, BARS, BARS, BARS, BARS, SKULL,
			BARS, KANGAROO_KIT, SWORDSMAN_KIT, ARCHER_KIT, SNOWMAN_KIT, FISHERMAN_KIT, NINJA_KIT, ENDERMAN_KIT, BARS,
			BARS, STRAFE_KIT, HULK_KIT, HORSEMAN_KIT, CACTUS_KIT, TROLL_KIT, VIPER_KIT, AIR, BARS,
			BARS, AIR, AIR, AIR, AIR, AIR, AIR, AIR, BARS,
			BARS, AIR, AIR, AIR, AIR, AIR, AIR, AIR, BARS,
			SKULL, BARS, BARS, BARS, BARS, BARS, BARS, BARS, SKULL
		};
		i.setContents(inv);
		return i;
	}
	
	private boolean cooldown(Player p) {
		if(cooldown.containsKey(p)) {
			long l = cooldown.get(p);
			long c = System.currentTimeMillis();
			long t = l - c;
			int time = (int) (t / 1000L);
			if(time <= 0) {
				cooldown.remove(p);
				return true;
			}
			else {
				String f = Util.color("&cYou must wait &6%1s &cseconds before using another special ability!");
				String error = String.format(f, time);
				p.sendMessage(error);
				return false;
			}
		} else return true;
	}
	
	private void addCooldown(Player p) {
		long c = System.currentTimeMillis();
		long l = c + (50 * 1000L);
		cooldown.put(p, l);
	}
	
	private boolean equal(ItemStack is1, ItemStack is2) {
		if(air(is1) || air(is2)) return false;
		else if(is1 == is2) return true;
		else if(is1.equals(is2)) return true;
		else {
			ItemMeta m1 = is1.getItemMeta();
			ItemMeta m2 = is2.getItemMeta();
			if(m1.hasDisplayName() && m2.hasDisplayName()) {
				String name1 = m1.getDisplayName();
				String name2 = m2.getDisplayName();
				return name1.equals(name2);
			} else {
				Material mat1 = is1.getType();
				Material mat2 = is2.getType();
				return (mat1 == mat2);
			}
		}
	}
}