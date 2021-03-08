package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.*;

import java.util.Map;

public class Controller {
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn file;
    @FXML
    private TableColumn actualClass;
    @FXML
    private TableColumn spamProbability;
    @FXML
    private TextField accuracy;
    @FXML
    private TextField precision;

    private TableView<TestFile> testFiles;


    @FXML
    public void initialize(File trainDir, File testDir) {
        // initialize the file counter for the training
        FileCounter train = new FileCounter();

        // Get the folders inside the folders
        File[] content = trainDir.listFiles();
        for (File current : content) {
            String fileName = current.getName().substring(0,3);
            // populate trainHamFreq and trainSpamFreq
            if(fileName.equals("ham") || fileName.equals("Ham")){
                train.parseFiles(current, "trainHamFreq");
            } else if (fileName.equals("spa") || fileName.equals("Spa")){
                train.parseFiles(current, "trainSpamFreq");
            }
        }

        // get the frequency maps
        Map<String, Integer> trainSpamFreq = train.getTrainSpamFreq();
        Map<String, Integer> trainHamFreq = train.getTrainHamFreq();
        // get the number of Ham and Spam files
        Integer numHamFiles = train.getNumHamFiles();
        Integer numSpamFiles = train.getNumSpamFiles();
//         for testing
//        train.printTrainHamFreq();
//        train.printTrainSpamFreq();


        Probabilites probs = new Probabilites();
        // populate the different probabilities maps
        probs.popualteMaps(trainHamFreq,numHamFiles,trainSpamFreq, numSpamFiles);
//         for testing
//        probs.printSpamProbMap();

        // get the probability of a file being spam given it contains a word map
        Map<String, Double> spamProb = probs.getSpamProbMap();


        file.setCellValueFactory(new PropertyValueFactory<>("filename"));
        actualClass.setCellValueFactory(new PropertyValueFactory<>("actualClass"));
        spamProbability.setCellValueFactory(new PropertyValueFactory<>("spamProbability"));
        try {
            // go through the test files, calculate the probability and populate the table
            tableView.setItems(DataSource.getAllTestFiles(testDir, spamProb));
        } catch(IOException e){
            e.printStackTrace();
        }

        // must calculate accuracy and precision
    }


}
