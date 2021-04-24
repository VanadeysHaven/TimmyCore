package me.VanadeysHaven.TimmyCore.Timers;

import lombok.Getter;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.Currencies.Currency;
import me.VanadeysHaven.TimmyCore.Data.Profiles.User.Stats.Stat;

@Getter
public enum CurrencyTimerDefinition {

    NEUROS(Currency.NEUROS, Stat.NEURO_TIMER, 20);

    private Currency currency;
    private Stat currencyTimer;
    private int increase;

    CurrencyTimerDefinition(Currency currency, Stat currencyTimer, int increase){
        this.currency = currency;
        this.currencyTimer = currencyTimer;
        this.increase = increase;
    }

}
