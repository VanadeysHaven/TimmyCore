package me.Cooltimmetje.TimmyCore.Commands;

import me.Cooltimmetje.TimmyCore.Data.Profiles.User.CorePlayer;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.ProfileManager;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.Settings.Setting;
import me.Cooltimmetje.TimmyCore.Utilities.MessageUtilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class PronounsCommand implements CommandExecutor {

    private static final int MAX_LENGTH = 16;
    private static final ProfileManager pm = ProfileManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Haha... NO.");
            return false;
        }
        Player p = (Player) sender;
        CorePlayer cp = pm.getUser(p);

        if(args.length < 1) {
            MessageUtilities.sendMessage(sender, "Pronouns", "Please specify your pronouns!", true);
            return false;
        }

        StringBuilder sb = new StringBuilder();
        for(String s : args) sb.append(s).append(" ");
        String pronouns = sb.toString().trim();

        if(pronouns.length() > MAX_LENGTH) {
            MessageUtilities.sendMessage(sender, "Pronouns", "Pronouns may not be longer than &b" + MAX_LENGTH + " &acharacters.", true);
            return false;
        }

        cp.getSettings().setString(Setting.PRONOUNS, pronouns);
        cp.updateAppearance();
        MessageUtilities.sendMessage(sender, "Pronouns", "Your pronouns are now &b" + pronouns + "&a!");

        return true;
    }

}
