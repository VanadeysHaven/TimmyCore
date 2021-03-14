package me.Cooltimmetje.TimmyCore.Database;

import lombok.Getter;

@Getter
public enum Query {

    //USERS
    INSERT_USER("insert into users(uuid, username) value (?,?) on duplicate key update username=?;"),

    //WARPS
    SELECT_ALL_WARPS("select * from warps;"),
    INSERT_WARP("insert into warps(name, world, x, y, z, yaw, pitch, owner, is_public) value (?,?,?,?,?,?,?,?,?) on duplicate key update is_public=?;"),
    DELETE_WARP("delete from warps where name=?;");

    private String query;

    Query(String query){
        this.query = query;
    }

}
