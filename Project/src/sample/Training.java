package sample;

import java.io.*;
import java.util.*;

public class Training {
    private Map<String, Integer> trainSpamFreq;
    private Map<String, Integer> trainHamFreq;

    public Training(){
        this.trainSpamFreq = new TreeMap<>();
        this.trainHamFreq = new TreeMap<>();
    }

    public void parseSpamFiles(File file) throws IOException{
//        System.out.println("Starting parsing the file:" + file.getAbsolutePath());

        if(file.isDirectory()){
            //parse each file inside the directory
            File[] content = file.listFiles();
            for(File current: content){
                parseSpamFiles(current);
            }
        }else{
            Scanner scanner = new Scanner(file);
            Map<String, Integer> words = new TreeMap<>();;
            // scanning token by token
            while (scanner.hasNext()){
                String  token = scanner.next();
                if (isValidWord(token)){
                    if(words.containsKey(token)){
                        int previous = words.get(token);
                        words.put(token, previous+1);
                    }else{
                        words.put(token, 1);
                    }
                }
            }

            Set<String> keys = words.keySet();
            Iterator<String> keyIterator = keys.iterator();

            while(keyIterator.hasNext()){
                String key = keyIterator.next();
                // adding the counts to the freq map
                if(trainSpamFreq.get(key) != null){
                    int reps = trainSpamFreq.get(key);
                    trainSpamFreq.put(key, reps+1);
                } else {
                    trainSpamFreq.put(key, 1);
                }
            }
        }

    }

    public void parseHamFiles(File file) throws IOException{
//        System.out.println("Starting parsing the file:" + file.getAbsolutePath());

        if(file.isDirectory()){
            //parse each file inside the directory
            File[] content = file.listFiles();
            for(File current: content){
                parseHamFiles(current);
            }
        }else{
            Scanner scanner = new Scanner(file);
            Map<String, Integer> words = new TreeMap<>();;
            // scanning token by token
            while (scanner.hasNext()){
                String  token = scanner.next();
                if (isValidWord(token)){
                    if(words.containsKey(token)){
                        int previous = words.get(token);
                        words.put(token, previous+1);
                    }else{
                        words.put(token, 1);
                    }
                }
            }

            Set<String> keys = words.keySet();
            Iterator<String> keyIterator = keys.iterator();

            while(keyIterator.hasNext()){
                String key = keyIterator.next();

                // adding the counts to the freq map
                if(trainHamFreq.get(key) != null){
                    int reps = trainHamFreq.get(key);
                    trainHamFreq.put(key, reps+1);
                } else {
                    trainHamFreq.put(key, 1);
                }
            }
        }

    }

    private boolean isValidWord(String word){
        String allLetters = "^[a-zA-Z]+$";
        // returns true if the word is composed by only letters otherwise returns false;
        return word.matches(allLetters);

    }

    public Map<String, Integer> getTrainSpamFreq() { return this.trainSpamFreq; }
    public Map<String, Integer> getTrainHamFreq() { return this.trainHamFreq; }

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
