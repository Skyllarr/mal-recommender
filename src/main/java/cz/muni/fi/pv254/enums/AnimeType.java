package cz.muni.fi.pv254.enums;

/**
 * Created by suomiy on 11/21/15.
 */
public enum AnimeType {
    TV(1),
    OVA(2),
    MOVIE(3),
    SPECIAL(4),
    ONA(5),
    MUSIC(6);

    private final int value;

    AnimeType(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}
