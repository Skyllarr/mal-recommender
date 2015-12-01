package cz.muni.fi.pv254.algorithms;

import com.mysema.commons.lang.Pair;
import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.User;
import cz.muni.fi.pv254.dataUtils.DataStore;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cz.muni.fi.pv254.utils.Utils.show;

/**
 * Created by suomiy on 11/27/15.
 */
public class TextAnalyzer {

    private Map<Anime, Map< String, Double>> corpus;
    private List<Anime> allAnimes;

    public TextAnalyzer(DataStore dataStore){
        allAnimes = dataStore.findAnimesForTextAnalysis();
    }

    public void preProcess(boolean debug) {
        corpus = allAnimes.stream().collect(Collectors.toMap(anime -> anime, this::computeTermFrequency));
        int debugCount = 0;
        for (Anime anime : allAnimes) {
            computeTextSimilarity(allAnimes, anime);
            if(debug){
                show(++debugCount + " / " + allAnimes.size());
            }
        }
    }

    public Map<Anime, Double> recommendToUser(User user, DataStore dataStore){
        Map<Anime, Double> results = new HashMap<>();
        Double average = Normalizer.calculateAverage(user.getAnimeEntries());
        List<AnimeEntry> favoriteAnimes = user.getAnimeEntries().stream()
                .filter(e -> e.getScore() >= average)
                .collect(Collectors.toList());

        List<Double> resultSimilarityVector = Stream.generate( () -> (double) 0)
                .limit(allAnimes.size())
                .collect(Collectors.toList());

        for (AnimeEntry animeEntry : favoriteAnimes) {
            Anime anime = dataStore.findAnimeByMalId(animeEntry.getMalId());
            List<Double> similarityVector = anime.getDescriptionSimilarityVector();

            for(int i = 0; i < similarityVector.size(); i++) {
                Double similarity = similarityVector.get(i);
                resultSimilarityVector.set(i, resultSimilarityVector.get(i) + (similarity == null ? 0D : similarity));
            }
        }

        for(int i = 0; i < resultSimilarityVector.size(); i++) {
            Anime iteratedAnime = allAnimes.get(i);
            if (!user.getAnimeEntries().contains(iteratedAnime)) {
                results.put(iteratedAnime, resultSimilarityVector.get(i));
            }
        }

        return results;
    }

    private void computeTextSimilarity(List<Anime> list, Anime anime) {
        Map<String, Double> animeKeywordsFrequency = corpus.get(anime);
        Map<String, Integer> keywordsOccurenceCountMap = new HashMap<>();

        for (String keyword : animeKeywordsFrequency.keySet()) {
            for (Map.Entry<Anime,  Map<String, Double>> entry : corpus.entrySet()) {
                if (!entry.getValue().containsKey(keyword)) {
                    continue;
                }

                if (entry.getValue().get(keyword) > 0) {
                    Integer count = 1;

                    if(keywordsOccurenceCountMap.containsKey(keyword)){
                        count += keywordsOccurenceCountMap.get(keyword);
                    }

                    keywordsOccurenceCountMap.put(keyword, count);
                }
            }
        }

        for (Anime doc : list) {
            List<Double> similarityVector = anime.getDescriptionSimilarityVector();
            if(anime == doc){
                similarityVector.add(null);
                continue;
            }

            double valueForDoc = 0D;

            for (String keyword : animeKeywordsFrequency.keySet()) {
                valueForDoc += computeInverseDocumentFrequency(doc, keyword, keywordsOccurenceCountMap.get(keyword));
            }

            similarityVector.add(valueForDoc);
        }
    }

    private Double computeInverseDocumentFrequency(Anime anime, String keyword, double numDocsContainingKeyword){
        if (corpus.get(anime).containsKey(keyword)) {
            return corpus.get(anime).get(keyword) * Math.log(corpus.size() /  numDocsContainingKeyword);
        }
        return 0D;
    }

    private  Map<String, Double> computeTermFrequency(Anime anime){
        Double maxFreq = 0D;
        Map<String, Double> documentCorpus = new TreeMap<>();

        if (anime.getDescription() == null) {
            return documentCorpus;
        }

        for(String word : anime.getDescription().split("\\W")){
            if("".equals(word)){
                continue;
            }
            word = word.toLowerCase();

            if(documentCorpus.containsKey(word)){
                Double count = documentCorpus.get(word) + 1;
                if(count > maxFreq){
                    maxFreq = count;
                }
                documentCorpus.replace(word, count);
            }else{
                documentCorpus.put(word, 1D);
            }
        }
        if(maxFreq != 0){//optimization against long docs - augmented frequency
            final Double finalMaxFreq = maxFreq;
            documentCorpus.replaceAll((k, v) -> v / finalMaxFreq);
        }
        return documentCorpus;
    }
}
