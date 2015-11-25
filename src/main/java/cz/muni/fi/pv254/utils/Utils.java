package cz.muni.fi.pv254.utils;

/**
 * Created by suomiy on 11/24/15.
 */
public class Utils {

    public static String removeEmptyStr(String string) {
        return string != null && string.isEmpty() ? null : string;
    }
}
