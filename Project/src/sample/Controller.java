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
            if(current.getName() == "ham" || current.getName() == "ham2"){
                train.parseFiles(current, "trainHamFreq");
            } else if (current.getName() == "spam" || current.getName() == "Spam"){
                train.parseFiles(current, "trainSpamFreq");
            }
        }


        Map<String, Integer> trainSpamFreq = train.getTrainSpamFreq();
        Map<String, Integer> trainHamFreq = train.getTrainHamFreq();
//         for testing training
        train.printTrainHamFreq();
        train.printTrainSpamFreq();


        Probabilites probs = new Probabilites();
        probs.popualteMaps(trainHamFreq,trainSpamFreq);
//         for testing
//        probs.printSpamProbMap();
//        probs.printWordInHamMap();
//        probs.printWordInSpamProbMap();

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
