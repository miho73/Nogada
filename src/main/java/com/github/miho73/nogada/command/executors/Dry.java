package com.github.miho73.nogada.command.executors;

import com.github.miho73.nogada.Root;
import com.github.miho73.nogada.command.CommandExecutor;
import com.github.miho73.nogada.lib.PointCalc;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public class Dry implements CommandExecutor {
    @Override
    public boolean Execute(CommandSender sender, String label, String[] args) {
        if(!PointCalc.NotNull(Root.Point1, Root.Point2)) {
            sender.sendMessage(ChatColor.YELLOW + "You must set the region first!");
        }
        Location[] sortX = PointCalc.sortX(Root.Point1, Root.Point2);
        Location[] sortY = PointCalc.sortY(Root.Point1, Root.Point2);
        Location[] sortZ = PointCalc.sortZ(Root.Point1, Root.Point2);
        World world = sortX[0].getWorld();
        for(int i = sortX[0].getBlockX(); i<=sortX[1].getBlockX(); i++) {
            for (int j = sortY[0].getBlockY(); j <= sortY[1].getBlockY(); j++) {
                for (int k = sortZ[0].getBlockZ(); k <= sortZ[1].getBlockZ(); k++) {
                    if(world.getBlockAt(i, j, k).getType() == Material.WATER) {
                        world.getBlockAt(i, j, k).setType(Material.AIR);
                    }
                }
            }
        }
        return true;
    }
}
