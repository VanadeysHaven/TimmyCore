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
    UPDATE_SETTING_VALUE("insert into user_has_settings (setting_id, uuid, value) values ((select get_user_setting_id(?)),?,?) on duplicate key update value=?;"),
    DELETE_SETTING_VALUE("delete uhs from user_has_settings uhs join user_settings us on uhs.setting_id = us.id where uhs.uuid=? and us.name=?;"),

    //WARPS
    SELECT_ALL_WARPS("select * from warps;"),
    INSERT_WARP("insert into warps(name, world, x, y, z, yaw, pitch, owner, is_public) value (?,?,?,?,?,?,?,?,?) on duplicate key update is_public=?;"),
    DELETE_WARP("delete from warps where name=?;");

    private String query;

    Query(String query){
        this.query = query;
    }

}
