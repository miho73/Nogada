package com.github.miho73.nogada.command;

import org.bukkit.command.CommandSender;

public interface CommandExecutor {
    void Execute(CommandSender sender, String label, String args[]);
}
