package me.Cooltimmetje.TimmyCore.Timers;

import me.Cooltimmetje.TimmyCore.Data.Profiles.User.CorePlayer;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.ProfileManager;

import java.util.Iterator;

public final class TenMinuteTimer implements Runnable {

    private static final ProfileManager pm = ProfileManager.getInstance();

    @Override
    public void run() {
        Iterator<CorePlayer> players = pm.getAll();
        while (players.hasNext())
            players.next().save();
    }

}