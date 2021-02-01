package com.github.miho73.nogada.lib;

import org.bukkit.Location;

public class PointCalc {
    public static boolean CompareLocation(Location loc1, Location loc2) {
        if(!NotNull(loc1, loc2)) {
            return false;
        }
        if(loc1.getZ() == loc2.getZ() && loc1.getX() == loc2.getX() && loc1.getY() == loc2.getY()) {
            return true;
        }
        return false;
    }

    public static Location[] sortX(Location loc1, Location loc2) {
        if(loc1.getX() > loc2.getX()) return new Location[]{loc2, loc1};
        else return new Location[]{loc1, loc2};
    }
    public static Location[] sortY(Location loc1, Location loc2) {
        if(loc1.getY() > loc2.getY()) return new Location[]{loc2, loc1};
        else return new Location[]{loc1, loc2};
    }
    public static Location[] sortZ(Location loc1, Location loc2) {
        if(loc1.getZ() > loc2.getZ()) return new Location[]{loc2, loc1};
        else return new Location[]{loc1, loc2};
    }

    /**
     * Return if region is made
     * @param loc1 Location of first point(Root.Point1)
     * @param loc2 Location of second point(Root.Point2)
     * @return return 'true' is region is made
     */
    public static boolean NotNull (Location loc1, Location loc2) {
        if(loc1 == null || loc2 == null) return false;
        return true;
    }
}
