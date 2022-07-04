package me.VanadeysHaven.TimmyCore.Listeners;

import me.VanadeysHaven.TimmyCore.Commands.TeleportCommand;
import me.VanadeysHaven.TimmyCore.Commands.WhisperCommand;
import me.VanadeysHaven.TimmyCore.Data.Database.Query;
import me.VanadeysHaven.TimmyCore.Data.Database.QueryExecutor;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.CorePlayer;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.ProfileManager;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.Settings.Setting;
import me.VanadeysHaven.TimmyCore.Main;
import me.VanadeysHaven.TimmyCore.Utilities.NameUtilities;
import me.VanadeysHaven.TimmyCore.Utilities.StringUtilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;
import java.util.Iterator;

public final class JoinQuitListener implements Listener {

    private static final ProfileManager pm = ProfileManager.getInstance();
    private static final NameUtilities nu = NameUtilities.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        registerPlayer(event.getPlayer().getUniqueId().toString(), event.getPlayer().getName());
        CorePlayer cp = pm.getUser(event.getPlayer());
        event.setJoinMessage(StringUtilities.colorify(StringUtilities.formatMessageWithTag("Join", formatName(cp) + " &2joined &athe game.")));

        Iterator<CorePlayer> players = pm.getAll();
        while(players.hasNext())
            players.next().setPlayerListHeader();

        nu.updateNames();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        CorePlayer cp = pm.getUser(event.getPlayer());
        WhisperCommand.getInstance().logout(cp);
        event.setQuitMessage(StringUtilities.colorify(StringUtilities.formatMessageWithTag("Quit", formatName(cp) + " &cleft &athe game.")));
        pm.unload(event.getPlayer().getUniqueId().toString());

        Main.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
            Iterator<CorePlayer> players = pm.getAll();
            while(players.hasNext())
                players.next().setPlayerListHeader();

            nu.updateNames();
            TeleportCommand.getInstance().logout(event.getPlayer());
            }, 20L);
    }

    public String formatName(CorePlayer cp){
        String s = cp.getSettings().getRank().formatTag() + " " + cp.getPlayer().getDisplayName() + "&r";
        if(!cp.getSettings().getString(Setting.PRONOUNS).equals(""))
            s += " &8[&b" + cp.getSettings().getString(Setting.PRONOUNS) + "&8]";

        return s;
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