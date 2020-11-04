package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DiagnosesController implements Initializable
{
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    @FXML
    private void receiveData(ActionEvent event)
    {
        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        Data data = (Data) stage.getUserData();
        System.out.println(data.getStuff());

       //1 User user = (User) stage.getUserData();
    }
}
