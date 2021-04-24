package me.VanadeysHaven.TimmyCore.Utilities;

public final class StringUtilities {

    public static String colorify(String input){
        return input.replace("&", "§");
    }

    public static String formatMessageWithTag(String tag, String message){
        return formatTag(tag) + "&a" + message;
    }

    public static String formatMessageWithError(String tag, String message){
        return formatTag(tag) + "&c&lERROR! &a" + message;
    }


    public static String formatTag(String tag){
        return "&8[&6" + tag + "&8] » ";
    }

}
