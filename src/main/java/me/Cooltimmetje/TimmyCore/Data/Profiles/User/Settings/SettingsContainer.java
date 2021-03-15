package me.Cooltimmetje.TimmyCore.Data.Profiles.User.Settings;

import me.Cooltimmetje.TimmyCore.Data.Profiles.DataContainers.DataContainer;
import me.Cooltimmetje.TimmyCore.Packages.Rank.Rank;

public class SettingsContainer extends DataContainer<Setting> {

    public SettingsContainer(String uuid, SettingsSapling sapling){
        super(uuid);
        processSettingsSapling(sapling);
    }

    public Rank getRank(){
        return Rank.valueOf(getString(Setting.RANK));
    }

    public void setRank(Rank rank){
        setString(Setting.RANK, rank.toString());
    }

    private void processSettingsSapling(SettingsSapling sapling){
        for (Setting setting : Setting.values()) {
            String value = sapling.getSetting(setting);
            if (value != null) {
                setString(setting, value, false, true);
            } else {
                setString(setting, setting.getDefaultValue(), false, true);
            }
        }
    }

}
