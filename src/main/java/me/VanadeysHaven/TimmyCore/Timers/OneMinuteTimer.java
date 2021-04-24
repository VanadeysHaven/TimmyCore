package me.VanadeysHaven.TimmyCore.Timers;

import me.VanadeysHaven.TimmyCore.Data.Profiles.User.CorePlayer;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.ProfileManager;

import java.util.Iterator;

public final class OneMinuteTimer implements Runnable {

    private static final ProfileManager pm = ProfileManager.getInstance();

    @Override
    public void run() {
        try {
            Iterator<CorePlayer> players = pm.getAll();
            while (players.hasNext()){
                CorePlayer cp = players.next();
                cp.runCurrencyTimers();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
