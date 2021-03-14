package me.Cooltimmetje.TimmyCore.Data.Profiles.DataContainers;

import me.Cooltimmetje.TimmyCore.Data.Database.Query;

public interface Data {

    ValueType getType();
    String getTerminology();
    String getTechnicalName();
    boolean hasBound();
    boolean checkBound(int value);
    int getMinBound();
    int getMaxBound();
    boolean hasCooldown();
    int getCooldown();
    String getDefaultValue();
    Query getDeleteQuery();
    Query getUpdateQuery();
    String getDbReference();

}
