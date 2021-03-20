package me.Cooltimmetje.TimmyCore.Data.Profiles.User.Currencies;

import me.Cooltimmetje.TimmyCore.Data.Profiles.DataContainers.DataContainer;

public final class CurrenciesContainer extends DataContainer<Currency> {
    public CurrenciesContainer(String uuid, CurrenciesSapling sapling) {
        super(uuid);
        processCurrenciesSapling(sapling);
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
