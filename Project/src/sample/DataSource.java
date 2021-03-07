package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.*;

public class DataSource {
    public static ObservableList<TestFile> getAllTestFiles(File testFilesDir, Map<String, Double> spamProb) throws IOException  {

        ObservableList<TestFile> testFiles = FXCollections.observableArrayList();

        File[] folders = testFilesDir.listFiles();
        for (File current : folders) {
            String actualClass = current.getName().substring(0,1).toUpperCase() + current.getName().substring(1);
            File[] files = current.listFiles();
            for (File currentFile : files){
                Scanner scanner = new Scanner(currentFile);
                Double n = 0.0;
                while (scanner.hasNext()) {
                    String token = scanner.next();
                    if (isValidWord(token)) {
                        String word = token.toLowerCase();
                        if (spamProb.containsKey(word)){
                            Double prob = spamProb.get(word);
                            n = n + (Math.log(1 - prob) - Math.log(prob));
                        }
                    }
                }
                Double fileSpamProb = 1/(1+ Math.pow(Math.E, n));
                testFiles.add(new TestFile(currentFile.getName(), fileSpamProb, actualClass));
            }
        }


        return testFiles;
    }

    private static boolean isValidWord(String word){
        String allLetters = "^[a-zA-Z]+$";
        // returns true if the word is composed by only letters otherwise returns false;
        return word.matches(allLetters);

    }

}
