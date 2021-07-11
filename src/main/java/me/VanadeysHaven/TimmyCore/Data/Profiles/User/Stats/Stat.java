package me.VanadeysHaven.TimmyCore.Data.Profiles.User.Stats;

import lombok.Getter;
import me.VanadeysHaven.TimmyCore.Data.Database.Query;
import me.VanadeysHaven.TimmyCore.Data.Database.QueryExecutor;
import me.VanadeysHaven.TimmyCore.Data.Database.QueryResult;
import me.VanadeysHaven.TimmyCore.Data.Profiles.DataContainers.Data;
import me.VanadeysHaven.TimmyCore.Data.Profiles.DataContainers.ValueType;

import java.sql.SQLException;
import java.util.ArrayList;

@Getter
public enum Stat implements Data {

    COINS_TIMER("coins_timer", ValueType.INTEGER, "10", false);

    private String dbReference;
    private ValueType type;
    private String defaultValue;
    private boolean show;

    Stat(String dbReference, ValueType type, String defaultValue, boolean show){
        this.dbReference = dbReference;
        this.type = type;
        this.defaultValue = defaultValue;
        this.show = show;
    }

    public static Stat getByDbReference(String reference) {
        for(Stat stat : values())
            if(stat.getDbReference().equalsIgnoreCase(reference))
                return stat;

        return null;
    }

    public static void saveToDatabase(){
        QueryExecutor qe = null;
        ArrayList<String> stats = new ArrayList<>();
        try {
            qe = new QueryExecutor(Query.SELECT_ALL_STAT_KEYS);
            QueryResult qr = qe.executeQuery();
            while(qr.nextResult()){
                stats.add(qr.getString("name"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(qe != null) qe.close();
        }
        for(Stat stat : values()){
            if(stats.contains(stat.getDbReference())) continue;
            try {
                qe = new QueryExecutor(Query.INSERT_STAT_KEY).setString(1, stat.getDbReference());
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
        return "stat";
    }

    @Override
    public String getTechnicalName() {
        return this.toString();
    }

    @Override
    public boolean hasBound() {
        return true;
    }

    @Override
    public boolean checkBound(int value) {
        return value >= getMinBound() && value <= getMaxBound();
    }

    @Override
    public int getMinBound() {
        return 0;
    }

    @Override
    public int getMaxBound() {
        return 2147483647;
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
        return Query.DELETE_STAT_VALUE;
    }

    @Override
    public Query getUpdateQuery() {
        return Query.UPDATE_STAT_VALUE;
    }

    @Override
    public boolean isSaveToDatabase(){
        return true;
    }

}
