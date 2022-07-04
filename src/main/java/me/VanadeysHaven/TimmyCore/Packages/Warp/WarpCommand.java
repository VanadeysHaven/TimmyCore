package me.VanadeysHaven.TimmyCore.Packages.Warp;

import me.VanadeysHaven.TimmyCore.Data.Profiles.User.CorePlayer;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.Currencies.Currency;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.ProfileManager;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.Stats.Stat;
import me.VanadeysHaven.TimmyCore.Main;
import me.VanadeysHaven.TimmyCore.Managers.Confirm.PendingConfirmation;
import me.VanadeysHaven.TimmyCore.Packages.Warp.Exceptions.WarpNotPublicException;
import me.VanadeysHaven.TimmyCore.Utilities.MessageUtilities;
import me.VanadeysHaven.TimmyCore.Utilities.StringUtilities;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class WarpCommand implements TabExecutor {

    private static final WarpManager manager = WarpManager.getInstance();
    private static final ProfileManager pm = ProfileManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Haha... NO.");
            return false;
        }
        Player p = (Player) sender;

        try {
            if(args.length == 1) {
                if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("listown")) return listAllWarps(p, args[0].equalsIgnoreCase("listown"));
                if (args[0].equalsIgnoreCase("reload")) return reload(p);
                if (args[0].equalsIgnoreCase("buy")) return buyWarp(p);
                else return teleportToWarp(p, args[0]);
            } else if(args.length == 2){
                if(args[0].equalsIgnoreCase("create")) return createNewWarp(p, args[1]);
                if(args[0].equalsIgnoreCase("delete")) return deleteWarp(p, args[1]);
            } else if(args.length == 3){
                if(args[0].equalsIgnoreCase("setpublic")) return changePublicState(p, args[1], args[2]);
            }

            MessageUtilities.sendMessage(p, "Warp", "&aInvalid usage! Usage: &b/warp <name> &8- &b/warp <create/delete> <name> &8- &b/warp setpublic <name> <true/false>", true);
            return false;
        } catch (Exception e) {
            MessageUtilities.sendMessage(p, "Warp", e.getMessage(), true);
        }

        return true;
    }

    private boolean buyWarp(Player p) {
        pm.getUser(p).setPendingConfirmation(new Confirm(p));
        return true;
    }

    private final class Confirm implements PendingConfirmation {

        private CorePlayer cp;
        private int cost;

        public Confirm(Player p){
            cp = pm.getUser(p);
            int slotAmount = cp.getStats().getInt(Stat.WARP_SLOTS);
            FileConfiguration config = Main.getPlugin().getConfig();
            cost = config.getInt("warps.cost") + (config.getInt("warps.additionalCost") * slotAmount);

            TextComponent message = new TextComponent(StringUtilities.colorify(StringUtilities.formatMessageWithTag("Warp", "&aAre you sure you want to buy &bone warp slot &afor &b" + cost + " " + Main.getPlugin().getConfig().getString("currency.currencyName") + "&a? ")));
            TextComponent confirm = new TextComponent(StringUtilities.colorify("&8[&2Confirm&8]"));
            confirm.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/confirm"));
            TextComponent cancel = new TextComponent(StringUtilities.colorify("&8[&cCancel&8]"));
            cancel.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cancel"));

            message.addExtra(confirm);
            message.addExtra(" ");
            message.addExtra(cancel);
            cp.getPlayer().spigot().sendMessage(message);
        }

        @Override
        public void confirm() {
            if(!cp.getCurrencies().hasEnoughBalance(Currency.COINS, cost)) {
                MessageUtilities.sendMessage(cp, "Warp", "&aYou do not have enough &b" + Main.getPlugin().getConfig().getString("currency.currencyName") + " &ato buy this!", true);
                return;
            }

            cp.getCurrencies().incrementInt(Currency.COINS, cost * -1);
            cp.getStats().incrementInt(Stat.WARP_SLOTS);
            cp.setPlayerListFooter();
            MessageUtilities.sendMessage(cp, "Warp", "&aYou bought &b1 warp slot &afor &b" + cost + " " + Main.getPlugin().getConfig().getString("currency.currencyName") + "&a!");
        }


        @Override
        public void cancel() {
            MessageUtilities.sendMessage(cp, "Warp", "Purchase cancelled.");
        }
    }

    private boolean listAllWarps(Player p, boolean listOwn){
        MessageUtilities.sendMessage(p, "Warp", "&aAvailable warps: " + manager.formatWarpsForPlayer(p, listOwn));
        return true;
    }

    private boolean teleportToWarp(Player p, String name){ //1
        name = name.toLowerCase();
        Warp warp = manager.getWarp(name);
        if(!warp.isPublic() && !warp.isOwner(p) && !p.isOp())
            throw new WarpNotPublicException(warp.getName());

        p.teleport(warp.getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
        MessageUtilities.sendMessage(p, "Warp", "&aYou have been teleported to warp &b" + warp.getName() + "&a!");
        return true;
    }

    private boolean createNewWarp(Player p, String warp){ //2
        warp = warp.toLowerCase();
        Location location = p.getLocation();
        manager.addWarp(warp, location, p, true);

        String message = MessageFormat.format("&aNew warp &b{0} &acreated at &b({1},{2},{3})&a!", warp, location.getBlockX(), location.getBlockY(), location.getBlockZ());
        MessageUtilities.sendMessage(p, "Warp", message);
        getListContainer().clear();
        pm.getUser(p).setPlayerListFooter();
        return true;
    }

    private boolean deleteWarp(Player p, String warp){ //2
        warp = warp.toLowerCase();
        manager.deleteWarp(warp, p);
        MessageUtilities.sendMessage(p, "Warp", "&aWarp &b" + warp + " &ahas been deleted.");
        getListContainer().clear();
        pm.getUser(p).setPlayerListFooter();
        return true;
    }

    private boolean changePublicState(Player p, String warp, String newPublic){ //3
        warp = warp.toLowerCase();
        boolean newPub = Boolean.parseBoolean(newPublic);
        manager.setWarpPublic(warp, p, newPub);
        MessageUtilities.sendMessage(p, "Warp", "&aWarp &b" + warp + " &ahas been set to " + (newPub ? "&2public" : "&cprivate") + ".");
        getListContainer().clear();
        return true;
    }

    private boolean reload(Player p){
        if(p.isOp()) {
            manager.loadAllWarps();
            getListContainer().clear();
            MessageUtilities.sendMessage(p, "Warp", "&aAll warps have been reloaded.");
            pm.getAll().forEachRemaining(CorePlayer::setPlayerListFooter);
            return true;
        } else {
            MessageUtilities.sendMessage(p, "Warp", "&aYou don't have permission to do this!", true);
            return false;
        }
    }

    private final class ListContainer {

        private HashMap<String, List<String>> tpLists;
        private HashMap<String, List<String>> editLists;

        private ListContainer() {
            tpLists = new HashMap<>();
            editLists = new HashMap<>();
        }

        public List<String> getTpList(Player p, String curArg){
            if(!tpLists.containsKey(p.getUniqueId().toString())){
                List<String> list = new ArrayList<>(manager.listWarpsForPlayer(p, false));
                list.add("create");
                list.add("list");
                list.add("listown");
                list.add("delete");
                list.add("setpublic");
                list.add("buy");
                if(p.isOp())
                    list.add("reload");

                tpLists.put(p.getUniqueId().toString(), list);
            }
            return filter(tpLists.get(p.getUniqueId().toString()), curArg);
        }

        public List<String> getEditList(Player p, String curArg){
            if(!editLists.containsKey(p.getUniqueId().toString())){
                editLists.put(p.getUniqueId().toString(), manager.listWarpsForPlayer(p, !p.isOp()));
            }
            return filter(editLists.get(p.getUniqueId().toString()), curArg);
        }

        public List<String> filter(List<String> listInput, String curArg){
            List<String> list = new ArrayList<>(listInput);
            list.removeIf(s -> !s.startsWith(curArg));
            return list;
        }

        public void clear(){
            tpLists.clear();
            editLists.clear();
        }

    }

    private ListContainer instance;
    private static final List<String> trueFalseList = new ArrayList<>(List.of("true", "false"));
    private static final List<String> emptyList = new ArrayList<>();

    private ListContainer getListContainer(){
        if(instance == null)
            instance = new ListContainer();

        return instance;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if(!(sender instanceof Player)) {
            return list;
        }
        Player p = (Player) sender;
        switch (args.length){
            case 1:
                return getListContainer().getTpList(p, args[0]);
            case 2:
                if(args[0].equalsIgnoreCase("create")) return emptyList;
                if(args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("setpublic")) return getListContainer().getEditList(p, args[1]);
            case 3:
                return trueFalseList;
            default:
                return list;
        }
    }
}
