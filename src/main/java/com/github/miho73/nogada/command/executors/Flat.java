package com.github.miho73.nogada.command.executors;

import com.github.miho73.nogada.Root;
import com.github.miho73.nogada.command.CommandExecutor;
import com.github.miho73.nogada.lib.PointCalc;
import com.github.miho73.nogada.lib.SpaceBFS;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;

import java.util.Hashtable;

public class Flat implements CommandExecutor {
    @Override
    public boolean Execute(CommandSender sender, String label, String[] args) {
        if(!PointCalc.NotNull(Root.Point1, Root.Point2)) {
            sender.sendMessage(ChatColor.YELLOW + "You must set the region first!");
        }
        else {
            if(args.length != 1) {
                sender.sendMessage(ChatColor.YELLOW + "Usage: /flat [target altitude]");
            }
            try {
                int altitude = Integer.parseInt(args[0]);
                World world = Root.Point1.getWorld();
                Location[] sortX = PointCalc.sortX(Root.Point1, Root.Point2);
                Location[] sortY = PointCalc.sortY(Root.Point1, Root.Point2);
                Location[] sortZ = PointCalc.sortZ(Root.Point1, Root.Point2);
                if(sortY[0].getBlockY() > altitude || sortY[1].getBlockY() < altitude) {
                    sender.sendMessage(ChatColor.RED + "Target altitude must be between y coordinate of the region");
                    return true;
                }
                new Lumber().Execute(sender, label, args);
                for(int i = sortX[0].getBlockX(); i<=sortX[1].getBlockX(); i++) {
                    for(int k = sortZ[0].getBlockZ(); k<=sortZ[1].getBlockZ(); k++) {
                        boolean FlagSetBlockOnAltitude = true;
                        //If location is above or block below the sea, do not place the block.
                        for(int j=altitude+1; j>=altitude-10; j--) {
                            int volume = SpaceBFS.VolumeOf(i, j, k, new Material[]{
                                Material.WATER,
                                Material.KELP_PLANT,
                                Material.TALL_SEAGRASS,
                                Material.KELP,
                                Material.SEAGRASS
                            }, (Root.Point1.getWorld()));
                            if(volume == SpaceBFS.BLOCK_THRESHOLD) {
                                FlagSetBlockOnAltitude = false;
                                break;
                            }
                        }
                        if(FlagSetBlockOnAltitude) {
                            world.getBlockAt(i, altitude, k).setType(Material.DIRT);
                            for(int y = world.getHighestBlockYAt(i, k); y > altitude; y--) {
                                world.getBlockAt(i, y, k).setType(Material.AIR);
                            }
                            for(int y = altitude-1; y >= altitude-15; y--) {
                                if(world.getBlockAt(i, y, k).getType() == Material.AIR || world.getBlockAt(i, y, k).getType() == Material.WATER || world.getBlockAt(i, y, k).getType() == Material.LAVA) {
                                    world.getBlockAt(i, y, k).setType(Material.STONE);
                                }
                            }
                        }
                        //If it is above the sea, leave it as it was
                    }
                }
                sender.sendMessage(ChatColor.LIGHT_PURPLE+"Well done!");
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Cannot parse altitude: " + args[0]);
            }
        }
        return true;
    }
}
