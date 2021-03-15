package me.Cooltimmetje.TimmyCore.Data.Profiles.User;

import lombok.Getter;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.Settings.Setting;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.Settings.SettingsContainer;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.Settings.SettingsSapling;
import org.bukkit.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class CorePlayer {

    private static final Logger logger = LoggerFactory.getLogger(ProfileManager.class);
    private static final ProfileManager pm = ProfileManager.getInstance();

    private Player player;
    private SettingsContainer settings;

    public CorePlayer(Player player){
        this.player = player;
        this.settings = new SettingsSapling(getUuid()).grow();

        updateDisplayName();
    }

    public String getUuid(){
        return player.getUniqueId().toString();
    }

    public void updateDisplayName(){
        logger.info("Updating Displayname...");
        String nickname = settings.getString(Setting.NICKNAME);
        if(nickname.equals(""))
            nickname = player.getName();

        player.setDisplayName(nickname);
        player.setPlayerListName(nickname);
    }

    public void unload() {

    }
}
