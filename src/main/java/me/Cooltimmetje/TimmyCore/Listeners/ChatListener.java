package me.Cooltimmetje.TimmyCore.Listeners;

import me.Cooltimmetje.TimmyCore.Data.Profiles.User.ProfileManager;
import me.Cooltimmetje.TimmyCore.Packages.Rank.Rank;
import me.Cooltimmetje.TimmyCore.Utilities.StringUtilities;
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
