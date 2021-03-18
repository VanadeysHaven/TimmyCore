package me.Cooltimmetje.TimmyCore.Packages.Holograms;

import lombok.Getter;
import org.bukkit.Location;

@Getter
public enum HologramDefinition implements HoloDefiner {

    DO_NOT_SPAWN();

    private Location location;
    private String[] lines;

    HologramDefinition(Location location, String... lines) {
        this.location = location;
        this.lines = lines;
    }

    HologramDefinition(){
        //placeholder to not make the class error
    }

}
