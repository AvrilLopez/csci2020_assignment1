package sample;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Probabilites class to calcuate all the probabilities linked to the training.
 *
 * The overall layout and iteration structure through the files was taken from
 * the module 5 examples provided
 */
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

    /**
     * Function to call outside the class to populate all probability maps
     *
     * @param trainHamFreq Map of words and number of Ham files it appears in
     * @param numHamFiles number of Ham files
     * @param trainSpamFreq Map of words and number of Spam files it appears in
     * @param numSpamFiles number of Spam files
     */
    public void popualteMaps(Map<String, Integer> trainHamFreq, Integer numHamFiles, Map<String, Integer> trainSpamFreq, Integer numSpamFiles) {
        populateWordInHam(trainHamFreq, numHamFiles);
        populateWordInSpam(trainSpamFreq, numSpamFiles);
        populateSpamProb();

    }

    /**
     * Function to populate the probability the word appears in a Ham file map
     *
     * @param trainHamFreq Map of words and number of Ham files it appears in
     * @param numHamFiles number of Ham files
     */
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

    /**
     * Function to populate the probability the word appears in a Spam file map
     *
     * @param trainSpamFreq Map of words and number of Spam files it appears in
     * @param numSpamFiles number of Spam files
     */
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

    /**
     * Function to populate the probability that a file is Spam given
     * it contains a word map
     */
    private void populateSpamProb(){
        Set<String> keys = wordInSpam.keySet();
        Iterator<String> keyIterator = keys.iterator();

        // iterate over the probability the word appears in a Spam file map
        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            Double wordInSpamProb = wordInSpam.get(key);
            Double wordInHamProb = wordInHam.get(key);
            // calculate the probability
            if(wordInSpam.get(key) != null && wordInHam.get(key) != null) {
                Double spamProb = wordInSpamProb / (wordInSpamProb + wordInHamProb);
                spamIfWord.put(key, spamProb);
            } else if(wordInSpam.get(key) != null && wordInHam.get(key) == null) {
                spamIfWord.put(key, (double) 1);
            } else {
                spamIfWord.put(key, (double) 0);
            }
        }
    }

    // getter function
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
