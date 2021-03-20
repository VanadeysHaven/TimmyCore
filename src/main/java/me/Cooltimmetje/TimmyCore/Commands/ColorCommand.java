package me.Cooltimmetje.TimmyCore.Commands;

import me.Cooltimmetje.TimmyCore.Data.Profiles.User.CorePlayer;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.ProfileManager;
import me.Cooltimmetje.TimmyCore.Managers.Interact.PendingInteract;
import me.Cooltimmetje.TimmyCore.Utilities.MessageUtilities;
import me.Cooltimmetje.TimmyCore.Utilities.StringUtilities;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public final class ColorCommand implements CommandExecutor {

    private final static ProfileManager pm = ProfileManager.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("haha no");
            return true;
        }
        Player p = (Player) sender;
        CorePlayer cp = pm.getUser(p);

        if(args.length >= 1) if(args[0].equalsIgnoreCase("cancel")) {
            PendingInteract interact = cp.getPendingInteract();
            if(!(interact instanceof SignInteract)) {
                MessageUtilities.sendMessage(p, "ColorIt", "You have no outstanding color interaction.", true);
                return true;
            }
            cp.setPendingInteract(null);
            MessageUtilities.sendMessage(p, "ColorIt", "Your outstanding color interaction has been cancelled.");
            return true;
        }

        if(p.getInventory().getItemInMainHand().getType() == Material.AIR) {
            cp.setPendingInteract(new SignInteract());
            MessageUtilities.sendMessage(p, "ColorIt", "Right click a sign to apply colors to it! - &oCancel this operation with /colorit cancel.");
            return true;
        }

        int heldSlot = p.getInventory().getHeldItemSlot();
        ItemStack is = p.getInventory().getItem(heldSlot);
        ItemMeta im = is.getItemMeta();
        if(im == null || !im.hasDisplayName()) {
            MessageUtilities.sendMessage(p, "ColorIt", "The display name of this item can't be colored!", true);
            return true;
        }
        im.setDisplayName(StringUtilities.colorify(im.getDisplayName()));
        is.setItemMeta(im);
        p.getInventory().setItem(heldSlot, is);
        MessageUtilities.sendMessage(p, "ColorIt", "The display name of the item in your hand has been colored.");

        return true;
    }

    private class SignInteract implements PendingInteract {

        @Override
        public boolean execute(PlayerInteractEvent event) {
            if(event.getHand() != EquipmentSlot.HAND) return false;
            Block block = event.getClickedBlock();
            BlockState state = block.getState();
            if(!(state instanceof Sign)) return false;
            Sign sign = (Sign) state;
            for(int i = 0; i < 4; i++)
                sign.setLine(i, StringUtilities.colorify(sign.getLine(i)));
            sign.update();

            MessageUtilities.sendMessage(event.getPlayer(), "ColorIt", "The sign has been colored!");
            return true;
        }

    }

}
