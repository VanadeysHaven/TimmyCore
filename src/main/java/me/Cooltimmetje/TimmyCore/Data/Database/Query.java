package me.Cooltimmetje.TimmyCore.Data.Database;

import lombok.Getter;

@Getter
public enum Query {

    //USERS
    INSERT_USER("insert into users(uuid, username) value (?,?) on duplicate key update username=?;"),

    //SETTINGS
    SELECT_ALL_SETTINGS_VALUES("select us.name, ush.value from user_has_settings ush join user_settings us on ush.setting_id = us.id where uuid=?;"),
    SELECT_ALL_SETTING_KEYS("select name from user_settings;"),
    INSERT_SETTING_KEY("insert ignore into user_settings(name) value (?);"),
    UPDATE_SETTING_VALUE("insert into user_has_settings (setting_id, uuid, value) values ((select id from user_settings where name=?),?,?) on duplicate key update value=?;"),
    DELETE_SETTING_VALUE("delete uhs from user_has_settings uhs join user_settings us on uhs.setting_id = us.id where uhs.uuid=? and us.name=?;"),

    //CURRENCIES
    SELECT_ALL_CURRENCIES_VALUES("select c.name, uhc.value from user_has_currencies uhc join currencies c on uhc.currency_id = c.id where uuid=?;"),
    SELECT_ALL_CURRENCY_KEYS("select name from currencies;"),
    INSERT_CURRENCY_KEY("insert ignore into currencies(name) value (?);"),
    UPDATE_CURRENCY_VALUE("insert into user_has_currencies (currency_id, uuid, value) values ((select id from currencies where name=?),?,?) on duplicate key update value=?;"),
    DELETE_CURRENCY_VALUE("delete uhc from user_has_currencies uhc join currencies us on uhs.currency_id = us.id where uhs.uuid=? and us.name=?;"),

    //STATS
    SELECT_ALL_STATS_VALUES("select s.name, uhs.value from user_has_stats uhs join stats s on uhs.setting_id = s.id where uuid=?;"),
    SELECT_ALL_STAT_KEYS("select name from stats;"),
    INSERT_STAT_KEY("insert ignore into stats(name) value (?);"),
    UPDATE_STAT_VALUE("insert into user_has_stats (stat_id, uuid, value) values ((select id from stats where name=?),?,?) on duplicate key update value=?;"),
    DELETE_STAT_VALUE("delete uhs from user_has_stats uhs join stats s on uhs.stat_id = s.id where uhs.uuid=? and s.name=?;"),

    //WARPS
    SELECT_ALL_WARPS("select * from warps;"),
    INSERT_WARP("insert into warps(name, world, x, y, z, yaw, pitch, owner, is_public) value (?,?,?,?,?,?,?,?,?) on duplicate key update is_public=?;"),
    DELETE_WARP("delete from warps where name=?;");

    private String query;

    Query(String query){
        this.query = query;
    }

}
