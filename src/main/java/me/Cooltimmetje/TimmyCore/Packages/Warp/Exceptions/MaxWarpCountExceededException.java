package me.Cooltimmetje.TimmyCore.Packages.Warp.Exceptions;

public final class MaxWarpCountExceededException extends RuntimeException {

    int maxAllowed;

    public MaxWarpCountExceededException(int maxAllowed){
        this.maxAllowed = maxAllowed;
    }

    @Override
    public String getMessage() {
        return "&aYou can only create a maximum of &b" + maxAllowed + " &awarps.";
    }
}
