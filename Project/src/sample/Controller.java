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
        // populate trainHamFreq and trainSpamFreq
        FileCounter train = new FileCounter();

        // Get the folders inside the folders
        File[] content = trainDir.listFiles();
        for (File current : content) {
            String fileName = current.getName().substring(0,3);
            if(fileName.equals("ham") || fileName.equals("Ham")){
                train.parseFiles(current, "trainHamFreq");
            } else if (fileName.equals("spa") || fileName.equals("Spa")){
                train.parseFiles(current, "trainSpamFreq");
            }
        }

        Map<String, Integer> trainSpamFreq = train.getTrainSpamFreq();
        Map<String, Integer> trainHamFreq = train.getTrainHamFreq();
        Integer numHamFiles = train.getNumHamFiles();
        Integer numSpamFiles = train.getNumSpamFiles();
//         for testing
//        train.printTrainHamFreq();
//        train.printTrainSpamFreq();


        Probabilites probs = new Probabilites();
        probs.popualteMaps(trainHamFreq,numHamFiles,trainSpamFreq, numSpamFiles);
//         for testing
//        probs.printSpamProbMap();


        Map<String, Double> spamProb = probs.getSpamProbMap();

        file.setCellValueFactory(new PropertyValueFactory<>("filename"));
        actualClass.setCellValueFactory(new PropertyValueFactory<>("actualClass"));
        spamProbability.setCellValueFactory(new PropertyValueFactory<>("spamProbability"));
        try {
            tableView.setItems(DataSource.getAllTestFiles(testDir, spamProb));
        } catch(IOException e){
            e.printStackTrace();
        }

        // must calculate accuracy and precision
    }


}
