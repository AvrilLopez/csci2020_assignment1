package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.*;

public class DataSource {
    public static ObservableList<TestFile> getAllTestFiles(File testFilesDir, Map<String, Double> spamProb) {

        ObservableList<TestFile> testFiles = FXCollections.observableArrayList();

        File[] content1 = testFilesDir.listFiles();
        for (File current : content1) {
            if(current.getName().substring(0,1) == "h" || current.getName().substring(0,1) == "H"){
                try {
                    parseFiles(current, "Ham", testFiles, spamProb);
                } catch(FileNotFoundException e){
                    System.err.println("Invalid input directory, data folder not found");
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }
            } else if (current.getName().substring(0,1) == "s" || current.getName().substring(0,1) == "S"){
                try {
                    parseFiles(current, "Spam", testFiles,spamProb);
                } catch(FileNotFoundException e){
                    System.err.println("Invalid input directory, data folder not found");
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

        return testFiles;
    }

    private static void parseFiles(File file, String actualClass, ObservableList<TestFile> testFiles, Map<String, Double> spamProb) throws IOException {
        if (file.isDirectory()) {
            //parse each file inside the directory
            File[] content = file.listFiles();
            for (File current : content) {
                parseFiles(current, actualClass, testFiles, spamProb);
            }

        } else {
            Scanner scanner = new Scanner(file);
            Double n = 0.0;
            //scanning current file word by word
            while (scanner.hasNext()) {
                String token = scanner.next();
                if (isValidWord(token)) {
                    token=token.toLowerCase();
                    if (spamProb.containsKey(token)) {
                        n = n + (Math.log(1 - spamProb.get(token)) - Math.log(spamProb.get(token)));
                    } else {
                        n = n + 0;
                    }
                }
            }

            Double fileSpamProb = 1/(1+ Math.pow(Math.E, n));
            testFiles.add(new TestFile(file.getName(), fileSpamProb, actualClass));

        }
    }

    private static boolean isValidWord(String word){
        String allLetters = "^[a-zA-Z]+$";
        // returns true if the word is composed by only letters otherwise returns false;
        return word.matches(allLetters);

    }

}
