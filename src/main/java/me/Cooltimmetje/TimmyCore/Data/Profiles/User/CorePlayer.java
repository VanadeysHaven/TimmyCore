package me.Cooltimmetje.TimmyCore.Data.Profiles.User;

import lombok.Getter;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.Settings.SettingsContainer;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.Settings.SettingsSapling;

@Getter
public class CorePlayer {

    private String uuid;
    private SettingsContainer settings;

    public CorePlayer(String uuid){
        this.uuid = uuid;
        this.settings = new SettingsSapling(uuid).grow();
    }

}
