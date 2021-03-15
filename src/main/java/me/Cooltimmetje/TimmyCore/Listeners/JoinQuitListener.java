package me.Cooltimmetje.TimmyCore.Listeners;

import me.Cooltimmetje.TimmyCore.Data.Database.Query;
import me.Cooltimmetje.TimmyCore.Data.Database.QueryExecutor;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.ProfileManager;
import me.Cooltimmetje.TimmyCore.Utilities.StringUtilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public final class JoinQuitListener implements Listener {

    private static final ProfileManager pm = ProfileManager.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        registerPlayer(event.getPlayer().getUniqueId().toString(), event.getPlayer().getName());
        pm.getUser(event.getPlayer()); //We only need to load here, nothing else.
        event.setJoinMessage(StringUtilities.colorify(StringUtilities.formatMessageWithTag("Join", event.getPlayer().getDisplayName() + " joined the game.")));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(StringUtilities.colorify(StringUtilities.formatMessageWithTag("Quit", event.getPlayer().getDisplayName() + " left the game.")));
        pm.unload(event.getPlayer().getUniqueId().toString());
    }

    private void registerPlayer(String uuid, String name){
        QueryExecutor qe = null;
        try {
            qe = new QueryExecutor(Query.INSERT_USER).setString(1, uuid).setString(2, name).and(3);
            qe.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert qe != null;
            qe.close();
        }
    }

}