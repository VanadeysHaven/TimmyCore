package me.Cooltimmetje.TimmyCore.Utilities;

import org.bukkit.entity.Player;

public class MessageUtilities {

    public static void sendMessage(Player player, String message){
        player.sendMessage(StringUtilities.colorify(message));
    }

    public static void sendMessage(Player player, String tag, String message){
        sendMessage(player, StringUtilities.formatMessageWithTag(tag, message));
    }

}
