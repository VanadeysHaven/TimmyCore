package me.Cooltimmetje.TimmyCore.Data.Profiles.User;

import lombok.Getter;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.Settings.Setting;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.Settings.SettingsContainer;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.Settings.SettingsSapling;
import me.Cooltimmetje.TimmyCore.Utilities.StringUtilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class CorePlayer {

    private static final Logger logger = LoggerFactory.getLogger(ProfileManager.class);
    private static final ProfileManager pm = ProfileManager.getInstance();

    private Player player;
    private SettingsContainer settings;
    private Team team;

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

        nickname = StringUtilities.colorify("&a" + nickname);

        player.setDisplayName(nickname);
        player.setPlayerListName(nickname);

        ScoreboardManager manager = Bukkit.getScoreboardManager(); assert manager != null;
        Scoreboard board = manager.getMainScoreboard();
        if(team == null)
            team = board.registerNewTeam(player.getName());
        team.setPrefix(StringUtilities.colorify("&a"));
        team.setSuffix(StringUtilities.colorify(" &8[" + nickname + "&8]"));
        team.addPlayer(player);
    }

    public void unload() {
        team.unregister();
    }
}
