package me.Cooltimmetje.TimmyCore.Commands;

import me.Cooltimmetje.TimmyCore.Utilities.MessageUtilities;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public final class BackCommand implements CommandExecutor, Listener {

    private static final HashMap<String, Location> lastLocations = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("haha no");
            return false;
        }
        Player p = (Player) sender;
        String uuid = p.getUniqueId().toString();

        if(!lastLocations.containsKey(uuid)){
            MessageUtilities.sendMessage(p, "Back", "You have no last location.", true);
            return true;
        }

        p.teleport(lastLocations.get(uuid), PlayerTeleportEvent.TeleportCause.COMMAND);
        MessageUtilities.sendMessage(p, "Back", "You have been teleported back to your last location!");
        return true;
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event){
        if(event.getCause() == PlayerTeleportEvent.TeleportCause.PLUGIN || event.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND
            || event.getCause() == PlayerTeleportEvent.TeleportCause.UNKNOWN)
            lastLocations.put(event.getPlayer().getUniqueId().toString(), event.getFrom());
    }

}
