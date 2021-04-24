package me.VanadeysHaven.TimmyCore.Listeners;

import me.VanadeysHaven.TimmyCore.Data.Profiles.User.ProfileManager;
import me.VanadeysHaven.TimmyCore.Packages.Rank.Rank;
import me.VanadeysHaven.TimmyCore.Utilities.StringUtilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class ChatListener implements Listener {

    private static final ProfileManager pm = ProfileManager.getInstance();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Rank rank = pm.getUser(event.getPlayer()).getSettings().getRank();
        String rankTag = "&8[&" + rank.getColorCode() + rank.getRankName() + "&8]";
        event.setFormat(StringUtilities.colorify(rankTag + " %1$s&r &8» &f%2$s"));
    }

}
