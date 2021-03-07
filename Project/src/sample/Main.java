package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.DirectoryChooser;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Spam Master 3000");
        primaryStage.setScene(new Scene(root, 700, 500));

        // Prompts user to choose folders
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File ham = directoryChooser.showDialog(primaryStage);
        File ham2 = directoryChooser.showDialog(primaryStage);
        File spam = directoryChooser.showDialog(primaryStage);

        // populate trainHamFreq and trainSpamFreq
        Training train = new Training();

        try {
            train.parseHamFiles(ham);
            train.parseHamFiles(ham2);
            train.parseSpamFiles(spam);
        } catch(IOException e){
            e.printStackTrace();
        }

        Map<String, Integer> trainSpamFreq = train.getTrainSpamFreq();
        Map<String, Integer> trainHamFreq = train.getTrainHamFreq();
//         for testing
//        train.printTrainHamFreq();
//        train.printTrainSpamFreq();


        Probabilites probs = new Probabilites();
        probs.popualteMaps(trainHamFreq,trainSpamFreq);
//         for testing
//        probs.printSpamProbMap();
//        probs.printWordInHamMap();
//        probs.printWordInSpamProbMap();
        
        Map<String, Double> spamProb = probs.getSpamProbMap();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
