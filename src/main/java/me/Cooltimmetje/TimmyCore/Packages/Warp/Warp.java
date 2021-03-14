package me.Cooltimmetje.TimmyCore.Packages.Warp;

import lombok.Getter;
import lombok.Setter;
import me.Cooltimmetje.TimmyCore.Data.Database.Query;
import me.Cooltimmetje.TimmyCore.Data.Database.QueryExecutor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public final class Warp {

    @Getter private String name;
    @Getter private Location location;
    @Getter private String owner;
    @Getter @Setter private boolean isPublic;

    public Warp(String name, Location location, String owner, boolean isPublic){
        this.name = name;
        this.location = location;
        this.owner = owner;
        this.isPublic = isPublic;
    }

    public void save(){
        QueryExecutor qe = null;
        try {
            qe = new QueryExecutor(Query.INSERT_WARP);
            qe.setString(1, name).setString(8, owner).setBoolean(9, isPublic).and(10);
            qe.setString(2, location.getWorld().getName()).setDouble(3, location.getX()).setDouble(4, getLocation().getY()).setDouble(5, getLocation().getZ());
            qe.setFloat(6, getLocation().getYaw()).setFloat(7, getLocation().getPitch());

            qe.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert qe != null;
            qe.close();
        }
    }


    public void delete(){
        QueryExecutor qe = null;
        try {
            qe = new QueryExecutor(Query.DELETE_WARP);
            qe.setString(1, name);

            qe.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert qe != null;
            qe.close();
        }
    }

    public boolean isOwner(Player p){
        return isOwner(p.getUniqueId().toString());
    }

    public boolean isOwner(String s){
        return s.equals(owner);
    }

}
