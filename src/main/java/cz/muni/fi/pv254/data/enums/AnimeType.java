package cz.muni.fi.pv254.data.enums;

import java.util.HashMap;
import java.util.Map;

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
    private static final Map<Integer ,AnimeType> map =  new HashMap<>();

    static {
        for(AnimeType type : AnimeType.values()){
            map.put(type.getValue(), type);
        }
    }

    AnimeType(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }

    public static AnimeType get(int value) {
        return map.get(value);
    }
}
