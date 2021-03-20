package me.Cooltimmetje.TimmyCore.Managers.Interact;

import org.bukkit.event.player.PlayerInteractEvent;

public interface PendingInteract {

    boolean execute(PlayerInteractEvent event);

}
