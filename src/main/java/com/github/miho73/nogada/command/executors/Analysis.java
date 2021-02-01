package com.github.miho73.nogada.command.executors;

import com.github.miho73.nogada.Root;
import com.github.miho73.nogada.lib.PointCalc;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import com.github.miho73.nogada.command.CommandExecutor;

import java.util.*;

public class Analysis implements CommandExecutor {
    @Override
    public boolean Execute(CommandSender sender, String label, String[] args) {
        if(!PointCalc.NotNull(Root.Point1, Root.Point2)) {
            sender.sendMessage(ChatColor.YELLOW + "You must set the region first!");
        }
        else {
            World world = Root.Point1.getWorld();
            Location[] sortX = PointCalc.sortX(Root.Point1, Root.Point2);
            Location[] sortY = PointCalc.sortY(Root.Point1, Root.Point2);
            Location[] sortZ = PointCalc.sortZ(Root.Point1, Root.Point2);
            Hashtable<Material, Integer> Counter = new Hashtable<Material, Integer>();
            for(int i = sortX[0].getBlockX(); i<=sortX[1].getBlockX(); i++) {
                for(int j = sortY[0].getBlockY(); j<=sortY[1].getBlockY(); j++) {
                    for(int k = sortZ[0].getBlockZ(); k<=sortZ[1].getBlockZ(); k++) {
                        Block pointer = world.getBlockAt(i, j, k);
                        if(Counter.containsKey(pointer.getType())) {
                            Counter.put(pointer.getType(), Counter.get(pointer.getType())+1);
                        }
                        else {
                            Counter.put(pointer.getType(), 1);
                        }
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            Set<Material> materials = Counter.keySet();
            materials.forEach(material -> {
                sb.append(material.toString() + ": "+Counter.get(material) + " blocks\n");
            });
            sender.sendMessage(sb.toString());
        }
        return true;
    }
}
