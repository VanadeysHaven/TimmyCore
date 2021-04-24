package me.VanadeysHaven.TimmyCore.Data.Profiles.User.Settings;

import me.VanadeysHaven.TimmyCore.Data.Database.Query;
import me.VanadeysHaven.TimmyCore.Data.Database.QueryExecutor;
import me.VanadeysHaven.TimmyCore.Data.Database.QueryResult;

import java.sql.SQLException;
import java.util.HashMap;

public final class SettingsSapling {

    private String uuid;
    private HashMap<Setting,String> settings;

    public SettingsSapling(String uuid){
        this.uuid = uuid;
        this.settings = new HashMap<>();

        QueryExecutor qe = null;
        try {
            qe = new QueryExecutor(Query.SELECT_ALL_SETTINGS_VALUES).setString(1, uuid);
            QueryResult qr = qe.executeQuery();
            while (qr.nextResult()) {
                addSetting(Setting.getByDbReference(qr.getString("name")), qr.getString("value"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            assert qe != null;
            qe.close();
        }
    }

    public void addSetting(Setting setting, String value){
        settings.put(setting, value);
    }

    public String getSetting(Setting setting){
        if (settings.containsKey(setting))
            return settings.get(setting);
        return null;
    }

    public SettingsContainer grow(){
        return new SettingsContainer(uuid, this);
    }

}