package me.Cooltimmetje.TimmyCore.Data.Profiles.User.Settings;

import me.Cooltimmetje.TimmyCore.Data.Profiles.DataContainers.DataContainer;

public class SettingsContainer extends DataContainer<Setting> {

    public SettingsContainer(String uuid, SettingsSapling sapling){
        super(uuid);
        processSettingsSapling(sapling);
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
