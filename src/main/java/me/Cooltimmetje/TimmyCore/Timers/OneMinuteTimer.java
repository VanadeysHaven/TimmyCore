package me.Cooltimmetje.TimmyCore.Timers;

import me.Cooltimmetje.TimmyCore.Data.Profiles.User.CorePlayer;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.ProfileManager;

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
