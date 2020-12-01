package diagnostics;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SolutionsController implements Initializable
{
    InitialPartsData currentParts;

    TimeClock SYnergy;

    SolutionData solutions;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
      SYnergy = Context.getInstance().currentClock();
      currentParts = SYnergy.getInitialParts();
    }
      
      private boolean isComplete()
    {
        boolean isComplete = true;
        String errorMsg = "";
        
        if (!isComplete)
        {
         Alert errorAlert = new Alert(Alert.AlertType.ERROR);

         errorAlert.setContentText(errorMsg);
         errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

         errorAlert.showAndWait();
        }
        return isComplete;
    }


    @FXML
    private void sendToMain(ActionEvent event)
    {
        if(isComplete())
        {
            setSolutions();
            Context.getInstance().currentClock().setSolutions(solutions);

            Node node = (Node) event.getSource();
            Stage curStage = (Stage) node.getScene().getWindow();
            curStage.close();
        }
    }

    @FXML
    private void cancelToMain(ActionEvent event)
    {
        Node node = (Node) event.getSource();
        Stage curStage = (Stage) node.getScene().getWindow();
        curStage.close();
    }

    private void setSolutions()
    {

    }
    
}
