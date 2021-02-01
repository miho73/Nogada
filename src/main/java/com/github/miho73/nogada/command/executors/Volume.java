package com.github.miho73.nogada.command.executors;

import com.github.miho73.nogada.Root;
import com.github.miho73.nogada.command.CommandExecutor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Volume implements CommandExecutor {
    @Override
    public boolean Execute(CommandSender sender, String label, String[] args) {
        if(Root.Point1 == null || Root.Point2 == null) {
            sender.sendMessage(ChatColor.YELLOW + "You must set the region first!");
        }
        else {
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "" +
                (Math.abs((int)Root.Point2.getX()-(int)Root.Point1.getX())+1) *
                (Math.abs((int)Root.Point2.getY()-(int)Root.Point1.getY())+1) *
                (Math.abs((int)Root.Point2.getZ()-(int)Root.Point1.getZ())+1)
            + " blocks are in the region");
        }
        return true;
    }
}
