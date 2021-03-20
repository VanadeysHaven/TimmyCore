package me.Cooltimmetje.TimmyCore.Data.Profiles.User.Currencies;

import me.Cooltimmetje.TimmyCore.Data.Database.Query;
import me.Cooltimmetje.TimmyCore.Data.Database.QueryExecutor;
import me.Cooltimmetje.TimmyCore.Data.Database.QueryResult;

import java.sql.SQLException;
import java.util.HashMap;

public final class CurrenciesSapling {

    private String uuid;
    private HashMap<Currency,String> currencies;

    public CurrenciesSapling(String uuid){
        this.uuid = uuid;
        this.currencies = new HashMap<>();

        QueryExecutor qe = null;
        try {
            qe = new QueryExecutor(Query.SELECT_ALL_CURRENCIES_VALUES).setString(1, uuid);
            QueryResult qr = qe.executeQuery();
            while (qr.nextResult()) {
                addCurrency(Currency.getByDbReference(qr.getString("name")), qr.getString("value"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            assert qe != null;
            qe.close();
        }
    }

    public void addCurrency(Currency currency, String value){
        currencies.put(currency, value);
    }

    public String getCurrency(Currency currency){
        if (currencies.containsKey(currency))
            return currencies.get(currency);
        return null;
    }

    public CurrenciesContainer grow(){
        return new CurrenciesContainer(uuid, this);
    }

}
