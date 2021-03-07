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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        primaryStage.setTitle("Spam Master 3000");
        primaryStage.setScene(new Scene(root, 700, 500));

        // Prompts user to choose folders
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File trainDir = directoryChooser.showDialog(primaryStage);
        File testDir = directoryChooser.showDialog(primaryStage);

        // populate trainHamFreq and trainSpamFreq
        FileCounter train = new FileCounter();

        // Get the folders inside the folders
        File[] content = trainDir.listFiles();
        for (File current : content) {
            if(current.getName().substring(0,1) == "h" || current.getName().substring(0,1) == "H"){
                train.parseFiles(current, "trainHamFreq");
            } else if (current.getName().substring(0,1) == "s" || current.getName().substring(0,1) == "S"){
                train.parseFiles(current, "trainSpamFreq");
            }
        }


        Map<String, Integer> trainSpamFreq = train.getTrainSpamFreq();
        Map<String, Integer> trainHamFreq = train.getTrainHamFreq();
//         for testing training
//        train.printTrainHamFreq();
//        train.printTrainSpamFreq();


        Probabilites probs = new Probabilites();
        probs.popualteMaps(trainHamFreq,trainSpamFreq);
//         for testing
        probs.printSpamProbMap();
//        probs.printWordInHamMap();
//        probs.printWordInSpamProbMap();

        Map<String, Double> spamProb = probs.getSpamProbMap();

        // Testing
        controller.tableSetUp(testDir,spamProb);



        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
