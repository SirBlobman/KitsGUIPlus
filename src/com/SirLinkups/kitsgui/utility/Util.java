package com.SirLinkups.kitsgui.utility;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;

public class Util {
	protected static final Server SERVER = Bukkit.getServer();
	
	public static String color(String o) {return ChatColor.translateAlternateColorCodes('&', o);}
	public static String strip(String c) {return ChatColor.stripColor(c);}
	public static String[] color(String... ss) {
		for(int i = 0; i < ss.length; i++) {
			String c = color(ss[i]);
			ss[i] = c;
		}
		return ss;
	}
	
	public static String str(Object o) {
		if((o instanceof Short) || (o instanceof Integer) || (o instanceof Long)) {
			Number n = (Number) o;
			long l = n.longValue();
			return Long.toString(l);
		} else if((o instanceof Float) || (o instanceof Double) || (o instanceof Number)) {
			Number n = (Number) o;
			double d = n.doubleValue();
			return Double.toString(d);
		} else {
			String s = o.toString();
			return s;
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
}