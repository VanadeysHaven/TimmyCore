package me.Cooltimmetje.TimmyCore.Packages.Warp.Exceptions;

public final class NotOwnerOfWarpException extends WarpDoesNotExistException {

    public NotOwnerOfWarpException(String warpName) {
        super(warpName);
    }

    @Override
    public String getMessage() {
        return "&aYou are not the owner of warp &b" + warpName + "&a!";
    }

}
