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

    public Probabilites(){
        wordInHam = new TreeMap<>();
        wordInSpam = new TreeMap<>();
        spamIfWord = new TreeMap<>();
    }

    public void popualteMaps(Map<String, Integer> trainHamFreq, Integer numHamFiles, Map<String, Integer> trainSpamFreq, Integer numSpamFiles) {
        populateWordInHam(trainHamFreq, numHamFiles);
        populateWordInSpam(trainSpamFreq, numSpamFiles);
        populateSpamProb();

    }

    private void populateWordInHam(Map<String, Integer> trainHamFreq, Integer numHamFiles){

        Set<String> keys = trainHamFreq.keySet();
        Iterator<String> keyIterator = keys.iterator();

        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            Integer numFiles = trainHamFreq.get(key);
            if(trainHamFreq.get(key) != null) {
                wordInHam.put(key, (double) numFiles / numHamFiles);
            } else {
                wordInHam.put(key, (double) 0);
            }
        }
    }

    private void populateWordInSpam(Map<String, Integer> trainSpamFreq, Integer numSpamFiles){

        Set<String> keys = trainSpamFreq.keySet();
        Iterator<String> keyIterator = keys.iterator();

        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            Integer numFiles = trainSpamFreq.get(key);
            if(trainSpamFreq.get(key) != null) {
                wordInSpam.put(key,  (double)numFiles/numSpamFiles);
            } else {
                wordInSpam.put(key, (double) 0);
            }
        }
    }

    private void populateSpamProb(){
        Set<String> keys = wordInSpam.keySet();
        Iterator<String> keyIterator = keys.iterator();

        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            Double wordInSpamProb = wordInSpam.get(key);
            Double wordInHamProb = wordInHam.get(key);
            if(wordInSpam.get(key) != null && wordInHam.get(key) != null) {
                Double spamProb = wordInSpamProb / (wordInSpamProb + wordInHamProb);
                spamIfWord.put(key, spamProb);
            } else {
                spamIfWord.put(key, (double) 0);
            }
        }
    }

    public Map<String, Double> getSpamProbMap() { return this.spamIfWord; }

    // for testing purposes:

    public void printWordInHamMap() {
        Set<String> keys = wordInHam.keySet();
        Iterator<String> keyIterator = keys.iterator();
        System.out.println("Probabilities of words appearing in Ham files.");

        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            Double prob = wordInHam.get(key);
            System.out.println(key + ": " + prob);
        }
    }

    public void printWordInSpamProbMap() {
        Set<String> keys = wordInSpam.keySet();
        Iterator<String> keyIterator = keys.iterator();
        System.out.println("Probabilities of words appearing in Spam files.");

        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            Double prob = wordInSpam.get(key);
            System.out.println(key + ": " + prob);
        }
    }

    public void printSpamProbMap() {
        Set<String> keys = spamIfWord.keySet();
        Iterator<String> keyIterator = keys.iterator();
        System.out.println("Probabilities of a file being spam if it contains the word.");
        System.out.println(spamIfWord.keySet().size());

        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            Double spamProb = spamIfWord.get(key);
            System.out.println(key + ": " + spamProb);
        }
    }
}
