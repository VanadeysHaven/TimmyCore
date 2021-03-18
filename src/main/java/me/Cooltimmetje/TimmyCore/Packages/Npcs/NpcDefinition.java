package me.Cooltimmetje.TimmyCore.Packages.Npcs;

import lombok.Getter;
import me.Cooltimmetje.TimmyCore.Packages.Holograms.HoloDefiner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@Getter
public enum NpcDefinition implements HoloDefiner {

    QUEST_MASTER (new Location(Bukkit.getWorld("world"), -41.5, 67, 144.5, -45, 0), EntityType.VILLAGER, "&a&lQUEST MASTER", "&7Coming soon");

    private Location location;
    private EntityType entityType;
    private String[] lines;

    NpcDefinition(Location location, EntityType entityType, String... holoLines){
        this.location = location;
        this.entityType = entityType;
        this.lines = holoLines;
    }

}
