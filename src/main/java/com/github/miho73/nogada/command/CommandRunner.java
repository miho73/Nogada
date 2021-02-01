package com.github.miho73.nogada.command;

import com.github.miho73.nogada.command.executors.Pos;
import com.github.miho73.nogada.command.executors.Search;
import com.github.miho73.nogada.command.executors.Volume;
import org.bukkit.command.CommandSender;

import java.util.Hashtable;

public class CommandRunner {
    public CommandRunner() {
        CommandRegistry.put("search", new Search());
        CommandRegistry.put("volume", new Volume());
        CommandRegistry.put("pos1", new Pos.Pos1());
        CommandRegistry.put("pos2", new Pos.Pos2());
    }

    private Hashtable<String, CommandExecutor> CommandRegistry = new Hashtable<String, CommandExecutor>();
    public boolean Run(CommandSender sender, String label, String[] args) {
        String inst = label.toLowerCase();
        if(CommandRegistry.containsKey(inst)) {
            CommandRegistry.get(inst).Execute(sender, inst, args);
        }
        else {
            return false;
        }
        return true;
    }
}
