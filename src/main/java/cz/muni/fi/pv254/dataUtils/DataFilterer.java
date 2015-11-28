package cz.muni.fi.pv254.dataUtils;


import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by suomiy on 11/28/15.
 */
public class DataFilterer {

    private final int stayCount = 1300;
    private final int stayOffset = 60;
    private final int stayTailOffset = 40;
    private int stayModulo = 0;
    private int position = 0;

    public  void run(Map<Anime, Integer> animes, List<User> users){
        Map<Anime, Anime> filteredAnimeMap;
        stayModulo = (int) Math.ceil((animes.size() - stayOffset - stayTailOffset) / (double) stayCount);

        animes.forEach((k, v)->{
            boolean delete = !(position < stayOffset || animes.size() - stayTailOffset < position || position % stayModulo ==  0);
            position++;
            k.setDeleted(delete);
        });

        filteredAnimeMap = animes.entrySet()
                .stream()
                .filter(e -> !e.getKey().isDeleted())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getKey));

        users.forEach(u -> {
            List<AnimeEntry> entries = u.getAnimeEntries();
            for (Iterator<AnimeEntry> iterator = entries.iterator(); iterator.hasNext();) {
                AnimeEntry entry = iterator.next();
                if(entry.getScore() == null || entry.getScore() == 0 || !filteredAnimeMap.containsKey(entry)){
                    iterator.remove();
                }
            }
            if(entries.size() == 0){
                u.setDeleted(true);
            }
        });

        List<AnimeEntry>  animeEntries = new ArrayList<>();
        users.forEach(u -> animeEntries.addAll(u.getAnimeEntries()));

        System.out.println("Animes left: " + filteredAnimeMap.size());
        System.out.println("Entries left: " + animeEntries.size());
        System.out.println("Users left: " + users.stream().filter(u -> !u.isDeleted()).count());
    }
}
