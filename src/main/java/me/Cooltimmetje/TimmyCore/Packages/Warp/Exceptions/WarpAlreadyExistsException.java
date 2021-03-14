package me.Cooltimmetje.TimmyCore.Packages.Warp.Exceptions;

public final class WarpAlreadyExistsException extends WarpDoesNotExistException {

    public WarpAlreadyExistsException(String warpName) {
        super(warpName);
    }

    @Override
    public String getMessage() {
        return "&aWarp &b" + warpName + " &aalready exists.";
    }

}
