package cz.muni.fi.pv254.utils;

import java.util.*;

/**
 * Created by suomiy on 11/24/15.
 */
public class Utils {

    public static String removeEmptyStr(String string) {
        return string != null && string.isEmpty() ? null : string;
    }

    public static void show(Map<Object, Integer> map) {
        map.forEach((k, v)->{
            String s  = k == null ? null : k.toString().substring(0, Math.min(k.toString().length(), 20));
            System.out.println(String.format("%20s", s) + ": " + v);
        });
        System.out.println();
    }

    public static void showSorted(Map<Object, Integer> map) {
        show(sortByValue(map));
    }

    public static void show(Object message){
        System.out.println(message);
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map ) {
        List<Map.Entry<K, V>> list = new LinkedList<>( map.entrySet() );
        Collections.sort( list, (o1, o2) -> (o2.getValue()).compareTo( o1.getValue() ));

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }
}
