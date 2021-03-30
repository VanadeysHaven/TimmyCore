package me.Cooltimmetje.TimmyCore.Commands;

import me.Cooltimmetje.TimmyCore.Data.Profiles.User.CorePlayer;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.ProfileManager;
import me.Cooltimmetje.TimmyCore.Utilities.MessageUtilities;
import me.Cooltimmetje.TimmyCore.Utilities.NameUtilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class WhisperCommand implements TabExecutor {

    private static final NameUtilities nu = NameUtilities.getInstance();
    private static final ProfileManager pm = ProfileManager.getInstance();
    private static WhisperCommand instance;

    public static WhisperCommand getInstance(){
        if(instance == null)
            instance = new WhisperCommand();

        return instance;
    }

    private HashMap<CorePlayer,CorePlayer> lastReply;

    private WhisperCommand() {
        this.lastReply = new HashMap<>();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Haha no...");
            return true;
        }
        Player p = (Player) sender;
        StringBuilder sb = new StringBuilder();
        if(label.equalsIgnoreCase("w")){
            if(args.length < 2) {
                MessageUtilities.sendMessage(p, "Whisper", "Invalid usage! Usage: &b/w <user> <message...>&a.", true);
                return true;
            }

            for(int i=1; i < args.length; i++)
                sb.append(args[i]).append(" ");
            String message = sb.toString().trim();

            Player receiver = nu.resolveName(args[0]);
            if(receiver == null) {
                MessageUtilities.sendMessage(p, "Whisper", "Player &b" + args[0] + " &adoes not exist or is not online.");
                return true;
            }
            CorePlayer cpReceiver = pm.getUser(receiver);
            return sendWhisper(pm.getUser(p), cpReceiver, message);
        } else if(label.equalsIgnoreCase("r")) {
            if(args.length < 1) {
                MessageUtilities.sendMessage(p, "Whisper", "Invalid usage! Usage: &b/r <message...>&a.", true);
                return true;
            }

            for (String arg : args) sb.append(arg).append(" ");
            String message = sb.toString().trim();
            sendReply(pm.getUser(p), message);
        }
        return true;
    }

    public boolean sendWhisper(CorePlayer sender, CorePlayer receiver, String message){
        if(sender.getUuid().equals(receiver.getUuid())){
            MessageUtilities.sendMessage(sender, "whisper", "You cannot whisper yourself!", true);
            return true;
        }
        message = sender.getFullDisplayName() + " &8-> " + receiver.getFullDisplayName() + " &8» &r" + message;
        MessageUtilities.sendMessage(sender, "Whisper", message);
        MessageUtilities.sendMessage(receiver, "Whisper", message);

        lastReply.put(sender, receiver);
        lastReply.put(receiver, sender);

        return true;
    }

    public boolean sendReply(CorePlayer sender, String message){
        if(!lastReply.containsKey(sender) || lastReply.get(sender) == null){
            MessageUtilities.sendMessage(sender, "whisper", "There's nobody to reply to. Send a whisper using &b/w <user> <message...>&a.", true);
            return true;
        }

        return sendWhisper(sender, lastReply.get(sender), message);
    }

    public void logout(CorePlayer p){
        lastReply.remove(p);
        for(CorePlayer cp : lastReply.keySet()){
            if(lastReply.get(cp).getUuid().equals(p.getUuid()))
                lastReply.remove(cp);
        }
    }

    private final List<String> emptyList = new ArrayList<>();

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if (label.equalsIgnoreCase("w"))
                if (args.length == 1)
                    return nu.getAllNames(p, args[0]);
        }

        return emptyList;
    }

}
