package sample;

import java.io.*;
import java.util.*;


public class FileCounter{

    private Map<String, Integer> trainHamFreq;
    private Map<String, Integer> trainSpamFreq;
    private Integer numHamFiles;
    private Integer numSpamFiles;


    public FileCounter(){
        trainHamFreq = new TreeMap<>();
        trainSpamFreq = new TreeMap<>();
        this.numHamFiles = 0;
        this.numSpamFiles = 0;
    }


    public void parseFiles(File file, String trainMap){
        if(trainMap == "trainHamFreq"){
            try{
                goThroughWords(file, trainHamFreq);
            }catch(FileNotFoundException e){
                System.err.println("Invalid input directory, data folder not found");
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
        } else if (trainMap == "trainSpamFreq"){
            try{
                goThroughWords(file, trainSpamFreq);
            }catch(FileNotFoundException e){
                System.err.println("Invalid input directory, data folder not found");
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }


    private void goThroughWords(File file, Map<String,Integer> trainMap ) throws IOException {
        if(trainMap == trainHamFreq){
            this.numHamFiles = this.numHamFiles + 1;
        } else if (trainMap == trainSpamFreq){
            this.numSpamFiles = this.numSpamFiles + 1;
        }


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
                String key = wordIterator.next();
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

        System.out.println("Spam folders contents \n (word)+(how many files contain this word) ");
        Set<String> spamWords = trainSpamFreq.keySet();
        Iterator<String> spamIterator = spamWords.iterator();
        while(spamIterator.hasNext()) {
            String key = spamIterator.next();
            int spamCount = trainSpamFreq.get(key);
            System.out.println(key + ": " + spamCount);
        }


    }

    public Map<String, Integer> getTrainSpamFreq() { return this.trainSpamFreq; }
    public Map<String, Integer> getTrainHamFreq() { return this.trainHamFreq; }
    public Integer getNumHamFiles(){ return this.numHamFiles; }
    public Integer getNumSpamFiles(){ return this.numSpamFiles; }


    // For testing
    public void printTrainSpamFreq() {
        Set<String> keys = trainSpamFreq.keySet();
        Iterator<String> keyIterator = keys.iterator();
        System.out.println("TrainSpamFreq:");

        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            Integer freq = trainSpamFreq.get(key);
            System.out.println(key + ": " + freq);
        }
    }
    public void printTrainHamFreq() {
        Set<String> keys = trainHamFreq.keySet();
        Iterator<String> keyIterator = keys.iterator();
        System.out.println("TrainHamFreq:");

        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            Integer freq = trainHamFreq.get(key);
            System.out.println(key + ": " + freq);
        }
    }

}