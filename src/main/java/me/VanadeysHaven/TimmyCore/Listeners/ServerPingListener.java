package me.VanadeysHaven.TimmyCore.Listeners;

import lombok.Getter;
import me.VanadeysHaven.TimmyCore.Utilities.StringUtilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.List;
import java.util.Random;

public final class ServerPingListener implements Listener {

    private static final String motd = "&8[&61.17&8] » &3&lN&3euro&a&lC&araft\n&3&lN&a&lC &8» &b&o";

    private enum Messages {

        POWERED_BY("Powered by Sparked Host - sparkedhost.com"),
        STIMMING("Stimming, together."),
        PUPPIES("PUPPIES!!!"),
        BLOCKY("Mmm... Blocky..."),
        MEMES("Now with memes!"),
        EST("Est. 1986"),
        CHEESE("Now with cheese!"),
        BEES("BEES!"),
        HI("Hello!"),
        FIRE("In case of fire; yell at Timmy.");

        @Getter private String message;

        Messages(String message){
            this.message = message;
        }


        private static final List<Messages> VALUES = List.of(values());
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        public static Messages random()  {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }
    }

    @EventHandler
    public void onPing(ServerListPingEvent event){
        event.setMotd(StringUtilities.colorify(motd + Messages.random().getMessage()));
    }

}
