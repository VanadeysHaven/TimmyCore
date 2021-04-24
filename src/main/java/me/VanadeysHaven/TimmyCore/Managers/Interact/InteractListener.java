package me.VanadeysHaven.TimmyCore.Managers.Interact;

import me.VanadeysHaven.TimmyCore.Data.Profiles.User.CorePlayer;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.ProfileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    private final static ProfileManager pm = ProfileManager.getInstance();

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        CorePlayer cp = pm.getUser(event.getPlayer());
        if(cp.getPendingInteract() != null) if(cp.getPendingInteract().execute(event))
            cp.setPendingInteract(null);
    }

}
