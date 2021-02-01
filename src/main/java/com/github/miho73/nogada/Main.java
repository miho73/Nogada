package com.github.miho73.nogada;

import com.github.miho73.nogada.command.CommandRunner;
import com.github.miho73.nogada.lib.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Event(), this);
        Logger.Info("Nogada plugin is enabled");
    }
    @Override
    public void onDisable() {
        Logger.Info("Nogada plugin is Disabled");
    }

    private CommandRunner runner = new CommandRunner();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return runner.Run(sender, label, args);
    }
}
