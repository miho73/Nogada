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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Objects;
import java.util.Vector;

public class Flat implements CommandExecutor {
    @Override
    public boolean Execute(CommandSender sender, String label, String[] args) {
        if(!PointCalc.NotNull(Root.Point1, Root.Point2)) {
            sender.sendMessage(ChatColor.YELLOW + "You must set the region first!");
        }
        else {
            if(args.length != 1) {
                sender.sendMessage(ChatColor.YELLOW + "Usage: /flat [target altitude]");
                return true;
            }
            if(!Root.DidEstimate && !sender.isOp()) {
                sender.sendMessage(ChatColor.YELLOW + "You must get all-green in estimate first.\nType \"/estimate [target altitude]\" to get estimate");
                return true;
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
                //+: will give, -: will take
                Hashtable<Material, Integer> Required = new Hashtable<>();
                Required.put(Material.DIRT, 0);
                Required.put(Material.COBBLESTONE, 0);
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
                            }, (Objects.requireNonNull(Root.Point1.getWorld())));
                            if(volume == SpaceBFS.BLOCK_THRESHOLD) {
                                FlagSetBlockOnAltitude = false;
                                break;
                            }
                        }
                        if(FlagSetBlockOnAltitude) {
                            assert world != null;
                            Material TargetAltitudeBlk = world.getBlockAt(i, altitude, k).getType();
                            if(TargetAltitudeBlk != Material.DIRT && TargetAltitudeBlk != Material.GRASS_BLOCK && TargetAltitudeBlk != Material.SAND) {
                                world.getBlockAt(i, altitude, k).setType(Material.DIRT);
                                Required.put(Material.DIRT, Required.get(Material.DIRT)-1);
                            }
                            for(int y = world.getHighestBlockYAt(i, k); y > altitude; y--) {
                                Block pointer = world.getBlockAt(i, y, k);
                                Material PointMaterial = pointer.getType();
                                PointMaterial = MaterialMatcher(PointMaterial);
                                if(Required.containsKey(PointMaterial)) Required.put(PointMaterial, Required.get(PointMaterial)+1);
                                else Required.put(pointer.getType(), +1);
                                pointer.setType(Material.AIR);
                            }
                            for(int y = altitude-1; y >= altitude-15; y--) {
                                if(world.getBlockAt(i, y, k).getType() == Material.AIR || world.getBlockAt(i, y, k).getType() == Material.WATER || world.getBlockAt(i, y, k).getType() == Material.LAVA) {
                                    world.getBlockAt(i, y, k).setType(Material.STONE);
                                    Required.put(Material.COBBLESTONE, Required.get(Material.COBBLESTONE)-1);
                                }
                            }
                        }
                        //If it is above the sea, leave it as it was
                    }
                }
                if(!sender.isOp()) {
                    Enumeration<Material> keys = Required.keys();
                    ItemStack[] diffSub;
                    ItemStack[] diffAdd;
                    Vector<ItemStack> AddTmp = new Vector<>();
                    Vector<ItemStack> SubTmp = new Vector<>();
                    while(keys.hasMoreElements()) {
                        Material met = keys.nextElement();
                        int blks = Required.get(met);
                        if(Required.get(met) >= 0){
                            AddTmp.add(new ItemStack(met, blks));
                        }
                        if(Required.get(met) < 0){
                            SubTmp.add(new ItemStack(met, -blks));
                        }
                    }
                    Player sentPlayer = (Player)sender;
                    diffAdd = new ItemStack[AddTmp.size()];
                    diffSub = new ItemStack[SubTmp.size()];
                    diffAdd = AddTmp.toArray(diffAdd);
                    diffSub = SubTmp.toArray(diffSub);
                    sentPlayer.getInventory().addItem(diffAdd);
                    sentPlayer.getInventory().removeItem(diffSub);
                }
                sender.sendMessage(ChatColor.LIGHT_PURPLE+"Well done!");
                Root.DidEstimate = false;
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Cannot parse altitude: " + args[0]);
            }
        }
        return true;
    }
    private Material MaterialMatcher(Material origin) {
        return switch (origin) {
            case STONE -> Material.COBBLESTONE;
            case GRASS_BLOCK -> Material.DIRT;
            case COAL_ORE -> Material.COAL;
            case LAPIS_ORE -> Material.LAPIS_LAZULI;
            case DIAMOND_ORE -> Material.DIAMOND;
            default -> origin;
        };
    }
}
