package me.Cooltimmetje.TimmyCore.Commands;

import me.Cooltimmetje.TimmyCore.Data.Profiles.User.CorePlayer;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.ProfileManager;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.Settings.Setting;
import me.Cooltimmetje.TimmyCore.Utilities.MessageUtilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NicknameCommand implements CommandExecutor {

    private static final ProfileManager pm = ProfileManager.getInstance();
    private static final int MAX_LENGTH = 16;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Haha... NO.");
            return false;
        }
        Player p = (Player) sender;
        CorePlayer cp = pm.getUser(p);

        if(args.length < 1){
            MessageUtilities.sendMessage(p, "Nick", "&aPlease specify a nickname.", true);
            return false;
        }
        if(args[0].length() > MAX_LENGTH){
            MessageUtilities.sendMessage(p, "Nick", "&aYour nickname is too long, it may not be longer than &b" + MAX_LENGTH + " &acharacters.", true);
            return false;
        }

        if(args[0].equalsIgnoreCase("off")) {
            cp.getSettings().setString(Setting.NICKNAME, "");
            MessageUtilities.sendMessage(p, "Nick", "&aYour nickname has been turned &coff&a.");
        } else {
            cp.getSettings().setString(Setting.NICKNAME, args[0]);
            MessageUtilities.sendMessage(p, "Nick", "&aYour nickname has been updated to &b" + args[0]);
        }

        cp.updateDisplayName();

        return true;
    }

}
