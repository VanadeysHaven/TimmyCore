package me.VanadeysHaven.TimmyCore.Exceptions;

public final class CooldownException extends RuntimeException {

    private String timeRemaining;

    public CooldownException(String timeRemaining){
        this.timeRemaining = timeRemaining;
    }

    @Override
    public String getMessage() {
        return "&aThis action is currently on cooldown! Time remaining: &b" + timeRemaining;
    }
}
