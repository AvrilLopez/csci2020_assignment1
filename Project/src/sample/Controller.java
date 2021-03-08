package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.*;

import java.text.DecimalFormat;
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
    private TextField txtAccuracy;
    @FXML
    private TextField txtPrecision;

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
            ObservableList<TestFile> testFiles = DataSource.getAllTestFiles(testDir, spamProb);
            tableView.setItems(testFiles);

            Integer numTruePositives = 0;
            Integer numTrueNegatives = 0;
            int numFalsePositives=0;
            int numFiles=0;
            // must calculate accuracy and precision
            for (TestFile file : testFiles){

                // in here
                // Do whatever with each file
                // getSpamProbCalc() gets the calculated probability in type double
                if ((file.getSpamProbCalc()>=0.5)&&(file.getActualClass().equals("Spam"))){
                    numTruePositives++;
                }
                if ((file.getSpamProbCalc()<0.5)&&(file.getActualClass().equals("Ham"))){
                    numTrueNegatives++;
                }
                if ((file.getSpamProbCalc()>=0.5)&&(file.getActualClass().equals("Ham"))){
                    numFalsePositives++;
                }
                numFiles++;

            }


            double accuracy=(double)(numTruePositives+numTrueNegatives)/numFiles;
            System.out.println(accuracy);
            double precision=(double)numTruePositives/(numFalsePositives+numTruePositives);
            DecimalFormat formatter = new DecimalFormat("0.00000");
            
            txtAccuracy.setText(String.valueOf(formatter.format(accuracy)));
            txtPrecision.setText(String.valueOf(formatter.format(precision)));


        } catch(IOException e){
            e.printStackTrace();
        }



    }


}
