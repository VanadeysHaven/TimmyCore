package me.VanadeysHaven.TimmyCore.Exceptions;

import me.VanadeysHaven.TimmyCore.Data.Profiles.User.Currencies.Currency;

public final class InsufficientBalanceException extends Exception {

    private final Currency currency;
    private final int amount;

    public InsufficientBalanceException(Currency currency, int amount){
        super("You do not have enough balance of currency " + currency + ". Amount required: " + amount);
        this.currency = currency;
        this.amount = amount;
    }

}
