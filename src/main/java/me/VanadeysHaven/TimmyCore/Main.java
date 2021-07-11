package me.VanadeysHaven.TimmyCore;

import github.scarsz.discordsrv.DiscordSRV;
import me.VanadeysHaven.TimmyCore.Commands.*;
import me.VanadeysHaven.TimmyCore.Data.Database.HikariManager;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.Currencies.Currency;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.ProfileManager;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.Settings.Setting;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.Stats.Stat;
import me.VanadeysHaven.TimmyCore.Listeners.*;
import me.VanadeysHaven.TimmyCore.Managers.Interact.InteractListener;
import me.VanadeysHaven.TimmyCore.Packages.Discord.DiscordReady;
import me.VanadeysHaven.TimmyCore.Packages.Rank.RankCommand;
import me.VanadeysHaven.TimmyCore.Packages.Warp.WarpCommand;
import me.VanadeysHaven.TimmyCore.Timers.OneMinuteTimer;
import me.VanadeysHaven.TimmyCore.Timers.TenMinuteTimer;
import me.VanadeysHaven.TimmyCore.Utilities.Reload.ReloadManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static final ProfileManager pm = ProfileManager.getInstance();
    private static final DiscordReady discordReadyListener = new DiscordReady();

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
        Stat.saveToDatabase();
        Currency.saveToDatabase();

        getLogger().info("Registering listeners...");
        ServerPingListener ping = new ServerPingListener();
        registerEvent(new DeathListener(), new JoinQuitListener(), new ChatListener(), ping, new EntityExplodeListener(), new BackCommand(), new InteractListener(),
                new ButtonListener());
        DiscordSRV.api.subscribe(discordReadyListener);

        getLogger().info("Registering commands");
        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("nick").setExecutor(new NicknameCommand());
        getCommand("rank").setExecutor(new RankCommand());
        getCommand("pronouns").setExecutor(new PronounsCommand());
        getCommand("fw").setExecutor(new FireworkCommand());
        getCommand("back").setExecutor(new BackCommand());
        getCommand("colorit").setExecutor(new ColorCommand());
        getCommand("pay").setExecutor(new PayCommand());
        getCommand("tp").setExecutor(TeleportCommand.getInstance());
        getCommand("w").setExecutor(WhisperCommand.getInstance());
        getCommand("note").setExecutor(new NoteCommand());
        getCommand("tcreload").setExecutor(new ReloadCommand());
//        getCommand("r").setExecutor(WhisperCommand.getInstance());


        getLogger().info("Loading players...");
        for(Player p : Bukkit.getOnlinePlayers())
            pm.getUser(p); //We just need to load here, nothing else.

        getLogger().info("Starting timers...");
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new OneMinuteTimer(), 0L, 1200L);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new TenMinuteTimer(), 12000L, 12000L);

        getLogger().info("Registering reloadables...");
        ReloadManager.getInstance().add(ping);
    }

    @Override
    public void onDisable() {
        getLogger().info("Shutting down...");
        pm.unload();
        DiscordSRV.api.unsubscribe(discordReadyListener);
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
