package cz.muni.fi.pv254.utils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by suomiy on 11/24/15.
 */
public class Utils {

    private static final int strLen = 1;

    public static String removeEmptyStr(String string) {
        return string != null && string.isEmpty() ? null : string;
    }

    public static void show(Object message){
        System.out.println(message);
    }

    public static <K, V> void show(Map<K, V> map) {
        map.forEach((k, v)->{
            String s  = k == null ? null : k.toString().substring(0, Math.min(k.toString().length(), strLen));
            System.out.println(String.format(": %" + strLen +"s", s) + ": " + v);
        });
        System.out.println();
    }

    public static <K> void showSorted(Map<K, Integer> map) {
        show(sortByValue(map));
    }

    public static void showSortedByKeys(Map<Object, Integer> map) {
        Map<Double, Integer> result = new HashMap<>();
        map.entrySet().forEach((e) -> result.put(((Number)e.getKey()).doubleValue(), e.getValue()));
        show(sortByKey(result));
    }


    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map ) {
        List<Map.Entry<K, V>> list = new LinkedList<>( map.entrySet() );
        Collections.sort( list, (o1, o2) -> (o1.getValue()).compareTo( o2.getValue() ));
        return getKvMap(list);
    }

    private static <K extends Comparable<? super K>, V> Map<K, V> sortByKey( Map<K, V> map ) {
        List<Map.Entry<K, V>> list = new LinkedList<>( map.entrySet() );
        Collections.sort( list, (o1, o2) -> (o2.getKey()).compareTo( o1.getKey() ));
        return getKvMap(list);
    }

    private static <K, V> Map<K, V> getKvMap(List<Map.Entry<K, V>> list) {
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list){
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }
}
