package me.Cooltimmetje.TimmyCore.Utilities;

import org.bukkit.command.CommandSender;

public final class MessageUtilities {

    public static void sendMessage(CommandSender player, String message){
        player.sendMessage(StringUtilities.colorify(message));
    }

    public static void sendMessage(CommandSender player, String tag, String message){
        sendMessage(player, tag, message, false);
    }

    public static void sendMessage(CommandSender player, String tag, String message, boolean isError){
        if(isError) {
            sendMessage(player, StringUtilities.formatMessageWithError(tag, message));
        } else {
            sendMessage(player, StringUtilities.formatMessageWithTag(tag, message));
        }

    }

}
