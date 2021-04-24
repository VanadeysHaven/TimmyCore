package me.VanadeysHaven.TimmyCore.Data.Profiles.User.Stats;

import me.VanadeysHaven.TimmyCore.Data.Profiles.DataContainers.DataContainer;

public final class StatsContainer extends DataContainer<Stat> {

    public StatsContainer(String uuid, StatsSapling sapling) {
        super(uuid);
        processStatsSapling(sapling);
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
