package cz.muni.fi.pv254.enums;

/**
 * Created by suomiy on 11/21/15.
 */
public enum AnimeEntryStatus {
    CURRENTLY_WATCHING(1),
    COMPLETED(2),
    ON_HOLD(3),
    DROPPED(4),
    PLAN_TO_WATCH(6),
    ALL_ANIME(7);

    private final int value;

    AnimeEntryStatus(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}
