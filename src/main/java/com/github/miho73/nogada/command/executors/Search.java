package com.github.miho73.nogada.command.executors;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import com.github.miho73.nogada.command.CommandExecutor;

public class Search implements CommandExecutor {
    @Override
    public void Execute(CommandSender sender, String label, String[] args) {

    }
    public void Usage(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "Cannot understand the command");
    }
}
