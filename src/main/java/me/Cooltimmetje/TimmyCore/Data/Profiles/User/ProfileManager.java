package me.Cooltimmetje.TimmyCore.Data.Profiles.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ProfileManager {

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
    }

    public CorePlayer getUser(String uuid){
        logger.info("Requested player profile for uuid " + uuid);
        CorePlayer cp = getPlayer(uuid);

        if(cp == null){ //Doesn't exist, create new
            logger.info("Loading from database...");
            cp = new CorePlayer(uuid);
            players.add(cp);
        }

        return cp;
    }

    public CorePlayer getPlayer(String uuid){
        for(CorePlayer cp : players)
            if(cp.getUuid().equals(uuid))
                return cp;

        return null;
    }

    public void unloadPlayer(String uuid){
        players.remove(getPlayer(uuid));
    }

}