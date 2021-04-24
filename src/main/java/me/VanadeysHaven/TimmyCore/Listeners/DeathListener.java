package me.VanadeysHaven.TimmyCore.Listeners;

import me.VanadeysHaven.TimmyCore.Utilities.MessageUtilities;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public final class DeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        Location location = player.getLocation();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        MessageUtilities.sendMessage(player, "Death", "You died! Your death location is: &l(" + x + "," + y + "," + z + ")");
    }

}
