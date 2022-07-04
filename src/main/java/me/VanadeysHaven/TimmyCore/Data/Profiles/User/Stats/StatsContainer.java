package me.VanadeysHaven.TimmyCore.Data.Profiles.User.Stats;

import me.VanadeysHaven.TimmyCore.Data.Profiles.DataContainers.DataContainer;
import me.VanadeysHaven.TimmyCore.Main;
import me.VanadeysHaven.TimmyCore.Packages.Warp.WarpManager;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class StatsContainer extends DataContainer<Stat> {

    public StatsContainer(String uuid, StatsSapling sapling) {
        super(uuid);
        processStatsSapling(sapling);
    }

    @Override
    public int getInt(Stat field) {
        Player p = Main.getPlugin().getServer().getPlayer(UUID.fromString(uuid));
        if(p != null) {
            if (field == Stat.WARP_SLOTS) {
                WarpManager wm = WarpManager.getInstance();
                int playerAmount = wm.getWarpCountForPlayer(p);
                if (super.getInt(Stat.WARP_SLOTS) == -1) {
                    int slotAmount = Math.max(0, playerAmount - wm.getMAX_WARPS());
                    setInt(Stat.WARP_SLOTS, slotAmount);
                } else if (playerAmount > (super.getInt(Stat.WARP_SLOTS) + wm.getMAX_WARPS())) {
                    int slotAmount = Math.max(0, playerAmount - wm.getMAX_WARPS());
                    setInt(Stat.WARP_SLOTS, slotAmount);
                }
            }
        }

        return super.getInt(field);
    }

    private void processStatsSapling(StatsSapling sapling){
        for (Stat stat : Stat.values()) {
            String value = sapling.getStat(stat);
            if (value != null) {
                setString(stat, value, false, true);
            } else {
                setString(stat, stat.getDefaultValue(), false, true);
            }
        }
    }

}
