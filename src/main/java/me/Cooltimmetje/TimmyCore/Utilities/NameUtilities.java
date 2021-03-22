package me.Cooltimmetje.TimmyCore.Utilities;

import me.Cooltimmetje.TimmyCore.Data.Profiles.User.CorePlayer;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.ProfileManager;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.Settings.Setting;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class NameUtilities {

    private static final ProfileManager pm = ProfileManager.getInstance();

    private static NameUtilities instance;

    public static NameUtilities getInstance() {
        if(instance == null)
            instance = new NameUtilities();

        return instance;
    }

    private List<String> names;

    private NameUtilities() {
        names = new ArrayList<>();
        updateNames();
    }

    public Player resolveName(String name){
        name = name.toLowerCase();
        Player p = Bukkit.getPlayer(name);
        if(p != null && p.isOnline())
            return p;

        Iterator<CorePlayer> players = pm.getAll();
        while(players.hasNext()) {
            CorePlayer cp = players.next();
            if(cp.getSettings().getString(Setting.ID_NICK).equalsIgnoreCase(name))
                return cp.getPlayer();
        }

        return null;
    }

    public void updateNames(){
        names.clear();
        Iterator<CorePlayer> players = pm.getAll();
        while(players.hasNext()) {
            CorePlayer cp = players.next();
            names.add(cp.getPlayer().getName());
            names.add(cp.getSettings().getString(Setting.ID_NICK));
        }
    }

    public List<String> getAllNames(Player p, String cur){
        if(names.isEmpty())
            updateNames();

        List<String> copy = new ArrayList<>(names);
        copy.removeIf(s -> !s.startsWith(cur));
        copy.remove(p.getName());
        copy.remove(pm.getUser(p).getSettings().getString(Setting.ID_NICK));

        return copy;
    }

}
