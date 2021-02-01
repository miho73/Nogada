package com.github.miho73.nogada.lib;

import org.bukkit.Location;

public class Compare {
    public static boolean CompareLocation(Location loc1, Location loc2) {
        if(loc1 == null || loc2 == null) return false;
        if(loc1.getZ() == loc2.getZ() && loc1.getX() == loc2.getX() && loc1.getY() == loc2.getY()) {
            return true;
        }
        return false;
    }
}
