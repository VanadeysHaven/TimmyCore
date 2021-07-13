package me.VanadeysHaven.TimmyCore.Data.Profiles.User;

import lombok.Getter;
import lombok.Setter;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.Currencies.CurrenciesContainer;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.Currencies.CurrenciesSapling;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.Currencies.Currency;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.Settings.Setting;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.Settings.SettingsContainer;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.Settings.SettingsSapling;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.Stats.Stat;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.Stats.StatsContainer;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.Stats.StatsSapling;
import me.VanadeysHaven.TimmyCore.Main;
import me.VanadeysHaven.TimmyCore.Managers.Confirm.PendingConfirmation;
import me.VanadeysHaven.TimmyCore.Managers.Interact.PendingInteract;
import me.VanadeysHaven.TimmyCore.Packages.Rank.Rank;
import me.VanadeysHaven.TimmyCore.Packages.Warp.WarpManager;
import me.VanadeysHaven.TimmyCore.Timers.CurrencyTimerDefinition;
import me.VanadeysHaven.TimmyCore.Utilities.MessageUtilities;
import me.VanadeysHaven.TimmyCore.Utilities.StringUtilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

@Getter
public final class CorePlayer {

    private static final Logger logger = LoggerFactory.getLogger(CorePlayer.class);
    private static final ProfileManager pm = ProfileManager.getInstance();

    private Player player;

    private SettingsContainer settings;
    private StatsContainer stats;
    private CurrenciesContainer currencies;

    private Team team;

    @Setter private PendingInteract pendingInteract;
    @Setter private PendingConfirmation pendingConfirmation;

    String nickname;
    String rankTag;

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
        nickname = settings.getString(Setting.NICKNAME);
        boolean setNickname = true;
        if(nickname.equals("")) {
            setNickname = false;
            nickname = player.getName();
        }

        Rank rank = getSettings().getRank();
        nickname = StringUtilities.colorify("&" + rank.getColorCode() + nickname);
        rankTag = StringUtilities.colorify("&8[&" + rank.getColorCode() + rank.getRankName() + "&8]");
        String pronouns = getSettings().getString(Setting.PRONOUNS);
        if(!pronouns.equals(""))
            pronouns = StringUtilities.colorify("&8[&b" + getSettings().getString(Setting.PRONOUNS) + "&8]");

        setDisplayName();
        setNamePlate(setNickname);
        setPlayerListName(pronouns);
        setPlayerListHeader();
        setPlayerListFooter();
    }

    public void setPlayerListHeader(){
        String format = "&aWelcome to {0}&a, {1} {2}&a!\n&aPlayers online: &b{3}\n ";
        format = MessageFormat.format(format, Main.getPlugin().getConfig().getString("server.name"), rankTag, nickname, Bukkit.getOnlinePlayers().size());
        format = StringUtilities.colorify(format);

        player.setPlayerListHeader(format);
    }

    public void setPlayerListFooter(){
        String format = " \n&8--- &a&lMY PROFILE &8---\n&6" + Main.getPlugin().getConfig().getString("currency.currencyName") + "&8: &b{0} &8- &aWarps&8: &b{1}&8/&b{2}";
        format = MessageFormat.format(format, getCurrencies().getString(Currency.COINS), WarpManager.getInstance().getWarpCountForPlayer(player), getStats().getInt(Stat.WARP_SLOTS) + WarpManager.getInstance().getMAX_WARPS());
        format = StringUtilities.colorify(format);

        player.setPlayerListFooter(format);
    }

    private void setDisplayName(){
        player.setDisplayName(nickname);
    }

    private void setNamePlate(boolean setNickname){
        ScoreboardManager manager = Bukkit.getScoreboardManager(); assert manager != null;
        Scoreboard board = manager.getMainScoreboard();
        if(team == null)
            team = board.registerNewTeam(player.getName());

        team.setPrefix(StringUtilities.colorify(rankTag + " "));
        if(setNickname)
            team.setSuffix(StringUtilities.colorify(" &8[" + nickname + "&8]"));

        team.addPlayer(player);
    }

    private void setPlayerListName(String pronouns){
        player.setPlayerListName(StringUtilities.colorify(rankTag + " " + nickname + "&r " + pronouns));
    }

    public void unload() {
        save();
        team.unregister();
    }

    public void save(){
        currencies.save();
        stats.save();
        settings.save();
    }

    public void runCurrencyTimers() {
        StatsContainer stats = getStats();

        for(CurrencyTimerDefinition definition : CurrencyTimerDefinition.values()){
            Stat currencyTimer = definition.getCurrencyTimer();
            int curTime = stats.getInt(currencyTimer);
            if(curTime == 1) {
                MessageUtilities.sendMessage(player, getCurrencies().incrementInt(definition.getCurrency(), definition.getIncrease(), currencyTimer.getDefaultValue() + " minutes online"));
//                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1);
                stats.setString(currencyTimer, currencyTimer.getDefaultValue());
            } else {
                stats.incrementInt(currencyTimer, -1, false);
            }
        }

        setPlayerListFooter();
    }

    public String getFullDisplayName(){
        return rankTag + " " + nickname + "&r";
    }

}
