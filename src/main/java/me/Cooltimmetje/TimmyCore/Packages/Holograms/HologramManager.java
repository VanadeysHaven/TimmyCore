package me.Cooltimmetje.TimmyCore.Packages.Holograms;

import me.Cooltimmetje.TimmyCore.Packages.Npcs.NpcDefinition;
import me.Cooltimmetje.TimmyCore.Packages.Npcs.NpcHologram;

import java.util.ArrayList;

public final class HologramManager {

    private static HologramManager instance;

    public static HologramManager getInstance() {
        if(instance == null)
            instance = new HologramManager();

        return instance;
    }

    private ArrayList<CoreHologram> holograms;

    private HologramManager(){
        this.holograms = new ArrayList<>();
    }

    public void spawnAll(){
//        for(HologramDefinition definition : HologramDefinition.values()){
//            CoreHologram hologram = new CoreHologram(definition);
//            hologram.spawn();
//            holograms.add(hologram);
//        }
    }

    public NpcHologram create(NpcDefinition definition, double npcHeight){
        NpcHologram hologram = new NpcHologram(definition, npcHeight);
        holograms.add(hologram);
        return hologram;
    }

    public void despawnAll(){
        for(CoreHologram hologram : holograms)
            hologram.despawn();
    }

}
