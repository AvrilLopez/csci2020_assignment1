package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.*;

public class DataSource {
    /**
     * Function to populate the testFiles list to read from in the controller
     *
     * @param testFilesDir Directory where test files are stored
     * @param spamProb  probability of a file being spam given it contains a word map
     */
    public static ObservableList<TestFile> getAllTestFiles(File testFilesDir, Map<String, Double> spamProb) throws IOException  {

        // list where test files will be read from
        ObservableList<TestFile> testFiles = FXCollections.observableArrayList();

        // for each folder in the test directory
        File[] folders = testFilesDir.listFiles();
        for (File current : folders) {
            // get the name of the folder to be the actual class of the file
            String actualClass = current.getName().substring(0,1).toUpperCase() + current.getName().substring(1);
            // for each file in the folder
            File[] files = current.listFiles();
            for (File currentFile : files){
                Scanner scanner = new Scanner(currentFile);
                Double n = 0.0; // initialize n
                while (scanner.hasNext()) {
                    String token = scanner.next();
                    if (isValidWord(token)) {   // if the word is valid
                        String word = token.toLowerCase();
                        if (spamProb.containsKey(word)){
                            Double prob = spamProb.get(word); // get the probability
                            n = n + (Math.log(1 - prob) - Math.log(prob)); // update n
                        }
                    }
                }
                Double fileSpamProb = 1/(1+ Math.pow(Math.E, n)); // calculate spam probability
                testFiles.add(new TestFile(currentFile.getName(), fileSpamProb, actualClass)); // add file to the list
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
