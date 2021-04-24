package me.VanadeysHaven.TimmyCore.Packages.Warp.Exceptions;

public class WarpDoesNotExistException extends RuntimeException {

    protected String warpName;

    public WarpDoesNotExistException(String warpName){
        this.warpName = warpName;
    }

    @Override
    public String getMessage() {
        return "&aWarp &b" + warpName + " &adoes not exist.";
    }

}
