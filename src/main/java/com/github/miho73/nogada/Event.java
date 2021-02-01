package com.github.miho73.nogada;

import com.github.miho73.nogada.lib.PointCalc;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Event implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.WOODEN_AXE) {
                event.setCancelled(true);
                if (PointCalc.CompareLocation(Root.Point1, event.getClickedBlock().getLocation())) {
                    return;
                }
                else {
                    Root.Point1 = event.getClickedBlock().getLocation();
                    event.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE +
                            "First point set to (" + (int) Root.Point1.getX() + ", " + (int) Root.Point1.getY() + ", " + (int) Root.Point1.getZ() + ")");
                }
            }
        }
        else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.WOODEN_AXE) {
                event.setCancelled(true);
                if (PointCalc.CompareLocation(Root.Point2, event.getClickedBlock().getLocation())) {
                    return;
                }
                else {
                    Root.Point2 = event.getClickedBlock().getLocation();
                    event.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE +
                            "Second point set to (" + (int) Root.Point2.getX() + ", " + (int) Root.Point2.getY() + ", " + (int) Root.Point2.getZ() + ")");
                }
            }
        }
    }
}
