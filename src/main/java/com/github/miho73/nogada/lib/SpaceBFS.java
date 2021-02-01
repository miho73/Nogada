package com.github.miho73.nogada.lib;

import com.github.miho73.nogada.command.CommandExecutor;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

class Point {
    public int x, y, z;
    public Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Point Calc(int x, int y, int z) {
        return new Point(this.x+x, this.y+y, this.z+z);
    }
}

public class SpaceBFS implements CommandExecutor {
    //For example, if the number of adjacent water blocks are over 500, that is the sea.
    public static int BLOCK_THRESHOLD = 100;
    public static int VolumeOf(int x, int y, int z, Material[] madeOf, World world) {
        int volume = 0;
        Queue<Point> q = new LinkedList<>();
        Vector<Point> DidAccess = new Vector<>();
        List<Material> materials = Arrays.asList(madeOf);
        if(materials.contains(world.getBlockAt(x, y, z).getType())) return 0;
        q.add(new Point(x, y, z));
        DidAccess.add(new Point(x, y, z));
        while(!q.isEmpty()) {
            Point current = q.poll();
            volume++;
            if(volume >= BLOCK_THRESHOLD) break;
            for(int i=-1; i<=1; i++) {
                for(int j=-1; j<=1; j++) {
                    for(int k=-1; k<=1; k++) {
                        if(i==0 && j==0 && k==0) continue;
                        Point pointer = current.Calc(i, j, k);
                        boolean DidAccessFlag = true;
                        for(int p = 0; p <DidAccess.size(); p++) {
                            Point ele = DidAccess.elementAt(p);
                            if(ele.x == pointer.x && ele.y == pointer.y && ele.z == pointer.z) DidAccessFlag = false;
                        }
                        if(materials.contains(world.getBlockAt(pointer.x, pointer.y, pointer.z).getType()) && DidAccessFlag) {
                            q.add(pointer);
                            DidAccess.add(pointer);
                        }
                    }
                }
            }
        }
        return volume;
    }

    @Override
    public boolean Execute(CommandSender sender, String label, String[] args) {
        if(args.length != 3) {
            sender.sendMessage(ChatColor.YELLOW + "Usage: /bfs [x] [y] [z]");
        }
        try {
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            int z = Integer.parseInt(args[2]);
            int volume = VolumeOf(x, y, z, new Material[]{((Player)sender).getWorld().getBlockAt(x, y, z).getType()},
                    ((Player)sender).getWorld());
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "" + volume + " blocks"
                    + (volume == BLOCK_THRESHOLD ? " (Threshold)" : "")
                    + "(" + ((Player)sender).getWorld().getBlockAt(x, y, z).getType() + ")");
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Cannot parse coordinate");
        }
        return true;
    }
}
