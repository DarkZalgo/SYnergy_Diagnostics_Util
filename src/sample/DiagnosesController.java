package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DiagnosesController implements Initializable
{
    TimeClock SYnergy;
    Stage curStage;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
       /* Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("diagnosesWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        SYnergy = (TimeClock) root.getUserData();
        System.out.println(SYnergy.getInitialParts().getModel());
        curStage = SYnergy.getStage();*/

    }


    private void receiveData()
    {
        /*Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        SYnergy = (TimeClock) stage.getUserData();*/


    }
    @FXML
    private void sendToMain(ActionEvent event)
    {
        curStage.setUserData(SYnergy);
        curStage.close();
    }

    @FXML
    private void cancelToMain(ActionEvent event)
    {
        Node node = (Node) event.getSource();
        Stage curStage = (Stage) node.getScene().getWindow();
        curStage.close();
    }
}
