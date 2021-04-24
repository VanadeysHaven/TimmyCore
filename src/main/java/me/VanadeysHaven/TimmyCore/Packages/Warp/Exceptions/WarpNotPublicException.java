package me.VanadeysHaven.TimmyCore.Packages.Warp.Exceptions;

public final class WarpNotPublicException extends WarpDoesNotExistException {

    public WarpNotPublicException(String warpName) {
        super(warpName);
    }

    @Override
    public String getMessage() {
        return "&aWarp &b" + warpName + " &ais not public.";
    }

}
