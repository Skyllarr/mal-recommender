package cz.muni.fi.pv254.enums;

import java.util.HashMap;
import java.util.Map;

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
    private static final Map<Integer ,AnimeEntryStatus> map =  new HashMap<>();

    static {
        for(AnimeEntryStatus status : AnimeEntryStatus.values()){
            map.put(status.getValue(), status);
        }
    }

    AnimeEntryStatus(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }

    public static AnimeEntryStatus get(int value) {
        return map.get(value);
    }
}
