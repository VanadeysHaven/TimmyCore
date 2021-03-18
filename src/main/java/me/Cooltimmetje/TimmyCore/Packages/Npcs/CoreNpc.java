package me.Cooltimmetje.TimmyCore.Packages.Npcs;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.Cooltimmetje.TimmyCore.Main;
import me.Cooltimmetje.TimmyCore.Utilities.StringUtilities;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

public class CoreNpc {

    private CoreNpcDefinition definition;
    private NPC npc;
    private Hologram hologram;

    public CoreNpc(CoreNpcDefinition definition){
        this.definition = definition;
    }

    public void spawn(){
        this.npc = CitizensAPI.getNPCRegistry().createNPC(definition.getEntityType(), StringUtilities.colorify("&f"));
        npc.spawn(definition.getLocation());
        this.hologram = HologramsAPI.createHologram(Main.getPlugin(), definition.getLocation().add(0, 2.75, 0));
        for(String s : definition.getHoloLines())
            hologram.appendTextLine(StringUtilities.colorify(s));
    }

    public void despawn(){
        npc.despawn();
        npc.destroy();
        hologram.delete();
    }

}
