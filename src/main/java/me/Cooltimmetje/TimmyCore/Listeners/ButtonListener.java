package me.Cooltimmetje.TimmyCore.Listeners;

import me.Cooltimmetje.TimmyCore.Main;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.FireworkMeta;

public class ButtonListener implements Listener {

    @EventHandler
    public void onButton(PlayerInteractEvent event){
        if(event.getClickedBlock() == null) return;
        if(!event.getClickedBlock().getType().toString().contains("BUTTON")) return;
        World world = Bukkit.getWorld("world");
        Location location = new Location(world, -71, 63, 62);
        if(!event.getClickedBlock().getLocation().equals(location)) return;
        Location spawnLoc = location.clone().add(0.5, 0, 0.5);
        Firework fw = (Firework) world.spawnEntity(spawnLoc, EntityType.FIREWORK);
        FireworkMeta meta = fw.getFireworkMeta();
        meta.setPower(1);
        FireworkEffect.Builder builder = FireworkEffect.builder();
        builder.trail(true).flicker(true).withColor(Color.RED).withFade(Color.BLACK).with(FireworkEffect.Type.BURST);
        meta.addEffect(builder.build());
        fw.setFireworkMeta(meta);

        Main.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
            fw.detonate();
            world.strikeLightning(spawnLoc);
        }, 3L);

    }

}
