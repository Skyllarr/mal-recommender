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

/**
 * Created by skylar on 27.11.15.
 */
public class OneSlopeDb {

    public List<Pair<Integer, Double>> findRow(List<Pair<Long, List<Pair<Integer, Double>>>> storage, Anime anime) {
        for(Pair<Long, List<Pair<Integer, Double>>> list : storage) {
            if (list.getFirst().equals(anime.getMalId())) {
                return list.getSecond();
            }
        }
        return null;
    }

    public List<Long> getListOfIds(List<Pair<Long, List<Pair<Integer, Double>>>> storage) {
        List<Long> listOfIds = new ArrayList<>();
        for(Pair<Long, List<Pair<Integer, Double>>> list : storage) {
            listOfIds.add(list.getFirst());
        }
        return listOfIds;
    }

    public Integer getIndex(List<Long> listOfIds, AnimeEntry anime) {
        Long index = 0l;
        for (Long id : listOfIds) {
            index++;
            if (id == anime.getMalId()) {
                return Ints.checkedCast(index);
            }
        }
        return null;
    }

    public List<Pair<Long, Double>> computeUser(List<AnimeEntry> entries, DataStore dataStore, List<Pair<Long, List<Pair<Integer, Double>>>> storage) {
        List<Long> listOfIds = getListOfIds(storage);
        List<Pair<Long, Double>> recommValues = new ArrayList<>();

        Double value;
        double nominator = 0l;
        double denominator = 0l;
        for(Anime unSeenAnime : dataStore.findAnimes(anime -> !entries.contains(anime))) { // anime co nevidela
            List<Pair<Integer, Double>> row = findRow(storage, unSeenAnime); //najdi riadok unSeenAnime
            for (AnimeEntry seenAnime : entries) { //anime co videla

                Pair<Integer, Double> dbUnseenSeen = row.get(getIndex(listOfIds, seenAnime));
                nominator += dbUnseenSeen.getFirst() * (seenAnime.getNormalizedScore() + dbUnseenSeen.getSecond());
                denominator += dbUnseenSeen.getFirst();
            }

            recommValues.add(new Pair<>(unSeenAnime.getMalId(), nominator / denominator));
        }
        return recommValues;
    }

    public List<Pair<Long, List<Pair<Integer, Double>>>> computeDb(DataStore dataStore) {
        //dataStore.fetchDataForOneSlope();

        Map<Long,Map<AnimeEntry, AnimeEntry>> usersWithBothAnimes = dataStore.getUsersAsMapOfMaps();
        List<Anime> animes = dataStore.findAllAnimes();
        Map<Long,Map<AnimeEntry, AnimeEntry>> dumpedUsers = new HashMap<>();
        Map<Long,Map<AnimeEntry, AnimeEntry>> dumpedUsers2 = new HashMap<>();
        List<Pair<Long, List<Pair<Integer, Double>>>> storage = new ArrayList<>();

        for (Anime anime1 : animes) {
            List<Pair<Integer, Double>> animeDiff = new ArrayList<>();
            storage.add(new Pair<>(anime1.getMalId(),animeDiff));
            for (Anime anime2 : animes) {
                if (anime1.equals(anime2)) {
                    continue;
                }
                filterAnime(usersWithBothAnimes, dumpedUsers2, anime1, anime2);
                double animeDifferenceCount = 0.0;
                for (Map<AnimeEntry, AnimeEntry> processedUser : usersWithBothAnimes.values()) {
                    animeDifferenceCount += processedUser.get(anime1).getNormalizedScore() -
                            processedUser.get(anime2).getNormalizedScore();
                }
                Pair<Integer, Double> pair = usersWithBothAnimes.size() == 0 ? null : new Pair<>(usersWithBothAnimes.size(), animeDifferenceCount);
                animeDiff.add(pair);
                usersWithBothAnimes.putAll(dumpedUsers2);
                dumpedUsers2.clear();
            }
        }

        return storage;
    }

    private void filterAnime(Map<Long,Map<AnimeEntry, AnimeEntry>> usersWithBothAnimes,
                             Map<Long,Map<AnimeEntry, AnimeEntry>> dumpedByAnime1,
                             Anime anime) {
        usersWithBothAnimes.putAll(dumpedByAnime1);
        dumpedByAnime1.clear();
        for (Long key : usersWithBothAnimes.keySet()) {
            Map<AnimeEntry, AnimeEntry> entries = usersWithBothAnimes.get(key);
            if (entries.containsKey(anime)) {
                dumpedByAnime1.put(key, entries);
                usersWithBothAnimes.remove(key);
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
