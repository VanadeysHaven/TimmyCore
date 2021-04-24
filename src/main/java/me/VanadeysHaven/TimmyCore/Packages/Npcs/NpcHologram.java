package me.VanadeysHaven.TimmyCore.Packages.Npcs;

import me.VanadeysHaven.TimmyCore.Packages.Holograms.CoreHologram;

public final class NpcHologram extends CoreHologram {

    public NpcHologram(NpcDefinition definition, double npcHeight){
        this.definition = definition;
        location = definition.getLocation();
        location.add(0, npcHeight + 0.25 + (definition.getLines().length * 0.25), 0);
    }

}
