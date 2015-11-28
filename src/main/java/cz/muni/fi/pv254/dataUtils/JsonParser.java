package cz.muni.fi.pv254.dataUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mysema.commons.lang.Pair;
import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.enums.Genre;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by suomiy on 11/23/15.
 */
public class JsonParser {


    public static String save(List<?> entries){
        Gson gson = new Gson();
        return gson.toJson(entries);
    }

    public static List<AnimeEntry> loadAsAnimeEntry(String entries){
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<AnimeEntry>>() {}.getType();
        return (entries == null || "".equals(entries)) ?  new ArrayList<>() : gson.fromJson(entries, listType);
    }

    public static List<Pair<Long, Double>> loadAsDifferenceVector(String entries){
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Pair<Long, Double>>>() {}.getType();
        return (entries == null || "".equals(entries)) ?  new ArrayList<>() : gson.fromJson(entries, listType);
    }

    public static List<Genre> loadAsGenre(String entries){
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Genre>>() {}.getType();
        return (entries == null || "".equals(entries)) ?  new ArrayList<>() : gson.fromJson(entries, listType);
    }
}
