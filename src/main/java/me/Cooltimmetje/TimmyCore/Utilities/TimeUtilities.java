package me.Cooltimmetje.TimmyCore.Utilities;

import java.text.MessageFormat;

public final class TimeUtilities {

    public static String formatTime(long millisRemaining){
        long time = millisRemaining / 1000;
        long secondsRemaining = time % 60;
        time = (time - secondsRemaining) / 60;
        long minutesRemaining = time % 60;
        time = (time - minutesRemaining) / 60;
        long hoursRemaining = time % 24;
        long daysRemaining = (time - hoursRemaining) / 24;

        return MessageFormat.format("{0}d {1}h {2}m {3}s", daysRemaining, hoursRemaining, minutesRemaining, secondsRemaining);
    }

}