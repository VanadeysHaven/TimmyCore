package me.Cooltimmetje.TimmyCore.Utilities;

public class StringUtilities {

    public static String colorify(String input){
        return input.replace("&", "§");
    }

    public static String formatMessageWithTag(String tag, String message){
        return "&8[&6" + tag + "&8] » &a" + message;
    }

}
