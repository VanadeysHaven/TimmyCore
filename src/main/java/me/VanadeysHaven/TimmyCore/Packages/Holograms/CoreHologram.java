package me.VanadeysHaven.TimmyCore.Packages.Holograms;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.VanadeysHaven.TimmyCore.Main;
import me.VanadeysHaven.TimmyCore.Utilities.StringUtilities;
import org.bukkit.Location;

public class CoreHologram {

    protected HoloDefiner definition;
    protected Hologram hologram;
    protected Location location;

    public CoreHologram(HologramDefinition definition){
        this.definition = definition;
        location = definition.getLocation();
    }

    public CoreHologram(){}

    public void spawn(){
        this.hologram = HologramsAPI.createHologram(Main.getPlugin(), location);
        for(String s : definition.getLines())
            addLine(s);
    }

    public void despawn(){
        if(!hologram.isDeleted())
            hologram.delete();
    }

    public void addLine(String text){
        hologram.appendTextLine(StringUtilities.colorify(text));
    }

}
