package me.Cooltimmetje.TimmyCore.Managers.Interact;

import me.Cooltimmetje.TimmyCore.Data.Profiles.User.CorePlayer;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.ProfileManager;
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
