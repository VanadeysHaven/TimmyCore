package me.VanadeysHaven.TimmyCore.Listeners;

import lombok.Getter;
import me.VanadeysHaven.TimmyCore.Main;
import me.VanadeysHaven.TimmyCore.Utilities.StringUtilities;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.material.PressurePlate;

import java.util.List;
import java.util.Random;

public final class ServerPingListener implements Listener {

    private static Random random =  new Random();
    private static String motdFormat;
    private static String name;
    private static String shortName;
    private static List<String> motds;

    public ServerPingListener(){
        FileConfiguration config = Main.getPlugin().getConfig();
        motdFormat = config.getString("motd.format");
        name = config.getString("server.name");
        shortName = config.getString("server.shortName");
        motds = config.getStringList("motd.motds");
    }

    @EventHandler
    public void onPing(ServerListPingEvent event){
        String format = motdFormat.replace("$name", name).replace("$shortName", shortName);
        format = format.replace("$motd", motds.get(random.nextInt(motds.size() - 1)));
        event.setMotd(StringUtilities.colorify(format));
    }

}
