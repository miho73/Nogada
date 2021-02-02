package com.github.miho73.nogada.command.executors;

import com.github.miho73.nogada.Root;
import com.github.miho73.nogada.command.CommandExecutor;
import com.github.miho73.nogada.lib.PointCalc;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class Lumber implements CommandExecutor {
    private List<Material> Leaves = Arrays.asList(
            new Material[]{
                    Material.ACACIA_LEAVES, Material.BIRCH_LEAVES, Material.DARK_OAK_LEAVES,
                    Material.JUNGLE_LEAVES, Material.OAK_LEAVES, Material.SPRUCE_LEAVES
            }
    );
    private List<Material> Logs = Arrays.asList(
            new Material[]{
                    Material.ACACIA_LOG, Material.BIRCH_LOG, Material.DARK_OAK_LOG,
                    Material.JUNGLE_LOG, Material.OAK_LOG, Material.SPRUCE_LOG,
            }
    );

    private Hashtable<Material, Integer> Axe = new Hashtable<>();

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
            int acaciaLeaves = 0, birchLeaves = 0, jungleLeaves = 0, spruceLeaves = 0, oakLeaves = 0, darkoakLeaves = 0;
            int acaciaLogs = 0, birchLogs = 0, jungleLogs = 0, spruceLogs = 0, oakLogs = 0, darkoakLogs = 0;
            for(int i = sortX[0].getBlockX(); i<=sortX[1].getBlockX(); i++) {
                for(int j = sortY[0].getBlockY(); j<=sortY[1].getBlockY(); j++) {
                    for(int k = sortZ[0].getBlockZ(); k<=sortZ[1].getBlockZ(); k++) {
                        Block pointer = world.getBlockAt(i, j, k);
                        Material ptr = pointer.getType();
                        if (ptr == Material.ACACIA_LOG) {
                            acaciaLogs++;
                            pointer.setType(Material.AIR);
                        }
                        else if (ptr == Material.BIRCH_LOG) {
                            birchLogs++;
                            pointer.setType(Material.AIR);
                        }
                        else if (ptr == Material.SPRUCE_LOG) {
                            spruceLogs++;
                            pointer.setType(Material.AIR);
                        }
                        else if (ptr == Material.JUNGLE_LOG) {
                            jungleLogs++;
                            pointer.setType(Material.AIR);
                        }
                        else if (ptr == Material.OAK_LOG) {
                            oakLogs++;
                            pointer.setType(Material.AIR);
                        }
                        else if (ptr == Material.DARK_OAK_LOG) {
                            darkoakLogs++;
                            pointer.setType(Material.AIR);
                        }
                        else if(ptr == Material.ACACIA_LEAVES) {
                            acaciaLeaves++;
                            pointer.setType(Material.AIR);
                        }
                        else if(ptr == Material.BIRCH_LEAVES) {
                            birchLeaves++;
                            pointer.setType(Material.AIR);
                        }
                        else if(ptr == Material.DARK_OAK_LEAVES) {
                            darkoakLeaves++;
                            pointer.setType(Material.AIR);
                        }
                        else if(ptr == Material.OAK_LEAVES) {
                            oakLeaves++;
                            pointer.setType(Material.AIR);
                        }
                        else if(ptr == Material.JUNGLE_LEAVES) {
                            jungleLeaves++;
                            pointer.setType(Material.AIR);
                        }
                        else if(ptr == Material.SPRUCE_LEAVES) {
                            spruceLeaves++;
                            pointer.setType(Material.AIR);
                        }
                    }
                }
            }
            int TotalLog = acaciaLogs + birchLogs + darkoakLogs + oakLogs + jungleLogs + spruceLogs;
            sender.sendMessage(ChatColor.LIGHT_PURPLE
                    + Integer.toString(acaciaLeaves + birchLeaves + darkoakLeaves + oakLeaves + jungleLeaves + spruceLeaves)
                    + " leaves and "
                    + Integer.toString(TotalLog) + " logs were cut.");
            Player sentPlayer = (Player)sender;
            ItemStack[] items = {
                    new ItemStack(Material.APPLE, Math.round((oakLeaves + darkoakLeaves)/200)),
                    new ItemStack(Material.ACACIA_SAPLING, Math.round(acaciaLeaves/20)),
                    new ItemStack(Material.BIRCH_SAPLING, Math.round(birchLeaves/20)),
                    new ItemStack(Material.SPRUCE_SAPLING, Math.round(spruceLeaves/20)),
                    new ItemStack(Material.OAK_SAPLING, Math.round(oakLeaves/20)),
                    new ItemStack(Material.DARK_OAK_SAPLING, Math.round(darkoakLeaves/20)),
                    new ItemStack(Material.JUNGLE_SAPLING, Math.round(jungleLeaves/40)),
                    new ItemStack(Material.ACACIA_LOG, acaciaLogs),
                    new ItemStack(Material.BIRCH_LOG, birchLogs),
                    new ItemStack(Material.OAK_LOG, oakLogs),
                    new ItemStack(Material.DARK_OAK_LOG, darkoakLogs),
                    new ItemStack(Material.JUNGLE_LOG, jungleLogs),
                    new ItemStack(Material.SPRUCE_LOG, spruceLogs)
            };
            sentPlayer.getInventory().addItem(items);
        }
        return true;
    }
}
