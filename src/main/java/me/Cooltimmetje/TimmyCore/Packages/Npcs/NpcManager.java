package me.Cooltimmetje.TimmyCore.Packages.Npcs;

import net.citizensnpcs.api.CitizensAPI;

import java.util.ArrayList;

public final class NpcManager {

    private static NpcManager instance;

    public static NpcManager getInstance(){
        if(instance == null)
            instance = new NpcManager();

        return instance;
    }

    private ArrayList<CoreNpc> npcs;

    private NpcManager(){
        this.npcs = new ArrayList<>();
    }

    public void spawnAll(){
        CitizensAPI.getNPCRegistry().deregisterAll();
        for(NpcDefinition definition : NpcDefinition.values()) {
            CoreNpc npc = new CoreNpc(definition);
            npc.spawn();
            npcs.add(npc);
        }
    }

    public void despawnAll(){
        for(CoreNpc npc : npcs)
            npc.despawn();
    }

}
