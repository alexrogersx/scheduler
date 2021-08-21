package helpers;

import java.time.format.DateTimeFormatter;

/**
 * Abstract class with static variables to organize all format objects used in app.
 */
public abstract class Format {
    public static final DateTimeFormatter dateTimeFormat1 = DateTimeFormatter.ofPattern("LLLL d h:mm a");
    public static final DateTimeFormatter timeFormat1 = DateTimeFormatter.ofPattern("h:mm a");
}
