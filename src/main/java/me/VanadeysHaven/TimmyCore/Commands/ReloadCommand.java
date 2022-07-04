package me.VanadeysHaven.TimmyCore.Commands;

import me.VanadeysHaven.TimmyCore.Utilities.MessageUtilities;
import me.VanadeysHaven.TimmyCore.Utilities.Reload.ReloadManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.isOp()){
            MessageUtilities.sendMessage(sender, "Reload", "You do not have permission to do this!", true);
            return true;
        }

        ReloadManager.getInstance().reloadAll();
        MessageUtilities.sendMessage(sender, "Reload", "Config has been reloaded.");
        return true;
    }

}
