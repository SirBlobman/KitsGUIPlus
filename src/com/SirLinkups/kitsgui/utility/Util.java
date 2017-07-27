package com.SirLinkups.kitsgui.utility;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.Statistic;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.SirLinkups.kitsgui.config.ConfigDatabase;

public class Util {
	protected static final Server SERVER = Bukkit.getServer();
	protected static final ConsoleCommandSender CONSOLE = SERVER.getConsoleSender();
	
	public static String color(String o) {return ChatColor.translateAlternateColorCodes('&', o);}
	public static String strip(String c) {return ChatColor.stripColor(c);}
	public static String[] color(String... ss) {
		for(int i = 0; i < ss.length; i++) {
			String c = color(ss[i]);
			ss[i] = c;
		}
		return ss;
	}
	
	public static String format(Object o, Player p, Object... oo) {
		String s = str(o);
		int kills = p.getStatistic(Statistic.PLAYER_KILLS);
		int deaths = p.getStatistic(Statistic.DEATHS);
		double dkills = kills;
		double ddeath = deaths;
		double kdr = (dkills / ddeath);
		if(kdr == Double.NaN || kdr == 0) kdr = dkills;
		int level = Math.round((kills / 5));
		int next = (5 - (kills % 5));
		int coins = ConfigDatabase.coins(p);
		
		String k = s.replace("%kills%", str(kills));
		String d = k.replace("%deaths%", str(deaths));
		String kd = d.replace("%kdr%", str(kdr));
		String l = kd.replace("%level%", str(level));
		String nl = l.replace("%next_level%", str(next));
		String c = nl.replace("%coins%", str(coins));
		String f = format(c, oo);
		return f;
	}
	
	public static String format(Object o, Object... oo) {
	    String s = str(o);
	    Object[] ss = str(oo);
	    String f = String.format(s, ss);
	    String c = color(f);
	    return c;
	}
	
	public static String[] str(Object... oo) {
	    String[] ss = new String[oo.length];
	    for(int i = 0; i < oo.length; i++) {
	        Object o = oo[i];
	        String s = str(o);
	        ss[i] = s;
	    } return ss;
	}
	
	public static String str(Object o) {
		if(o == null) return "";
		if((o instanceof Short) || (o instanceof Integer) || (o instanceof Long)) {
			Number n = (Number) o;
			long l = n.longValue();
			String s = Long.toString(l);
			if(s.equals("")) return "0";
			return s;
		} else if((o instanceof Float) || (o instanceof Double) || (o instanceof Number)) {
			Number n = (Number) o;
			double d = n.doubleValue();
			String s = Double.toString(d);
			String format = "%,.2f";
			s = String.format(format, d);
			if(s.equals("")) return "0.0";
			return s;
		} else if(o instanceof Boolean) {
			boolean b = (boolean) o;
			String s = b ? "yes" : "no";
			return s;
		} else if(o instanceof InetSocketAddress){
			InetSocketAddress isa = (InetSocketAddress) o;
			String host = isa.getHostString();
			return host;
		} else if(o instanceof String) {
			String s = (String) o;
			return s;
		} else {
			String s = o.toString();
			return s;
		}
	}
	
	public static void print(Object... os) {
		for(Object o : os) {
			String s = str(o);
			String msg = color(s);
			CONSOLE.sendMessage(msg);
		}
	}
	
	public static void broadcast(Object... os) {
		print(os);
		for(Player p : Bukkit.getOnlinePlayers()) {
			for(Object o : os) {
				String s = str(o);
				String msg = color(s);
				p.sendMessage(msg);
			}
		}
	}
	
	@SafeVarargs
	public static <L> List<L> newList(L... ll) {
		List<L> list = new ArrayList<L>();
		for(L l : ll) {
			boolean n = (l == null);
			if(!n) list.add(l);
		}
		return list;
	}
	
	public static <K, V> Map<K, V> newMap() {
		Map<K, V> map = new HashMap<K, V>();
		return map;
	}
}