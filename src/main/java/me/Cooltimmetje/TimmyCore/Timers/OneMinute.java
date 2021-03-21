package me.Cooltimmetje.TimmyCore.Timers;

import me.Cooltimmetje.TimmyCore.Data.Profiles.User.CorePlayer;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.ProfileManager;
import me.Cooltimmetje.TimmyCore.Main;

import java.util.Iterator;

public class OneMinute implements Runnable {

    private static final ProfileManager pm = ProfileManager.getInstance();

    @Override
    public void run() {
        try {
            Main.getPlugin().getLogger().info("Running one minute timer...");
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
