package me.VanadeysHaven.TimmyCore.Managers.Confirm;

import me.VanadeysHaven.TimmyCore.Data.Profiles.User.CorePlayer;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.ProfileManager;
import me.VanadeysHaven.TimmyCore.Utilities.MessageUtilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConfirmCommand implements CommandExecutor {

    private static final ProfileManager pm = ProfileManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            MessageUtilities.sendMessage(sender, "Confirm", "This command is only for players.", true);
            return false;
        }
        Player p = (Player) sender;
        CorePlayer cp = pm.getUser(p);
        PendingConfirmation pendingConfirmation = cp.getPendingConfirmation();
        if(pendingConfirmation == null){
            MessageUtilities.sendMessage(sender, "Confirm", "You have no action pending confirmation.", true);
            return true;
        }
        cp.setPendingConfirmation(null);

        if(label.equalsIgnoreCase("confirm"))
            pendingConfirmation.confirm();
        else if(label.equalsIgnoreCase("cancel"))
            pendingConfirmation.cancel();

        return true;
    }

}
