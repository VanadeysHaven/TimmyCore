package me.VanadeysHaven.TimmyCore.Data.Profiles.User;

import me.VanadeysHaven.TimmyCore.Utilities.Reload.ReloadManager;
import me.VanadeysHaven.TimmyCore.Utilities.Reload.Reloadable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;

public final class ProfileManager implements Reloadable {

    private static ProfileManager instance;

    public static ProfileManager getInstance(){
        if(instance == null)
            instance = new ProfileManager();

        return instance;
    }

    private static final Logger logger = LoggerFactory.getLogger(ProfileManager.class);

    private ArrayList<CorePlayer> players;

    private ProfileManager(){
        players = new ArrayList<>();
        ReloadManager.getInstance().add(this);
    }

    public CorePlayer getUser(Player p){
//        logger.info("Requested player profile for uuid " + p.getUniqueId().toString());
        CorePlayer cp = getPlayer(p.getUniqueId().toString());

        if(cp == null){ //Doesn't exist, create new
            logger.info("Loading from database...");
            cp = new CorePlayer(p);
            players.add(cp);
        }

        return cp;
    }

    public Iterator<CorePlayer> getAll(){
        return players.iterator();
    }

    private CorePlayer getPlayer(String uuid){
        for(CorePlayer cp : players)
            if(cp.getUuid().equals(uuid))
                return cp;

        return null;
    }

    public void unload(String uuid){
        CorePlayer cp = getPlayer(uuid); assert cp != null;
        cp.unload();
        players.remove(cp);
    }

    public void unload() {
        for(Player p : Bukkit.getOnlinePlayers())
            unload(p.getUniqueId().toString());
    }

    @Override
    public void reload() {
        getAll().forEachRemaining(p -> {
            p.updateAppearance();
            p.setPlayerListFooter();
        });
    }

}