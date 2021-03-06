package sample;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Probabilites {

    private Map<String, Double> wordInHam;
    private Map<String, Double> wordInSpam;
    private Map<String, Double> spamIfWord;
    private Integer filesInSpam;

    public Probabilites(Map<String, Integer> trainSpamFreq){
        wordInHam = new TreeMap<>();
        wordInSpam = new TreeMap<>();
        spamIfWord = new TreeMap<>();
        this.filesInSpam = trainSpamFreq.keySet().size();
    }

    public void popualteMaps(Map<String, Integer> trainHamFreq, Map<String, Integer> trainSpamFreq) {
        populateWordInHam(trainHamFreq);
        populateWordInSpam(trainSpamFreq);
        populateSpamProb();

    }

    private void populateWordInHam(Map<String, Integer> trainHamFreq){

        Set<String> keys = trainHamFreq.keySet();
        Iterator<String> keyIterator = keys.iterator();
        Integer filesInHam = trainHamFreq.keySet().size();

        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            Double numFiles = wordInHam.get(key);
            wordInHam.put(key,  (double)numFiles/filesInHam);
        }
    }

    private void populateWordInSpam(Map<String, Integer> trainSpamFreq){

        Set<String> keys = trainSpamFreq.keySet();
        Iterator<String> keyIterator = keys.iterator();
        Integer filesInHam = trainSpamFreq.keySet().size();

        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            Double numFiles = wordInSpam.get(key);
            wordInHam.put(key,  (double)numFiles/filesInSpam);
        }
    }

    private void populateSpamProb(){
        Set<String> keys = wordInSpam.keySet();
        Iterator<String> keyIterator = keys.iterator();

        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            Double wordInSpamProb = wordInSpam.get(key);
            Double wordInHamProb = wordInHam.get(key);
            Double spamProb = wordInSpamProb / (wordInSpamProb + wordInHamProb);
            spamIfWord.put(key, spamProb);
        }
    }

    private Map<String, Double> getSpamProbMap() { return this.spamIfWord; }

    // for testing purposes:

    private void printWordInHamMap() {
        Set<String> keys = wordInHam.keySet();
        Iterator<String> keyIterator = keys.iterator();
        System.out.println("Probabilities of words appearing in Ham files.");

        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            Double prob = wordInHam.get(key);
            System.out.println(key + ": " + prob);
        }
    }

    private void printWordInSpamProbMap() {
        Set<String> keys = wordInSpam.keySet();
        Iterator<String> keyIterator = keys.iterator();
        System.out.println("Probabilities of words appearing in Spam files.");

        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            Double prob = wordInSpam.get(key);
            System.out.println(key + ": " + prob);
        }
    }

    private void printSpamProbMap() {
        Set<String> keys = spamIfWord.keySet();
        Iterator<String> keyIterator = keys.iterator();
        System.out.println("Probabilities of a file being spam if it contains the word.");

        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            Double spamProb = spamIfWord.get(key);
            System.out.println(key + ": " + spamProb);
        }
    }
}
