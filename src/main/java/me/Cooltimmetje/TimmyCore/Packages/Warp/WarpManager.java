package me.Cooltimmetje.TimmyCore.Packages.Warp;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class WarpManager {

    private ArrayList<Warp> warps;

    public WarpManager(){
        this.warps = new ArrayList<>();
    }

    public Warp getWarp(String name){
        name = name.toLowerCase();
        for(Warp warp : warps) if(warp.getName().equals(name))
            return warp;

            return null; //TODO: add exception
    }

    public void addWarp(String name, Player owner, Location location, boolean isPublic){
        warps.add(new Warp(name, location, owner, isPublic));
    }

    public void deleteWarp(String name){

    }

    public void setWarpPublic(String name, boolean newPublic){
        getWarp(name).setPublic(newPublic);
    }


}
