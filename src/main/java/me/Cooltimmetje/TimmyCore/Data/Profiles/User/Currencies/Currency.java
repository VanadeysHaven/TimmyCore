package me.Cooltimmetje.TimmyCore.Data.Profiles.User.Currencies;

import lombok.Getter;
import me.Cooltimmetje.TimmyCore.Data.Database.Query;
import me.Cooltimmetje.TimmyCore.Data.Database.QueryExecutor;
import me.Cooltimmetje.TimmyCore.Data.Database.QueryResult;
import me.Cooltimmetje.TimmyCore.Data.Profiles.DataContainers.Data;
import me.Cooltimmetje.TimmyCore.Data.Profiles.DataContainers.ValueType;

import java.sql.SQLException;
import java.util.ArrayList;

@Getter
public enum Currency implements Data {

    NEUROS("neuros", ValueType.INTEGER, "0");

    private String dbReference;
    private ValueType type;
    private String defaultValue;

    Currency(String dbReference, ValueType type, String defaultValue){
        this.dbReference = dbReference;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public static Currency getByDbReference(String reference){
        for(Currency currency : values())
            if(currency.getDbReference().equalsIgnoreCase(reference))
                return currency;

        return null;
    }

    public static void saveToDatabase(){
        QueryExecutor qe = null;
        ArrayList<String> currencies = new ArrayList<>();
        try {
            qe = new QueryExecutor(Query.SELECT_ALL_CURRENCY_KEYS);
            QueryResult qr = qe.executeQuery();
            while(qr.nextResult()){
                currencies.add(qr.getString("name"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(qe != null) qe.close();
        }
        for(Currency currency : values()){
            if(currencies.contains(currency.getDbReference())) continue;
            try {
                qe = new QueryExecutor(Query.INSERT_CURRENCY_KEY).setString(1, currency.getDbReference());
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
        return "currency";
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
        return Query.DELETE_CURRENCY_VALUE;
    }

    @Override
    public Query getUpdateQuery() {
        return Query.INSERT_CURRENCY_KEY;
    }
}
