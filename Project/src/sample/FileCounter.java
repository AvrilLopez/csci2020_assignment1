package sample;

import java.io.*;
import java.util.*;


public class FileCounter{

    private Map<String, Integer> trainHamFreq;
    private Map<String, Integer> trainSpamFreq;


    public FileCounter(){
        trainHamFreq = new TreeMap<>();
        trainSpamFreq = new TreeMap<>();
    }


    public void goThroughWords(File file, Map<String,Integer> trainMap ) throws IOException {

        if (file.isDirectory()) {
            //parse each file inside the directory
            File[] content = file.listFiles();
            for (File current : content) {
                goThroughWords(current, trainMap);
            }

        } else {
            Scanner scanner = new Scanner(file);

            // creating a map of words in the current file
            Map<String, Integer> wordsInCurrentFile = new TreeMap<>();

            //scanning current file word by word
            while (scanner.hasNext()) {
                String token = scanner.next();
                if (isValidWord(token)) {
                    token=token.toLowerCase();
                    if (wordsInCurrentFile.containsKey(token)) {
                        int previous = wordsInCurrentFile.get(token);
                        wordsInCurrentFile.put(token, previous + 1);
                    } else {
                        wordsInCurrentFile.put(token, 1);
                    }
                }
            }
            //filling out our frequency map
            Set<String> words = wordsInCurrentFile.keySet();
            Iterator<String> wordIterator = words.iterator();
            while (wordIterator.hasNext()) {
                String key =wordIterator.next();
                countFiles(key, trainMap);
            }

        }
    }




    private boolean isValidWord(String word){
        String allLetters = "^[a-zA-Z]+$";
        // returns true if the word is composed by only letters otherwise returns false;
        return word.matches(allLetters);

    }

    private void countFiles(String word, Map<String,Integer> trainMap){
        if(trainMap.containsKey(word)){
            int previous = trainMap.get(word);
            trainMap.put(word, previous+1);
        }else{
            trainMap.put(word, 1);
        }
    }



    public void outputWordCount(){
        //System.out.println("Saving word counts to file:" + output.getAbsolutePath());
        //System.out.println("Total words:" + wordCounts.keySet().size());

        /*System.out.println("Ham folder contents \n (word)+(how many files contain this word) ");
        Set<String> hamWords = trainHamFreq.keySet();
        Iterator<String> hamIterator = hamWords.iterator();
        while(hamIterator.hasNext()) {
            String key = hamIterator.next();
            int hamCount = trainHamFreq.get(key);
            System.out.println(key + ": " + hamCount);
        }*/

        System.out.println("Spam folders contents \n (word)+(how many files contain this word) ");
        Set<String> spamWords = trainSpamFreq.keySet();
        Iterator<String> spamIterator = spamWords.iterator();
        while(spamIterator.hasNext()) {
            String key = spamIterator.next();
            int spamCount = trainSpamFreq.get(key);
            System.out.println(key + ": " + spamCount);
        }


    }

    //main method
    public static void main(String[] args) {

        FileCounter filesThatContainAWord = new FileCounter();
        System.out.println("Hello");
        try{

            filesThatContainAWord.goThroughWords(new File("../../data/train/spam"), filesThatContainAWord.trainSpamFreq);
            filesThatContainAWord.goThroughWords(new File("../../data/train/ham"), filesThatContainAWord.trainHamFreq);
            filesThatContainAWord.goThroughWords(new File("../../data/train/ham2"), filesThatContainAWord.trainHamFreq);

            filesThatContainAWord.outputWordCount();

        }catch(FileNotFoundException e){
            System.err.println("Invalid input directory, data folder not found");
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }


    }

}