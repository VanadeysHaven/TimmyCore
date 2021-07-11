package me.VanadeysHaven.TimmyCore.Commands;

import me.VanadeysHaven.TimmyCore.Data.Profiles.User.CorePlayer;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.Currencies.CurrenciesContainer;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.Currencies.Currency;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.ProfileManager;
import me.VanadeysHaven.TimmyCore.Main;
import me.VanadeysHaven.TimmyCore.Utilities.MessageUtilities;
import me.VanadeysHaven.TimmyCore.Utilities.MiscUtilities;
import me.VanadeysHaven.TimmyCore.Utilities.NameUtilities;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class PayCommand implements TabExecutor {

    private static final NameUtilities nu = NameUtilities.getInstance();
    private static final ProfileManager pm = ProfileManager.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Haha no...");
            return true;
        }

        Player p = (Player) sender;
        CorePlayer cp = pm.getUser(p);
        CurrenciesContainer currencies = cp.getCurrencies();

        if (args.length < 2){
            MessageUtilities.sendMessage(p, "Pay", "Invalid usage! &b/pay <user> <amount>", true);
            return true;
        }

        Player target = nu.resolveName(args[0]);

        if (target == null) {
            MessageUtilities.sendMessage(p, "Pay", "Player &b" + args[0] + " &adoes not exist or is not online.", true);
            return true;
        }

        CorePlayer cpTarget = pm.getUser(target);
        CurrenciesContainer currenciesTarget = cpTarget.getCurrencies();

        if (p.getUniqueId().toString().equals(target.getUniqueId().toString())) {
            MessageUtilities.sendMessage(p, "Pay", "You cannot pay yourself!", true);
            return true;
        }
        if (!MiscUtilities.isInt(args[1])){
            MessageUtilities.sendMessage(p, "Pay", "&b" + args[1] + " &ais not a valid number.", true);
            return true;
        }
        int amount = Integer.parseInt(args[1]);
        if (!currencies.hasEnoughBalance(Currency.COINS, amount)){
            MessageUtilities.sendMessage(p, "Pay", "You do not have enough " + Main.getPlugin().getConfig().getString("currency.currencyName") + " to pay that.", true);
            return true;
        }
        if(amount < 1){
            MessageUtilities.sendMessage(p, "Pay", "The amount must be higher than 0.", true);
            return true;
        }

        currencies.incrementInt(Currency.COINS, amount * -1);
        currenciesTarget.incrementInt(Currency.COINS, amount);

        MessageUtilities.sendMessage(p, "Pay", "You sent &b" + amount + " " + Main.getPlugin().getConfig().getString("currency.currencyName") + " &ato " + cpTarget.getFullDisplayName() + "&a!");
        MessageUtilities.sendMessage(target, "Pay", "You received &b" + amount + " " + Main.getPlugin().getConfig().getString("currency.currencyName") + " &afrom " + cp.getFullDisplayName() + "&a!");
        target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1);

        cp.setPlayerListFooter();
        cpTarget.setPlayerListFooter();

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player)
            if(args.length == 1)
                return nu.getAllNames((Player) sender, args[0]);

        return null;
    }

}
