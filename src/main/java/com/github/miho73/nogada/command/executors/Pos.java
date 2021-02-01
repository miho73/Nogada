package com.github.miho73.nogada.command.executors;

import com.github.miho73.nogada.Root;
import com.github.miho73.nogada.command.CommandExecutor;
import com.github.miho73.nogada.lib.Compare;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Pos {
    public static class Pos1 implements CommandExecutor {
        @Override
        public void Execute(CommandSender sender, String label, String[] args) {
            if (Compare.CompareLocation(Root.Point1, ((Player)sender).getLocation())) {
                return;
            }
            else {
                Root.Point1 = ((Player)sender).getLocation();
                sender.sendMessage(ChatColor.LIGHT_PURPLE +
                        "First point set to (" + (int) Root.Point1.getX() + ", " + (int) Root.Point1.getY() + ", " + (int) Root.Point1.getZ() + ")");
            }
        }
    }
    public static class Pos2 implements CommandExecutor {
        @Override
        public void Execute(CommandSender sender, String label, String[] args) {
            if (Compare.CompareLocation(Root.Point1, ((Player)sender).getLocation())) {
                return;
            }
            else {
                Root.Point2 = ((Player)sender).getLocation();
                sender.sendMessage(ChatColor.LIGHT_PURPLE +
                        "Second point set to (" + (int) Root.Point2.getX() + ", " + (int) Root.Point2.getY() + ", " + (int) Root.Point2.getZ() + ")");
            }
        }
    }
}
