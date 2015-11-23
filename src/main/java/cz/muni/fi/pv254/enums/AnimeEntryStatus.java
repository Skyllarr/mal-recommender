package cz.muni.fi.pv254.enums;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by suomiy on 11/21/15.
 */
public enum AnimeEntryStatus {
    @SerializedName("1")
    CURRENTLY_WATCHING(1),
    @SerializedName("2")
    COMPLETED(2),
    @SerializedName("3")
    ON_HOLD(3),
    @SerializedName("4")
    DROPPED(4),
    @SerializedName("6")
    PLAN_TO_WATCH(6),
    @SerializedName("7")
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
