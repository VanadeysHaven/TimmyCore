package me.Cooltimmetje.TimmyCore.Packages.Warp;

import me.Cooltimmetje.TimmyCore.Data.Database.Query;
import me.Cooltimmetje.TimmyCore.Data.Database.QueryExecutor;
import me.Cooltimmetje.TimmyCore.Data.Database.QueryResult;
import me.Cooltimmetje.TimmyCore.Packages.Warp.Exceptions.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class WarpManager {

    private static final int MAX_WARPS = 5;
    private static final int MAX_LENGTH = 16;

    private ArrayList<Warp> warps;

    public WarpManager(){
        this.warps = new ArrayList<>();
        loadAllWarps();
    }

    public Warp getWarp(String name){
        name = name.toLowerCase();
        for(Warp warp : warps)
            if(warp.getName().equals(name))
                return warp;

        throw new WarpDoesNotExistException(name);
    }

    public List<String> listWarpsForPlayer(Player p, boolean onlyOwned){
        ArrayList<String> list = new ArrayList<>();
        for(Warp warp : warps)
            if(p.isOp() || warp.isPublic() || p.getUniqueId().toString().equals(warp.getOwner()))
                if(!onlyOwned || p.getUniqueId().toString().equals(warp.getOwner()))
                    list.add(warp.getName());

        return list;
    }

    public String formatWarpsForPlayer(Player p, boolean onlyOwned){
        StringBuilder sb = new StringBuilder();
        for(String warp : listWarpsForPlayer(p, onlyOwned))
            sb.append("&a, &b").append(warp);

        return sb.substring(4).trim();
    }

    public boolean doesWarpExist(String name){
        try {
            getWarp(name);
            return true;
        } catch (WarpDoesNotExistException ignored){
            return false;
        }
    }

    private void addWarp(String name, Location location, String owner, boolean isPublic){
        Warp warp = new Warp(name, location, owner, isPublic);
        warps.add(warp);
    }

    public void addWarp(String name, Location location, Player owner, boolean isPublic){
        if(doesWarpExist(name))
            throw new WarpAlreadyExistsException(name);
        if(name.length() > MAX_LENGTH)
            throw new NameTooLongException(name, MAX_LENGTH);
        if(getWarpCountForPlayer(owner) >= MAX_WARPS)
            if(!owner.isOp())
                throw new MaxWarpCountExceededException(MAX_WARPS);

        Warp warp = new Warp(name, location, owner.getUniqueId().toString(), isPublic);
        warps.add(warp);
        warp.save();
    }

    public void deleteWarp(String name, Player requester){
        Warp warp = getWarp(name);
        if(!requester.getUniqueId().toString().equals(warp.getOwner()) && !requester.isOp())
            throw new NotOwnerOfWarpException(name);

        warps.remove(warp);
        warp.delete();
    }

    public void setWarpPublic(String name, Player requester, boolean newPublic){
        Warp warp = getWarp(name);
        if(!requester.getUniqueId().toString().equals(warp.getOwner()) && !requester.isOp())
            throw new NotOwnerOfWarpException(name);


        warp.setPublic(newPublic);
        warp.save();
    }

    public int getWarpCountForPlayer(Player p){
        int count = 0;
        for(Warp warp : warps)
            if(p.getUniqueId().toString().equals(warp.getOwner()))
                count++;

        return count;
    }

    public void loadAllWarps(){
        warps.clear();

        QueryExecutor qe = null;
        try {
            qe = new QueryExecutor(Query.SELECT_ALL_WARPS);
            QueryResult qr = qe.executeQuery();

            while (qr.nextResult()) {
                Location location = new Location(Bukkit.getWorld(qr.getString("world")),
                        qr.getDouble("x"), qr.getDouble("y"), qr.getDouble("z"),
                        qr.getFloat("yaw"), qr.getFloat("pitch"));
                addWarp(qr.getString("name"), location, qr.getString("owner"), qr.getBoolean("is_public"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert qe != null;
            qe.close();
        }
    }

    public void saveAll(){
        for(Warp warp : warps)
            warp.save();
    }

}
