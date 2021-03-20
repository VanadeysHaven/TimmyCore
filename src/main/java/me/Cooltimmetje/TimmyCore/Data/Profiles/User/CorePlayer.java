package me.Cooltimmetje.TimmyCore.Data.Profiles.User;

import lombok.Getter;
import lombok.Setter;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.Currencies.CurrenciesContainer;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.Currencies.CurrenciesSapling;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.Settings.Setting;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.Settings.SettingsContainer;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.Settings.SettingsSapling;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.Stats.StatsContainer;
import me.Cooltimmetje.TimmyCore.Data.Profiles.User.Stats.StatsSapling;
import me.Cooltimmetje.TimmyCore.Managers.Interact.PendingInteract;
import me.Cooltimmetje.TimmyCore.Packages.Rank.Rank;
import me.Cooltimmetje.TimmyCore.Utilities.StringUtilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public final class CorePlayer {

    private static final Logger logger = LoggerFactory.getLogger(ProfileManager.class);
    private static final ProfileManager pm = ProfileManager.getInstance();

    private Player player;
    private SettingsContainer settings;
    private StatsContainer stats;
    private CurrenciesContainer currencies;
    private Team team;

    @Setter private PendingInteract pendingInteract;

    public CorePlayer(Player player){
        this.player = player;
        this.settings = new SettingsSapling(getUuid()).grow();
        this.stats = new StatsSapling(getUuid()).grow();
        this.currencies = new CurrenciesSapling(getUuid()).grow();

        updateAppearance();
    }

    public String getUuid(){
        return player.getUniqueId().toString();
    }

    public void updateAppearance(){
        String nickname = settings.getString(Setting.NICKNAME);
        boolean setNickname = true;
        if(nickname.equals("")) {
            setNickname = false;
            nickname = player.getName();
        }

        Rank rank = getSettings().getRank();
        nickname = StringUtilities.colorify("&" + rank.getColorCode() + nickname);
        String rankTag = StringUtilities.colorify("&8[&" + rank.getColorCode() + rank.getRankName() + "&8]");
        String pronouns = getSettings().getString(Setting.PRONOUNS);
        if(!pronouns.equals(""))
            pronouns = StringUtilities.colorify("&8[&b" + getSettings().getString(Setting.PRONOUNS) + "&8]");

        setDisplayName(nickname);
        setNamePlate(rankTag, nickname, setNickname);
        setPlayerListName(rankTag, nickname, pronouns);
    }

    private void setDisplayName(String nickname){
        player.setDisplayName(nickname);
    }

    private void setNamePlate(String rankTag, String nickname, boolean setNickname){
        ScoreboardManager manager = Bukkit.getScoreboardManager(); assert manager != null;
        Scoreboard board = manager.getMainScoreboard();
        if(team == null)
            team = board.registerNewTeam(player.getName());

        team.setPrefix(StringUtilities.colorify(rankTag + " "));
        if(setNickname)
            team.setSuffix(StringUtilities.colorify(" &8[" + nickname + "&8]"));

        team.addPlayer(player);
    }

    private void setPlayerListName(String rankTag, String nickname, String pronouns){
        player.setPlayerListName(StringUtilities.colorify(rankTag + " " + nickname + "&r " + pronouns));
    }

    public void unload() {
        team.unregister();
    }
}
