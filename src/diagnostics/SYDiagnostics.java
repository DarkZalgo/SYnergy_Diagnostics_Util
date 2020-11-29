package diagnostics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SYDiagnostics extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("SYnergy Diagnostics");
        primaryStage.setScene(new Scene(root, 850, 620));
        primaryStage.setResizable(false);
        primaryStage.show();



    }


    public static void main(String[] args) {
        launch(args);
    }
}
