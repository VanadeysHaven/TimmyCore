package me.Cooltimmetje.TimmyCore.Commands;

import me.Cooltimmetje.TimmyCore.Utilities.CooldownManager;
import me.Cooltimmetje.TimmyCore.Utilities.MessageUtilities;
import me.Cooltimmetje.TimmyCore.Utilities.RNGManager;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FireworkCommand implements CommandExecutor {

    private static final int COOLDOWN = 5;

    private RNGManager random;
    private CooldownManager cm;

    public FireworkCommand(){
        random = new RNGManager();
        cm = new CooldownManager(COOLDOWN);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("haha no");
            return false;
        }
        Player p = (Player) sender;

        if(cm.isOnCooldown(p.getUniqueId().toString())){
            try {
                MessageUtilities.sendMessage(p, "Firework",
                        "You must wait &b" + ((int) cm.getTimeRemaining(p.getUniqueId().toString()) / 1000) + " seconds &abefore launching your next firework!", true);
            } catch (IllegalStateException ignored){}
            return true;
        }

        Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
        fw.setFireworkMeta(fillMeta(fw.getFireworkMeta()));
        if(args.length >= 1)
            if(args[0].equalsIgnoreCase("ride")) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 10, true, false, true));
                fw.addPassenger(p);
            }


        MessageUtilities.sendMessage(p, "Firework", "Woosh...");
        cm.startCooldown(p.getUniqueId().toString());
        return true;
    }


    public FireworkMeta fillMeta(FireworkMeta meta){
        int amountOfEffects = random.integer(1, 3);
        for(int i = 0; i < amountOfEffects; i++)
            meta.addEffect(generateEffect());

        meta.setPower(random.integer(1,3));
        return meta;
    }

    public FireworkEffect generateEffect(){
        FireworkEffect.Builder builder = FireworkEffect.builder();
        builder.flicker(random.integer(1,2) == 1); //Determine flicker, train and effect
        builder.trail(random.integer(1,2) == 1);
        FireworkEffect.Type[] effects = FireworkEffect.Type.values();
        builder.with(effects[random.integer(0, effects.length - 1)]);


        int amountOfColors = random.integer(1,3); //Determine amount of primary colors
        Color[] colors = new Color[amountOfColors];
        for(int i = 0; i < amountOfColors; i++)
            colors[i] = getColor(random.integer(1,17));
        builder.withColor(colors);

        if(random.integer(1,2) == 1) //Determine fade color
            builder.withFade(getColor(random.integer(1,17)));

        return builder.build();
    }

    private Color getColor(int i) {
        switch (i) {
            case 1:
                return Color.AQUA;
            case 2:
                return Color.BLACK;
            case 3:
                return Color.BLUE;
            case 4:
                return Color.FUCHSIA;
            case 5:
                return Color.GRAY;
            case 6:
                return Color.GREEN;
            case 7:
                return Color.LIME;
            case 8:
                return Color.MAROON;
            case 9:
                return Color.NAVY;
            case 10:
                return Color.OLIVE;
            case 11:
                return Color.ORANGE;
            case 12:
                return Color.PURPLE;
            case 13:
                return Color.RED;
            case 14:
                return Color.SILVER;
            case 15:
                return Color.TEAL;
            case 16:
                return Color.WHITE;
            case 17:
                return Color.YELLOW;
        }

        return null;
    }

}
