package cz.muni.fi.pv254.algorithms;

import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.User;
import cz.muni.fi.pv254.data.entity.AnimeDescriptionVector;
import cz.muni.fi.pv254.data.enums.AnimeEntryStatus;
import cz.muni.fi.pv254.dataUtils.DataStore;
import cz.muni.fi.pv254.init.Setup;
import cz.muni.fi.pv254.repository.entityrepository.AnimeDescriptionVectorRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cz.muni.fi.pv254.utils.Utils.show;

/**
 * Created by suomiy on 11/27/15.
 */
@ApplicationScoped
public class TextAnalyzer {

    @Inject
    DataStore dataStore;

    @Inject
    AnimeDescriptionVectorRepository descriptionVectorRepository;

    private Map<Anime, Map< String, Double>> corpus;
    private List<Anime> allAnimes;

    public void preProcess(boolean debug) {
        init();
        corpus = allAnimes.stream().collect(Collectors.toMap(anime -> anime, this::computeTermFrequency));
        int debugCount = 0;
        List<AnimeDescriptionVector> vectors = new ArrayList<>();

        for (int i = 0; i < allAnimes.size(); i++ ) {
            vectors.add(computeTextSimilarity(allAnimes, allAnimes.get(i)));
            if(debug){
                show(++debugCount + " / " + allAnimes.size());
            }

            if(i % 501 == 500){
                descriptionVectorRepository.batchUpdate(vectors);
                vectors.clear();
            }
        }

        descriptionVectorRepository.batchUpdate(vectors);
        vectors.clear();
    }

    //synchronized to keep memory requirements low, when multiple users connect
    public synchronized Map<Anime, Float> recommendToUser(User user){
        init();
        Map<Anime, Float> results = new HashMap<>();
        double average = Normalizer.calculateAverage(user.getAnimeEntries());
        List<AnimeEntry> favoriteAnimes = user.getAnimeEntries().stream()
                .filter(e -> e.getScore() >= average && e.getScore() > 0)
                .limit(Setup.maxAnimesForTextAnalyserRecommendations)
                .collect(Collectors.toList());

        if(favoriteAnimes.size() < Setup.maxAnimesForTextAnalyserRecommendations){
            favoriteAnimes.addAll(user.getAnimeEntries().stream()
                    .filter(e ->  e.getScore() == 0)
                    .limit(Setup.maxAnimesForTextAnalyserRecommendations - favoriteAnimes.size())
                    .collect(Collectors.toList())
            );
        }

        List<Float> resultSimilarityVector = Stream.generate( () -> (float) 0)
                .limit(allAnimes.size())
                .collect(Collectors.toList());

        Map<Long, AnimeDescriptionVector> vectors = descriptionVectorRepository.findDescriptionSimilarityVectors(favoriteAnimes);

        for (AnimeEntry animeEntry : favoriteAnimes) {
            List<Float> similarityVector = vectors.get(animeEntry.getMalId()).getDescriptionSimilarityVectorAsList();

            for(int i = 0; i < similarityVector.size(); i++) {
                Float similarity = similarityVector.get(i);
                resultSimilarityVector.set(i, resultSimilarityVector.get(i) + (similarity == null ? 0F : similarity));
            }
            //clear memory
            vectors.remove(animeEntry.getMalId());
        }

        for(int i = 0; i < resultSimilarityVector.size(); i++) {
            Anime iteratedAnime = allAnimes.get(i);
            if (!user.getAnimeEntries().contains(iteratedAnime)) {
                results.put(iteratedAnime, resultSimilarityVector.get(i));
            }
        }
        return results;
    }

    private AnimeDescriptionVector computeTextSimilarity(List<Anime> list, Anime anime) {
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
        List<Float> similarityVector = new ArrayList<>();
        for (Anime doc : list) {

            if(anime == doc){
                similarityVector.add(null);
                continue;
            }

            float valueForDoc = 0f;

            for (String keyword : animeKeywordsFrequency.keySet()) {
                valueForDoc += computeInverseDocumentFrequency(doc, keyword, keywordsOccurenceCountMap.get(keyword));
            }

            similarityVector.add(valueForDoc);
        }

        AnimeDescriptionVector result = new AnimeDescriptionVector(anime, similarityVector);
        return result;
    }

    private float computeInverseDocumentFrequency(Anime anime, String keyword, int numDocsContainingKeyword){
        if (corpus.get(anime).containsKey(keyword)) {
            return (float) ( corpus.get(anime).get(keyword) * Math.log(corpus.size() / (float) numDocsContainingKeyword));
        }
        return 0f;
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

    private void init(){
        allAnimes = dataStore.findAnimesForTextAnalysis();
    }
}
