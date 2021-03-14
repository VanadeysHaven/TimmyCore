package me.Cooltimmetje.TimmyCore.Packages.Warp.Exceptions;

public final class NameTooLongException extends WarpDoesNotExistException {

    private int maxLength;

    public NameTooLongException(String warpName, int maxLength) {
        super(warpName);
        this.maxLength = maxLength;
    }

    @Override
    public String getMessage() {
        return "&aThe name if warp &b" + warpName + "&a is too long! (Maximum: &b" + maxLength + " &acharacters)";
    }

}
