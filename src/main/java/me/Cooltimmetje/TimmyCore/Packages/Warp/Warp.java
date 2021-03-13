package me.Cooltimmetje.TimmyCore.Packages.Warp;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Warp {

    @Getter private String name;
    @Getter private Location location;
    @Getter private Player owner;
    @Getter @Setter private boolean isPublic;

    public Warp(String name, Location location, Player owner, boolean isPublic){
        this.name = name;
        this.location = location;
        this.owner = owner;
        this.isPublic = isPublic;
    }

}
