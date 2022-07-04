package me.VanadeysHaven.TimmyCore.Data.Profiles.User.Currencies;

import me.VanadeysHaven.TimmyCore.Data.Profiles.DataContainers.DataContainer;
import me.VanadeysHaven.TimmyCore.Main;

public final class CurrenciesContainer extends DataContainer<Currency> {

    public CurrenciesContainer(String uuid, CurrenciesSapling sapling) {
        super(uuid);
        processCurrenciesSapling(sapling);
    }

    public String incrementInt(Currency currency, String reason){
        return incrementInt(currency, 1, reason);
    }

    public String incrementInt(Currency currency, int incrementBy, String reason){
        super.incrementInt(currency, incrementBy);

        if(currency == Currency.COINS)
            return currency.getColorCode() + "+" + incrementBy + " " + Main.getPlugin().getConfig().getString("currency.currencyName") + " (" + reason + ")";
        else
            return currency.getColorCode() + "+" + incrementBy + " " + currency.getName() + " (" + reason + ")";
    }

    public boolean hasEnoughBalance(Currency currency, int amount){
        return getInt(currency) >= amount;
    }

    private void processCurrenciesSapling(CurrenciesSapling sapling){
        for (Currency currency : Currency.values()) {
            String value = sapling.getCurrency(currency);
            if (value != null) {
                setString(currency, value, false, true);
            } else {
                setString(currency, currency.getDefaultValue(), false, true);
            }
        }
    }

}
