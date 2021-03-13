package me.Cooltimmetje.TimmyCore;

import me.Cooltimmetje.TimmyCore.CustomReciepes.GoldSmelting;
import me.Cooltimmetje.TimmyCore.Listeners.ChatListener;
import me.Cooltimmetje.TimmyCore.Listeners.DeathListener;
import me.Cooltimmetje.TimmyCore.Listeners.JoinQuitListener;
import me.Cooltimmetje.TimmyCore.Packages.Warp.WarpCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Loading...");
        getLogger().info("Registering listeners...");
        registerEvent(new DeathListener(), new JoinQuitListener(), new ChatListener());

        getLogger().info("Registering commands");
        getCommand("warp").setExecutor(new WarpCommand());

//        getLogger().info("Registering crafting recipes...");
//        new GoldSmelting(getServer(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Shutting down...");
    }

    public void registerEvent(Listener... listeners){
        for(Listener listener : listeners)
            registerEvent(listener);
    }

    public void registerEvent(Listener listener){
        getLogger().info("Registering listener " + listener.toString());
        getServer().getPluginManager().registerEvents(listener, this);
    }


}
