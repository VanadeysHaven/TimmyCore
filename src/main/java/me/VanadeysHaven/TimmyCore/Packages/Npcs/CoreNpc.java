package me.VanadeysHaven.TimmyCore.Packages.Npcs;

import me.VanadeysHaven.TimmyCore.Packages.Holograms.HologramManager;
import me.VanadeysHaven.TimmyCore.Utilities.StringUtilities;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

public final class CoreNpc {

    private NpcDefinition definition;
    private NPC npc;
    private NpcHologram hologram;

    public CoreNpc(NpcDefinition definition){
        this.definition = definition;
    }

    public void spawn(){
        this.npc = CitizensAPI.getNPCRegistry().createNPC(definition.getEntityType(), StringUtilities.colorify("&f"));
        npc.spawn(definition.getLocation());
        hologram = HologramManager.getInstance().create(definition, npc.getEntity().getHeight());
        hologram.spawn();
    }

    public void despawn(){
        npc.despawn();
        npc.destroy();
        hologram.despawn();
    }

}
