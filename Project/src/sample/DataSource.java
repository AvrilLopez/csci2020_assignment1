package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.*;

public class DataSource {
    public static ObservableList<TestFile> getAllTestFiles() {
        ObservableList<TestFile> testFiles = FXCollections.observableArrayList();

        // here, populate the testFiles list with the information on all the files
        // testFiles.add(new TestFile("", , ));


        return testFiles;
    }

}
