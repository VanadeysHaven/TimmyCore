package me.VanadeysHaven.TimmyCore.Data.Profiles.DataContainers;

import me.VanadeysHaven.TimmyCore.Data.Database.QueryExecutor;
import me.VanadeysHaven.TimmyCore.Exceptions.CooldownException;
import me.VanadeysHaven.TimmyCore.Exceptions.FieldOutOfBoundsException;
import me.VanadeysHaven.TimmyCore.Exceptions.FieldOutOfBoundsGenerator;
import me.VanadeysHaven.TimmyCore.Utilities.CooldownManager;
import me.VanadeysHaven.TimmyCore.Utilities.MiscUtilities;

import java.sql.SQLException;
import java.util.HashMap;

public class DataContainer<T extends Data> {

    private String uuid;
    private HashMap<T, String> values;
    private HashMap<T, CooldownManager> cooldowns;

    private static final boolean DEFAULT_SAVE = false;

    public DataContainer(String uuid){
        this.uuid = uuid;
        values = new HashMap<>();
        cooldowns = new HashMap<>();
    }

    public void setString(T field, String value){
        setString(field, value, DEFAULT_SAVE, false);
    }

    public void setString(T field, String value, boolean save, boolean bypassCooldown){
        if(isOnCooldown(field) && !bypassCooldown) throw new CooldownException(getCooldownManager(field).formatTime(69));
        if(value != null) {
            if(!checkType(field, value)) throw new IllegalArgumentException("Value " + value + " is unsuitable for " + field.getTerminology() + " `" + field.getTechnicalName() + "`; not of type " + field.getType());
            if(field.getType() == ValueType.INTEGER) if(field.hasBound()) if(!field.checkBound(Integer.parseInt(value))) throw new FieldOutOfBoundsException(new FieldOutOfBoundsGenerator<T>(field, value).getMessage());
            if(value.equalsIgnoreCase("null")) value = null;
        }

        this.values.put(field, value);
        if(!bypassCooldown) startCooldown(field);
        if(save) save(field);
    }

    public String getString(T field){
        return this.values.get(field);
    }

    public void setInt(T field, int value){
        setInt(field, value, DEFAULT_SAVE, false);
    }

    public void setInt(T field, int value, boolean save, boolean bypassCooldown){
        setString(field, value+"", save, bypassCooldown);
    }

    public void incrementInt(T field){
        incrementInt(field, 1, DEFAULT_SAVE);
    }

    public void incrementInt(T field, boolean save){
        incrementInt(field, 1, save);
    }

    public void incrementInt(T field, int incrementBy) {
        incrementInt(field, incrementBy, DEFAULT_SAVE);
    }

    public void incrementInt(T field, int incrementBy, boolean save){
        if(field.getType() != ValueType.INTEGER) throw new IllegalArgumentException(field.getTerminology() + " " + field.getTechnicalName() + " is not of type INTEGER.");
        setInt(field, getInt(field) + incrementBy, save, false);
    }

    public int getInt(T field){
        if(field.getType() != ValueType.INTEGER) throw new IllegalArgumentException(field.getTerminology() + " " + field.getTechnicalName() + " is not of type INTEGER.");
        return Integer.parseInt(getString(field));
    }

    public void setDouble(T field, double value){
        setDouble(field,  value, DEFAULT_SAVE, false);
    }

    public void setDouble(T field, double value, boolean save, boolean bypassCooldown){
        setString(field, value+"", save, bypassCooldown);
    }

    public double getDouble(T field){
        if(field.getType() != ValueType.DOUBLE) throw new IllegalArgumentException(field.getTerminology() + " " + field.getTechnicalName() + " is not of type DOUBLE.");
        return Double.parseDouble(getString(field));
    }

    public void setLong(T field, long value){
        setLong(field, value, DEFAULT_SAVE, false);
    }

    public void setLong(T field, long value, boolean save, boolean bypassCooldown){
        setString(field, value+"", save, bypassCooldown);
    }

    public void incrementLong(T field){
        incrementLong(field, 1);
    }

    public void incrementLong(T field, long incrementBy){
        if(field.getType() != ValueType.LONG) throw new IllegalArgumentException(field.getTerminology() + " " + field.getTechnicalName() + " is not of type LONG.");
        setLong(field, getLong(field) + incrementBy);
    }

    public long getLong(T field){
        if(field.getType() != ValueType.LONG) throw new IllegalArgumentException(field.getTerminology() + " " + field.getTechnicalName() + " is not of type LONG.");
        return Long.parseLong(getString(field));
    }

    public void setBoolean(T field, boolean value){
        setBoolean(field, value, DEFAULT_SAVE, false);
    }

    public void setBoolean(T field, boolean value, boolean save, boolean bypassCooldown){
        setString(field, value+"", save, bypassCooldown);
    }

    public void toggleBoolean(T field) {
        if(field.getType() != ValueType.BOOLEAN) throw new IllegalArgumentException(field.getTerminology() + " " + field.getTechnicalName() + " is not of type BOOLEAN.");
        setBoolean(field, !getBoolean(field));
    }

    public boolean getBoolean(T field){
        if(field.getType() != ValueType.BOOLEAN) throw new IllegalArgumentException(field.getTerminology() + " " + field.getTechnicalName() + " is not of type BOOLEAN.");
        return Boolean.parseBoolean(getString(field));
    }

    private boolean checkType(T field, String value){
        ValueType type = field.getType();
        switch (type) {
            case INTEGER: return MiscUtilities.isInt(value);
            case DOUBLE: return MiscUtilities.isDouble(value);
            case LONG: return MiscUtilities.isLong(value);
            case BOOLEAN: return MiscUtilities.isBoolean(value);
            default: return type == ValueType.STRING;
        }
    }

    private void startCooldown(T field) {
        if(!field.hasCooldown()) return;

        getCooldownManager(field).startCooldown(69);
    }

    private boolean isOnCooldown(T field){
        if(!field.hasCooldown()) return false;
        return getCooldownManager(field).isOnCooldown(69);
    }

    private CooldownManager getCooldownManager(T field) {
        CooldownManager manager;
        if(!cooldowns.containsKey(field)) {
            manager = new CooldownManager(field.getCooldown(), true);
            cooldowns.put(field, manager);
        } else manager = cooldowns.get(field);

        return manager;
    }

    public void save(){
        for(T field : values.keySet()){
            save(field);
        }
    }

    public void save(T field){ //identifier id //id identifier value value
        if(!field.isSaveToDatabase()) return;
        String value = getString(field);
        if(value == null) value = "null";
        QueryExecutor qe = null;
        if (getString(field).equals(field.getDefaultValue())) {
            try {
                qe = new QueryExecutor(field.getDeleteQuery()).setString(1, uuid).setString(2, field.getDbReference());
                qe.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                assert qe != null;
                qe.close();
            }
        } else {
            try {
                qe = new QueryExecutor(field.getUpdateQuery()).setString(1, field.getDbReference()).setString(2, uuid).setString(3, value).and(4);
                qe.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                assert qe != null;
                qe.close();
            }
        }
    }


}
