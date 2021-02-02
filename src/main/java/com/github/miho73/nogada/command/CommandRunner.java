package com.github.miho73.nogada.command;

import com.github.miho73.nogada.command.executors.*;
import com.github.miho73.nogada.lib.SpaceBFS;
import org.bukkit.command.CommandSender;

import java.util.Hashtable;

public class CommandRunner {
    public CommandRunner() {
        CommandRegistry.put("analysis", new Analysis());
        CommandRegistry.put("volume", new Volume());
        CommandRegistry.put("pos1", new Pos.Pos1());
        CommandRegistry.put("pos2", new Pos.Pos2());
        CommandRegistry.put("reg_clr", new Pos.ClaerPoint());
        CommandRegistry.put("lumber", new Lumber());
        CommandRegistry.put("flat", new Flat());
        CommandRegistry.put("bfs", new SpaceBFS());
        CommandRegistry.put("dry", new Dry());
        CommandRegistry.put("estimate", new Estimate());
    }

    private Hashtable<String, CommandExecutor> CommandRegistry = new Hashtable<String, CommandExecutor>();
    public boolean Run(CommandSender sender, String label, String[] args) {
        String inst = label.toLowerCase();
        if(CommandRegistry.containsKey(inst)) {
            return CommandRegistry.get(inst).Execute(sender, inst, args);
        }
        else {
            return false;
        }
    }
}
