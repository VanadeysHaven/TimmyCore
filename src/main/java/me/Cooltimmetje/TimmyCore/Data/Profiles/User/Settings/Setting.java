package me.Cooltimmetje.TimmyCore.Data.Profiles.User.Settings;

import lombok.Getter;
import me.Cooltimmetje.TimmyCore.Data.Database.Query;
import me.Cooltimmetje.TimmyCore.Data.Database.QueryExecutor;
import me.Cooltimmetje.TimmyCore.Data.Database.QueryResult;
import me.Cooltimmetje.TimmyCore.Data.Profiles.DataContainers.Data;
import me.Cooltimmetje.TimmyCore.Data.Profiles.DataContainers.ValueType;

import java.sql.SQLException;
import java.util.ArrayList;

@Getter
public enum Setting implements Data {

    NICKNAME("nickname", ValueType.STRING, ""),
    RANK("rank", ValueType.STRING, "NEUROCITIZEN");

    private String dbReference;
    private ValueType type;
    private String defaultValue;

    Setting(String dbReference, ValueType type, String defaultValue) {
        this.dbReference = dbReference;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public static Setting getByDbReference(String reference){
        for(Setting setting : values())
            if(setting.getDbReference().equalsIgnoreCase(reference))
                return setting;

        return null;
    }

    public static void saveToDatabase(){
        QueryExecutor qe = null;
        ArrayList<String> settings = new ArrayList<>();
        try {
            qe = new QueryExecutor(Query.SELECT_ALL_SETTING_KEYS);
            QueryResult qr = qe.executeQuery();
            while(qr.nextResult()){
                settings.add(qr.getString("name"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(qe != null) qe.close();
        }
        for(Setting setting : values()){
            if(settings.contains(setting.getDbReference())) continue;
            try {
                qe = new QueryExecutor(Query.INSERT_SETTING_KEY).setString(1, setting.getDbReference());
                qe.execute();
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                if(qe != null) qe.close();
            }
        }
    }

    @Override
    public String getTerminology() {
        return "setting";
    }

    @Override
    public String getTechnicalName() {
        return this.toString();
    }

    @Override
    public boolean hasBound() {
        return false;
    }

    @Override
    public boolean checkBound(int value) {
        return true;
    }

    @Override
    public int getMinBound() {
        return -1;
    }

    @Override
    public int getMaxBound() {
        return -1;
    }

    @Override
    public boolean hasCooldown() {
        return false;
    }

    @Override
    public int getCooldown() {
        return -1;
    }

    @Override
    public Query getDeleteQuery() {
        return Query.DELETE_SETTING_VALUE;
    }

    @Override
    public Query getUpdateQuery() {
        return Query.UPDATE_SETTING_VALUE;
    }
}
