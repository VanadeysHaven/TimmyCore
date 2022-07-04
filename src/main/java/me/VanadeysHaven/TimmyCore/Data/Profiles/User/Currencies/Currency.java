package me.VanadeysHaven.TimmyCore.Data.Profiles.User.Currencies;

import lombok.Getter;
import me.VanadeysHaven.TimmyCore.Data.Database.Query;
import me.VanadeysHaven.TimmyCore.Data.Database.QueryExecutor;
import me.VanadeysHaven.TimmyCore.Data.Database.QueryResult;
import me.VanadeysHaven.TimmyCore.Data.Profiles.DataContainers.Data;
import me.VanadeysHaven.TimmyCore.Data.Profiles.DataContainers.ValueType;
import me.VanadeysHaven.TimmyCore.Utilities.StringUtilities;

import java.sql.SQLException;
import java.util.ArrayList;

@Getter
public enum Currency implements Data {

    COINS ("coins", "Coins", "6", ValueType.INTEGER, "0");

    private String dbReference;
    private String name;
    private String color;
    private ValueType type;
    private String defaultValue;

    Currency(String dbReference, String name, String color, ValueType type, String defaultValue){
        this.dbReference = dbReference;
        this.name = name;
        this.color = color;
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

    public String getColorCode(){
        return StringUtilities.colorify("&" + color);
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
        return Query.UPDATE_CURRENCY_VALUE;
    }

    @Override
    public boolean isSaveToDatabase() {
        return true;
    }

}
