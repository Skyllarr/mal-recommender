package cz.muni.fi.pv254.utils;

import java.util.*;

/**
 * Created by suomiy on 11/24/15.
 */
public class Utils {

    private static final int strLen = 100;

    public static String removeEmptyStr(String string) {
        return string != null && string.isEmpty() ? null : string;
    }

    public static void show(Object message){
        System.out.println(message);
    }

    public static <K, V> void show(Map<K, V> map) {
        map.forEach((k, v)->{
            String s  = k == null ? null : k.toString().substring(0, Math.min(k.toString().length(), strLen));
            System.out.println(String.format("# %" + strLen +"s", s) + "# " + v);
        });
        System.out.println();
    }

    public static  <K extends Comparable<? super K>, V> void showByListSize(Map<K, List<V>> map) {
        map = sortByKey(map);
        map.forEach((k, v)->{
            System.out.println("#" + k + "# " + v.size());
        });
        System.out.println();
    }

    public static <K> void showSorted(Map<K, Integer> map) {
        show(sortByValue(map));
    }

    public static <K> void showSortedDouble(Map<K, Double> map) {
        show(sortByValue(map));
    }

    public static <K> void showSortedFloat(Map<K, Float> map) {
        show(sortByValue(map));
    }

    public static void showSortedByKeys(Map<Object, Integer> map) {
        Map<Double, Integer> result = new HashMap<>();
        map.entrySet().forEach((e) -> result.put(((Number)e.getKey()).doubleValue(), e.getValue()));
        show(sortByKey(result));
    }

    public static void showSortedByKeysDouble(Map<Object, Double> map) {
        Map<Double, Double> result = new HashMap<>();
        map.entrySet().forEach((e) -> result.put(((Number)e.getKey()).doubleValue(), e.getValue()));
        show(sortByKey(result));
    }



    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueASC( Map<K, V> map ) {
        List<Map.Entry<K, V>> list = new LinkedList<>( map.entrySet() );
        Collections.sort( list, (o1, o2) -> (o1.getValue()).compareTo( o2.getValue() ));
        return getKvMap(list);
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map ) {
        List<Map.Entry<K, V>> list = new LinkedList<>( map.entrySet() );
        Collections.sort( list, (o1, o2) -> (o2.getValue()).compareTo( o1.getValue() ));
        return getKvMap(list);
    }

    private static <K extends Comparable<? super K>, V> Map<K, V> sortByKey( Map<K, V> map ) {
        List<Map.Entry<K, V>> list = new LinkedList<>( map.entrySet() );
        Collections.sort( list, (o1, o2) -> {
            K k1 = o1.getKey();
            K k2 = o2.getKey();

            if(k1 == null && k2 == null){
                return 0;
            }

            if(k2 == null){
                return -1;
            }

            if(k1 == null){
                return 1;
            }

            return k2.compareTo(k1);
        });
        return getKvMap(list);
    }

    private static <K, V> Map<K, V> getKvMap(List<Map.Entry<K, V>> list) {
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list){
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }

    public static <T> List<T> getRandomSubList(List<T> input, int subsetSize)
    {
        Random r = new Random();
        int inputSize = input.size();
        for (int i = 0; i < subsetSize; i++)
        {
            int indexToSwap = i + r.nextInt(inputSize - i);
            T temp = input.get(i);
            input.set(i, input.get(indexToSwap));
            input.set(indexToSwap, temp);
        }
        return input.subList(0, subsetSize);
    }
}
