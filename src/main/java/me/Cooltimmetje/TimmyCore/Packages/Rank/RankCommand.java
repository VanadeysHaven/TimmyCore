package me.Cooltimmetje.TimmyCore.Packages.Rank;

import me.Cooltimmetje.TimmyCore.Data.Profiles.User.CorePlayer;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.ProfileManager;
import me.Cooltimmetje.TimmyCore.Utilities.MessageUtilities;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class RankCommand implements CommandExecutor {

    private static final ProfileManager pm = ProfileManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Rank rank;
        Player p;
        CorePlayer cp;
        if(!sender.isOp()){
            MessageUtilities.sendMessage(sender, "Rank", "&aYou do not have permission to do this." , true);
            return false;
        }
        if(args.length < 2) {
            MessageUtilities.sendMessage(sender, "Rank", "&aInvalid usage: &b/rank <name> <newrank>", true);
            return false;
        }
        p = Bukkit.getPlayer(args[0]);
        if(p == null){
            MessageUtilities.sendMessage(sender, "Rank", "&aPlayer &b" + args[0] + " &adoes not exist or is not online.", true);
            return false;
        }
        try {
            rank = Rank.valueOf(args[1].toUpperCase().replace("-", "_"));
        } catch (IllegalArgumentException e){
            MessageUtilities.sendMessage(sender, "Rank", "&aRank &b" + args[1] + " &adoes not exist.", true);
            return false;
        }
        cp = pm.getUser(p);

        Rank oldRank = cp.getSettings().getRank();
        cp.getSettings().setRank(rank);
        cp.updateAppearance();
        MessageUtilities.sendMessage(sender, "Rank", "&aPlayer " + p.getDisplayName() + " &a's rank has been changed from &" + oldRank.getColorCode() + oldRank.getRankName() + " &ato &" + rank.getColorCode() + rank.getRankName() + "&a!");
        MessageUtilities.sendMessage(p, "Rank", "&aOperator &b" + sender.getName() + " &ahas changed your rank from &" + oldRank.getColorCode() + oldRank.getRankName() + " &ato &" + rank.getColorCode() + rank.getRankName() + "&a!");

        return true;
    }
}
