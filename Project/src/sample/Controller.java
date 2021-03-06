package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
    public void initialize() {
        file.setCellValueFactory(new PropertyValueFactory<>("file"));
        actualClass.setCellValueFactory(new PropertyValueFactory<>("actualClass"));
        spamProbability.setCellValueFactory(new PropertyValueFactory<>("spamProbability"));
        tableView.setItems(DataSource.getAllTestFiles());
    }

    // must calculate accuracy and precision
}
