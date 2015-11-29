package cz.muni.fi.pv254.test;

import cz.muni.fi.pv254.algorithms.TextAnalyzer;
import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.User;
import cz.muni.fi.pv254.data.enums.AnimeEntryStatus;
import cz.muni.fi.pv254.dataUtils.DataStore;
import cz.muni.fi.pv254.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suomiy on 24.11.15.
 */
public class TestTextAnalyzer {

        public static void run(DataStore dataStore) {


                Anime anime1 = new Anime();
                Anime anime2 = new Anime();
                Anime anime3 = new Anime();
                Anime anime4 = new Anime();

                anime1.setMalId(1l);
                anime1.setTitle("A");
                anime2.setMalId(2l);
                anime2.setTitle("B");
                anime3.setMalId(3l);
                anime3.setTitle("C");
                anime4.setMalId(4l);
                anime4.setTitle("D");

                anime1.setDescription("cowboy fantasy drama");
                anime2.setDescription("vjfeiv cowboy");
                anime3.setDescription("jjaa kio sdvmr kvoe jkvf");
                anime4.setDescription("cowboy fantasy drama");

                List<Anime> anime = new ArrayList<>();
                anime.add(anime1);
                anime.add(anime2);
                anime.add(anime3);
                anime.add(anime4);
                //dataStore.setData(new ArrayList<>(), anime);
                dataStore.fetchData();
                TextAnalyzer textAnalyzer = new TextAnalyzer(dataStore);
                textAnalyzer.preprocess(true);
                User user = new User();
                List<AnimeEntry> listAnimeEntries = new ArrayList<>();
                listAnimeEntries.add(new AnimeEntry(269l, 8l, AnimeEntryStatus.COMPLETED));//Long malId, Long score, AnimeEntryStatus status
                //Long malId, Long score, AnimeEntryStatus status

                //Anime animee = dataStore.findAnimeByMalId(45l);
                //listAnimeEntries.add(new AnimeEntry(77l, 10l, AnimeEntryStatus.COMPLETED));
                user.setAnimeEntries(listAnimeEntries);
                Utils.showSortedDouble(textAnalyzer.recommendToUser(user, dataStore));
        }
}
