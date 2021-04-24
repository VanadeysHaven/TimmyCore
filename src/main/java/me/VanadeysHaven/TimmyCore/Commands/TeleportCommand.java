package me.VanadeysHaven.TimmyCore.Commands;

import me.VanadeysHaven.TimmyCore.Data.Profiles.User.CorePlayer;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.ProfileManager;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.Settings.Setting;
import me.VanadeysHaven.TimmyCore.Utilities.MessageUtilities;
import me.VanadeysHaven.TimmyCore.Utilities.NameUtilities;
import me.VanadeysHaven.TimmyCore.Utilities.StringUtilities;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class TeleportCommand implements TabExecutor {

    private static TeleportCommand cmdInstance;

    public static TeleportCommand getInstance() {
        if (cmdInstance == null)
            cmdInstance = new TeleportCommand();

        return cmdInstance;
    }

    private static final ProfileManager pm = ProfileManager.getInstance();
    private static final NameUtilities nu = NameUtilities.getInstance();

    private ArrayList<TeleportRequest> requests;

    private TeleportCommand() {
        requests = new ArrayList<>();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Haha no...");
            return true;
        }
        Player p = (Player) sender;
        CorePlayer cp = pm.getUser(p);
        if(args.length < 1) {
            MessageUtilities.sendMessage(p, "Teleport", "Invalid usage! &b/tp <user> &8- &b/tp accept <user> &8- &b/tp requirerequest <true/false>", true);
            return true;
        }

        switch (args[0].toLowerCase()){
            case "accept":
                return acceptTeleport(cp, args);
            case "requirerequest":
                return changeRequireRequest(cp, args);
            default:
                return attemptTeleport(cp, args);
        }
    }

    private boolean attemptTeleport(CorePlayer cp, String[] args){
        Player target = nu.resolveName(args[0]);
        if(target == null) {
            MessageUtilities.sendMessage(cp, "Teleport", "Player &b" + args[0] + " &adoes not exist or is not online.", true);
            return true;
        }
        if(target.getUniqueId().toString().equals(cp.getPlayer().getUniqueId().toString())) {
            MessageUtilities.sendMessage(cp, "Teleport", "You cannot teleport to yourself!", true);
            return true;
        }
        CorePlayer cpTarget = pm.getUser(target);
        if(args.length >= 2)
            if(args[1].equalsIgnoreCase("-f"))
                if(cp.getPlayer().isOp())
                    return teleport(cp, cpTarget, true);


        if(cpTarget.getSettings().getBoolean(Setting.REQUIRE_TP_REQUEST)) {
            return createRequest(cp, pm.getUser(target));
        } else {
            return teleport(cp, cpTarget);
        }
    }

    private boolean createRequest(CorePlayer cp, CorePlayer target){
        requests.removeIf(req -> req.target.getUniqueId().toString().equals(cp.getPlayer().getUniqueId().toString()));
        new TeleportRequest(cp.getPlayer(), target.getPlayer());
        MessageUtilities.sendMessage(cp, "Teleport", "Sent teleport request to " + target.getFullDisplayName() + "&a!");
        TextComponent message = new TextComponent(StringUtilities.colorify(StringUtilities.formatTag("Teleport") + "&aReceived teleport request from " + cp.getFullDisplayName() + "&a! "));
        TextComponent accept = new TextComponent(StringUtilities.colorify("&8[&2Click to accept&8]"));
        String name = cp.getSettings().getString(Setting.ID_NICK);
        if(name.equals(""))
            name = cp.getPlayer().getName();
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp accept " + name));
        message.addExtra(accept);
        target.getPlayer().spigot().sendMessage(message);
        target.getPlayer().playSound(target.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1);
        return true;
    }

    private boolean acceptTeleport(CorePlayer cp, String[] args){
        if(args.length < 2){
            MessageUtilities.sendMessage(cp, "Teleport", "Invalid usage! &b/tp accept <user>", true);
            return true;
        }
        Player target = nu.resolveName(args[1]);
        if(target == null) {
            MessageUtilities.sendMessage(cp, "Teleport", "Player &b" + args[0] + " &adoes not exist or is not online.", true);
            return true;
        }
        if(target.getUniqueId().toString().equals(cp.getPlayer().getUniqueId().toString())) {
            MessageUtilities.sendMessage(cp, "Teleport", "You cannot teleport to yourself!", true);
            return true;
        }

        CorePlayer cpTarget = pm.getUser(target);
        for (TeleportRequest request : requests) {
            if (request.match(target, cp.getPlayer())) {
                request.delete();
                return teleport(cpTarget, cp);
            }
        }

        MessageUtilities.sendMessage(cp, "Teleport", "No outstanding request found for user &b" + args[1] + "&a!", true);
        return true;
    }

    private boolean teleport(CorePlayer cp, CorePlayer target) {
        return teleport(cp, target, false);
    }

    private boolean teleport(CorePlayer cp, CorePlayer target, boolean force){
        cp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 1));
        cp.getPlayer().teleport(target.getPlayer().getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
        MessageUtilities.sendMessage(cp, "Teleport", "You teleported to " + target.getFullDisplayName() + "&a.");
        if(!force) {
            MessageUtilities.sendMessage(target, "Teleport", cp.getFullDisplayName() + " &ahas teleported to you.");
        }
        return true;
    }

    private boolean changeRequireRequest(CorePlayer cp, String[] args){
        if(args.length < 2) {
            MessageUtilities.sendMessage(cp, "Teleport", "Invalid usage! &b/tp requirerequest <true/false>", true);
            return false;
        }
        cp.getSettings().setBoolean(Setting.REQUIRE_TP_REQUEST, Boolean.parseBoolean(args[1]));
        MessageUtilities.sendMessage(cp, "Teleport", "Require request is now " + (cp.getSettings().getBoolean(Setting.REQUIRE_TP_REQUEST) ? "&2ON" : "&cOFF") + "&a.");
        return true;
    }

    private class TeleportRequest {

        Player requester;
        Player target;

        private TeleportRequest(Player requester, Player target){
            this.requester = requester;
            this.target = target;
            requests.add(this);
        }

        public void delete(){
            requests.remove(this);
        }

        public boolean match(Player requester, Player target){
            return requester.getUniqueId().toString().equals(this.requester.getUniqueId().toString())
                    && target.getUniqueId().toString().equals(this.target.getUniqueId().toString());
        }

    }

    private List<String> listRequests(Player p){
        List<String> ret = new ArrayList<>();
        requests.forEach(req -> {
            if(req.target.getUniqueId().toString().equals(p.getUniqueId().toString())){
                ret.add(req.requester.getName().toLowerCase());
                ret.add(pm.getUser(req.requester).getSettings().getString(Setting.ID_NICK).toLowerCase());
            }
        });
        return ret;
    }

    public void logout(Player p){
        requests.removeIf(req -> req.target.getUniqueId().toString().equals(p.getUniqueId().toString()));
        requests.removeIf(req -> req.requester.getUniqueId().toString().equals(p.getUniqueId().toString()));
    }

    private final class ListContainer {

        private ListContainer() {}

        public List<String> getFirstArg(Player p, String curArg){
            List<String> list = new ArrayList<>(nu.getAllNames(p));
            list.add("accept");
            list.add("requirerequest");

            return filter(list, curArg);
        }

        public List<String> getAccept(Player p, String curArg){
            return filter(listRequests(p), curArg);
        }

        public List<String> filter(List<String> listInput, String curArg){
            List<String> list = new ArrayList<>(listInput);
            list.removeIf(s -> !s.startsWith(curArg));
            return list;
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
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player))
            return emptyList;
        Player p = (Player) sender;

        switch(args.length) {
            case 1:
                return getListContainer().getFirstArg(p, args[0]);
            case 2:
                if(args[0].equalsIgnoreCase("accept")){
                    return getListContainer().getAccept(p, args[1]);
                } else if (args[0].equalsIgnoreCase("requirerequest")) {
                    return trueFalseList;
                }
            default:
                return emptyList;
        }
    }

}
