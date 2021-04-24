package me.VanadeysHaven.TimmyCore.Commands;

import me.VanadeysHaven.TimmyCore.Utilities.MessageUtilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public final class NoteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        StringBuilder sb = new StringBuilder();
        for(String s : args)
            sb.append(" ").append(s);

        MessageUtilities.sendMessage(sender, "Note", sb.toString().trim());
        return true;
    }

}
