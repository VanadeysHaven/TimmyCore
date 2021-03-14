package me.Cooltimmetje.TimmyCore.Listeners;

import me.Cooltimmetje.TimmyCore.Utilities.StringUtilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        event.setFormat(StringUtilities.colorify("&a%1$s &8» &f%2$s"));
    }

}
