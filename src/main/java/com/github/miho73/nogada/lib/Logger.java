package com.github.miho73.nogada.lib;

import org.bukkit.Bukkit;

public class Logger {
    public static void Info(String msg) {
        Bukkit.getConsoleSender().sendMessage(msg);
    }
}
