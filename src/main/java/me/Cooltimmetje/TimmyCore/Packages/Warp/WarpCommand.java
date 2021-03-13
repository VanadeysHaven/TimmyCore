package me.Cooltimmetje.TimmyCore.Packages.Warp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Haha... NO.");
            return false;
        }

        Player p = (Player) sender;



        return true;
    }

    private boolean teleportToWarp(Player p, String warp){
        return false;
    }

}
