package me.Cooltimmetje.TimmyCore;

import me.Cooltimmetje.TimmyCore.Commands.NicknameCommand;
import me.Cooltimmetje.TimmyCore.Data.Database.HikariManager;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.Settings.Setting;
import me.Cooltimmetje.TimmyCore.Listeners.ChatListener;
import me.Cooltimmetje.TimmyCore.Listeners.DeathListener;
import me.Cooltimmetje.TimmyCore.Listeners.JoinQuitListener;
import me.Cooltimmetje.TimmyCore.Listeners.ServerPingListener;
import me.Cooltimmetje.TimmyCore.Packages.Warp.WarpCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getLogger().info("Loading...");

        getLogger().info("Loading config..."); //TODO: make cleaner
        this.saveDefaultConfig();
        String databaseHost = this.getConfig().getString("mysql.host");
        String databasePort = this.getConfig().getString("mysql.port");
        String databaseName = this.getConfig().getString("mysql.database");
        String databaseUser = this.getConfig().getString("mysql.username");
        String databasePass = this.getConfig().getString("mysql.password");

        getLogger().info("Setting up DB connection...");
        HikariManager.setup(databaseHost, databasePort, databaseName, databaseUser, databasePass);

        getLogger().info("Updating database...");
        Setting.saveToDatabase();

        getLogger().info("Registering listeners...");
        registerEvent(new DeathListener(), new JoinQuitListener(), new ChatListener(), new ServerPingListener());

        getLogger().info("Registering commands");
        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("nick").setExecutor(new NicknameCommand());

//        getLogger().info("Registering crafting recipes...");
//        new GoldSmelting(getServer(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Shutting down...");
        HikariManager.close();
    }

    public void registerEvent(Listener... listeners){
        for(Listener listener : listeners)
            registerEvent(listener);
    }

    public void registerEvent(Listener listener){
        getLogger().info("Registering listener " + listener.toString());
        getServer().getPluginManager().registerEvents(listener, this);
    }

    public static Plugin getPlugin(){
        return plugin;
    }


}
