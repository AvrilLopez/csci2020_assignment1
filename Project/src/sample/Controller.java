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

//    private File testFilesDir;
//    private Map<String, Double> spamProb;

    @FXML
    public void initialize() {

    }

    @FXML
    public void tableSetUp(File testFilesDir, Map<String, Double> spamProb){
        file.setCellValueFactory(new PropertyValueFactory<>("file"));
        actualClass.setCellValueFactory(new PropertyValueFactory<>("actualClass"));
        spamProbability.setCellValueFactory(new PropertyValueFactory<>("spamProbability"));
        tableView.setItems(DataSource.getAllTestFiles(testFilesDir, spamProb));
    }



    // must calculate accuracy and precision
}
