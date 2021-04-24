package me.VanadeysHaven.TimmyCore.Listeners;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public final class EntityExplodeListener implements Listener {

    @EventHandler
    public void onExplode(EntityExplodeEvent event){
       if(event.getEntityType() == EntityType.CREEPER) {
           event.setCancelled(true);
           Location location = event.getLocation();
           World world = location.getWorld(); assert world != null;
           world.spawnParticle(Particle.EXPLOSION_NORMAL, location, 20, 1, 1, 1);
           world.spawnParticle(Particle.EXPLOSION_LARGE, location, 20, 3, 3, 3);
           world.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1, 0);
       }
    }

}
