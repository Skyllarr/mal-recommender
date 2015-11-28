package cz.muni.fi.pv254.algorithms;

import com.google.common.primitives.Ints;
import com.mysema.commons.lang.Pair;
import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.User;
import cz.muni.fi.pv254.dataUtils.DataStore;

import javax.xml.crypto.Data;
import java.util.*;
import java.util.stream.Collectors;

import static cz.muni.fi.pv254.utils.Utils.show;

/**
 * Created by skylar on 27.11.15.
 */

public class OneSlope {
    private DataStore dataStore;
    private boolean debug;

    public OneSlope(DataStore dataStore, boolean debug) {
        this.dataStore = dataStore;
        this.debug = debug;
    }

    public Map<Anime, Double> recommendToUser(User user) {
        List<AnimeEntry> entries = user.getAnimeEntries();
        Map<Long, Integer>  mapOfIds = new HashMap<>();
        List<Anime> animeList = dataStore.findAllAnimes();
        for(int i = 0; i < animeList.size(); i++){
            mapOfIds.put(animeList.get(i).getMalId(), i);
        }

        List<Pair<Long, Double>> recommValues = new ArrayList<>();
        double nominator = 0;
        double denominator = 0;

        for(Anime unSeenAnime : dataStore.findAnimes(anime -> !entries.contains(anime))) { // anime co nevidela
            List<Pair<Integer, Double>> row = unSeenAnime.getDifferenceVector(); //najdi riadok unSeenAnime

            for (AnimeEntry seenAnime : entries) { //anime co videla
                Pair<Integer, Double> dbUnseenSeen = row.get(mapOfIds.get(seenAnime.getMalId()));
                if (dbUnseenSeen == null) {
                    continue;
                }

                nominator += dbUnseenSeen.getFirst() * (seenAnime.getNormalizedScore() + dbUnseenSeen.getSecond());
                denominator += dbUnseenSeen.getFirst();
            }
            recommValues.add(new Pair<>(unSeenAnime.getMalId(), denominator == 0 ? 0 : nominator / denominator));
            nominator = 0;
            denominator = 0;
        }

        return recommValues.stream().collect(Collectors.toMap( p -> dataStore.findAnimeByMalId(p.getFirst()), Pair::getSecond));
    }

    public void preProcess() {

        Map<Long,Map<AnimeEntry, AnimeEntry>> usersWithBothAnimes = dataStore.getUsersAsMapOfMaps();
        List<Anime> animes = dataStore.findAllAnimes();
        Map<Long,Map<AnimeEntry, AnimeEntry>> dumpedUsers = new HashMap<>();
        int debugCount = 0;

        for (Anime anime1 : animes) {
            List<Pair<Integer, Double>> animeDiff = anime1.getDifferenceVector();
            animeDiff.clear();
            for (Anime anime2 : animes) {
                if (anime1.equals(anime2)) {
                    animeDiff.add(null);
                    continue;
                }
                filterAnime(usersWithBothAnimes, dumpedUsers, anime1, anime2);
                double animeDifferenceCount = 0.0;

                for (Map<AnimeEntry, AnimeEntry> processedUser : usersWithBothAnimes.values()) {
                    animeDifferenceCount += processedUser.get(anime1).getNormalizedScore() -
                            processedUser.get(anime2).getNormalizedScore();
                }
                boolean zeroSize = usersWithBothAnimes.size() == 0;
                Pair<Integer, Double> pair = new Pair<>(zeroSize ? 0 : usersWithBothAnimes.size(), zeroSize ? 0 : (animeDifferenceCount / usersWithBothAnimes.size()));
                animeDiff.add(pair);
                usersWithBothAnimes.putAll(dumpedUsers);
                dumpedUsers.clear();
            }

            if(debug){
                show(++debugCount + " / " + animes.size());
            }
        }
    }

    private void filterAnime(Map<Long,Map<AnimeEntry, AnimeEntry>> usersWithBothAnimes,
                             Map<Long,Map<AnimeEntry, AnimeEntry>> dumpedByAnime1, Anime anime1, Anime anime2) {
        for (Iterator<Map.Entry<Long, Map<AnimeEntry, AnimeEntry>>> it = usersWithBothAnimes.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Long, Map<AnimeEntry, AnimeEntry>> entry = it.next();
            Map<AnimeEntry, AnimeEntry> entries = entry.getValue();
            Long key =entry.getKey();

            if (!(entries.containsKey(anime1) && entries.containsKey(anime2))) {
                dumpedByAnime1.put(key, entries);
                it.remove();
            }
        }
    }
}
