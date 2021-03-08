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
        Scene scene = new Scene(root, 700, 500);
//        scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
        primaryStage.setScene(scene);

        // Prompts user to choose folders
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        directoryChooser.setTitle("Select training folder");
        File trainDir = directoryChooser.showDialog(primaryStage);
        directoryChooser.setTitle("Select testing folder");
        File testDir = directoryChooser.showDialog(primaryStage);

        // pass the directories chosen into the controller
        controller.initialize(trainDir, testDir);



        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
