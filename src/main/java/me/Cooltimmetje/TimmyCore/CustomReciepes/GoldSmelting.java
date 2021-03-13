package me.Cooltimmetje.TimmyCore.CustomReciepes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import javax.naming.Name;

public class GoldSmelting {

    public GoldSmelting(Server server, Plugin plugin){
        server.addRecipe(new BlastingRecipe(new NamespacedKey(plugin, "gold_helmet_to_ingots"), new ItemStack(Material.GOLD_INGOT, 5), Material.GOLDEN_HELMET, 0, 1500));
        server.addRecipe(new BlastingRecipe(new NamespacedKey(plugin, "gold_chest_to_ingots"), new ItemStack(Material.GOLD_INGOT, 8), Material.GOLDEN_CHESTPLATE, 0, 2400));
        server.addRecipe(new BlastingRecipe(new NamespacedKey(plugin, "gold_leggings_to_ingots"), new ItemStack(Material.GOLD_INGOT, 7), Material.GOLDEN_LEGGINGS, 0, 2100));
        server.addRecipe(new BlastingRecipe(new NamespacedKey(plugin, "gold_boots_to_ingots"), new ItemStack(Material.GOLD_INGOT, 4), Material.GOLDEN_BOOTS, 0, 1200));
        server.addRecipe(new BlastingRecipe(new NamespacedKey(plugin, "gold_sword_to_ingots"), new ItemStack(Material.GOLD_INGOT, 2), Material.GOLDEN_SWORD, 0, 600));
        server.addRecipe(new BlastingRecipe(new NamespacedKey(plugin, "gold_shovel_to_ingots"), new ItemStack(Material.GOLD_INGOT, 1), Material.GOLDEN_SHOVEL, 0, 300));
        server.addRecipe(new BlastingRecipe(new NamespacedKey(plugin, "gold_pickaxe_to_ingots"), new ItemStack(Material.GOLD_INGOT, 3), Material.GOLDEN_PICKAXE, 0, 900));
        server.addRecipe(new BlastingRecipe(new NamespacedKey(plugin, "gold_axe_to_ingots"), new ItemStack(Material.GOLD_INGOT, 3), Material.GOLDEN_AXE, 0, 900));
        server.addRecipe(new BlastingRecipe(new NamespacedKey(plugin, "gold_hoe_to_ingots"), new ItemStack(Material.GOLD_INGOT, 2), Material.GOLDEN_HOE, 0, 600));
    }

}
