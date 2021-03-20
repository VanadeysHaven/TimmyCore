package me.Cooltimmetje.TimmyCore.Data.Profiles.User.Stats;

import me.Cooltimmetje.TimmyCore.Data.Database.Query;
import me.Cooltimmetje.TimmyCore.Data.Database.QueryExecutor;
import me.Cooltimmetje.TimmyCore.Data.Database.QueryResult;

import java.sql.SQLException;
import java.util.HashMap;

public final class StatsSapling {

    private String uuid;
    private HashMap<Stat,String> stats;

    public StatsSapling(String uuid){
        this.uuid = uuid;
        this.stats = new HashMap<>();

        QueryExecutor qe = null;
        try {
            qe = new QueryExecutor(Query.SELECT_ALL_STATS_VALUES).setString(1, uuid);
            QueryResult qr = qe.executeQuery();
            while (qr.nextResult()) {
                addStat(Stat.getByDbReference(qr.getString("name")), qr.getString("value"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            assert qe != null;
            qe.close();
        }
    }

    public void addStat(Stat stat, String value){
        stats.put(stat, value);
    }

    public String getStat(Stat stat){
        if (stats.containsKey(stat))
            return stats.get(stat);
        return null;
    }

    public StatsContainer grow(){
        return new StatsContainer(uuid, this);
    }

}
