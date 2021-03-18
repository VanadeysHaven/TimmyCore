package me.Cooltimmetje.TimmyCore.Packages.Npcs;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@Getter
public enum CoreNpcDefinition {

    QUEST_MASTER (new Location(Bukkit.getWorld("world"), -41.5, 67, 144.5, -45, 0), EntityType.VILLAGER, "&a&lQUEST MASTER", "&7Coming soon");

    private Location location;
    private EntityType entityType;
    private String[] holoLines;

    CoreNpcDefinition(Location location, EntityType entityType, String... holoLines){
        this.location = location;
        this.entityType = entityType;
        this.holoLines = holoLines;
    }

}
