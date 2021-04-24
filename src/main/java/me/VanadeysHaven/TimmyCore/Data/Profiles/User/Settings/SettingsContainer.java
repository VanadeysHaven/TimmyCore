package me.VanadeysHaven.TimmyCore.Data.Profiles.User.Settings;

import me.VanadeysHaven.TimmyCore.Data.Profiles.DataContainers.DataContainer;
import me.VanadeysHaven.TimmyCore.Packages.Rank.Rank;
import me.VanadeysHaven.TimmyCore.Utilities.StringUtilities;
import org.bukkit.ChatColor;

public final class SettingsContainer extends DataContainer<Setting> {

    public SettingsContainer(String uuid, SettingsSapling sapling){
        super(uuid);
        processSettingsSapling(sapling);
    }

    @Override
    public void setString(Setting field, String value, boolean save, boolean bypassCooldown) {
        if(field == Setting.NICKNAME) {
            String idNick = ChatColor.stripColor(StringUtilities.colorify(value));
            super.setString(Setting.ID_NICK, idNick, save, bypassCooldown);
        }

        super.setString(field, value, save, bypassCooldown);
    }

    public Rank getRank(){
        return Rank.valueOf(getString(Setting.RANK));
    }

    public void setRank(Rank rank){
        setString(Setting.RANK, rank.toString());
    }

    private void processSettingsSapling(SettingsSapling sapling){
        for (Setting setting : Setting.values()) {
            if(setting == Setting.ID_NICK) continue;
            String value = sapling.getSetting(setting);
            if (value != null) {
                setString(setting, value, false, true);
            } else {
                setString(setting, setting.getDefaultValue(), false, true);
            }
        }
    }

}
