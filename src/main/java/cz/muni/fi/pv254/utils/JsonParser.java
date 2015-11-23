package cz.muni.fi.pv254.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cz.muni.fi.pv254.entity.AnimeEntry;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by suomiy on 11/23/15.
 */
public class JsonParser {


    public static String save(List<AnimeEntry> entries){
        Gson gson = new Gson();
        return gson.toJson(entries);
    }

    public static List<AnimeEntry> load(String entries){
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<AnimeEntry>>() {
        }.getType();
        return (entries == null || "".equals(entries)) ?  new ArrayList<>() : gson.fromJson(entries, listType);
    }
}
