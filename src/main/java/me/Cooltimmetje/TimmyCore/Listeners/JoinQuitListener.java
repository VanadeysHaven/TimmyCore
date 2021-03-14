package me.Cooltimmetje.TimmyCore.Listeners;

import me.Cooltimmetje.TimmyCore.Database.Query;
import me.Cooltimmetje.TimmyCore.Database.QueryExecutor;
import me.Cooltimmetje.TimmyCore.Utilities.StringUtilities;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(StringUtilities.colorify(StringUtilities.formatMessageWithTag("Join", event.getPlayer().getDisplayName() + " joined the game.")));
        registerPlayer(event.getPlayer().getUniqueId().toString(), event.getPlayer().getName());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(StringUtilities.colorify(StringUtilities.formatMessageWithTag("Quit", event.getPlayer().getDisplayName() + " left the game.")));
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