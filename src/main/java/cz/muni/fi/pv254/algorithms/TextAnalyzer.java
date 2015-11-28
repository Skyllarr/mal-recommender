package cz.muni.fi.pv254.algorithms;

import com.mysema.commons.lang.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Created by suomiy on 11/27/15.
 */
public class TextAnalyzer {

    private Map<Integer, Pair<String, Map< String, Double>>> corpus;
    private int id = 0;

    public TextAnalyzer(List<String> corpus){
        setCorpus(corpus);
    }

    public Map<Integer, Pair<String, Map<String, Double>>> getCorpus() {
        return corpus;
    }

    public void setCorpus(List<String> corpus) {
        this.id = 0;
        this.corpus = corpus.stream().collect(Collectors.toMap(doc ->  id++, this::computeTermFreuqency));
    }

    public List<Double> computeTfIdfSimilarity(int positionInCorpus){
        Pair<String, Map< String, Double>> document = corpus.get(positionInCorpus);
        List<Double> result = new ArrayList<>(corpus.size());

        corpus.forEach( (key, doc) -> {
            Double similarity = null;

            if(key != positionInCorpus){

            }

            result.add(similarity);

        });
        return result;
    }

    private Double computeInverseDocumentFrequency(String keyword ){
       /* Double termFrequency = document.get(keyword);
        Double idf = Math.log(corpus.size()/); */
        Double result = 0D;

        return result;
    }

    private Pair<String, Map< String, Double>> computeTermFreuqency(String document){
        Double maxFreq = 0D;
        Map<String, Double> corpus = new TreeMap<>();

        for(String word : document.split("\\W")){
            if("".equals(word)){
                continue;
            }

            if(corpus.containsKey(word)){
                Double count = corpus.get(word) + 1;
                if(count > maxFreq){
                    maxFreq = count;
                }
                corpus.replace(word, count);
            }else{
                corpus.put(word,1D);
            }
        }
        if(maxFreq != 0){//optimization against long docs - augmented frequency
            final Double finalMaxFreq = maxFreq;
            corpus.replaceAll((k, v) ->v / finalMaxFreq);
        }

        return new Pair<>(document, corpus);
    }
}
