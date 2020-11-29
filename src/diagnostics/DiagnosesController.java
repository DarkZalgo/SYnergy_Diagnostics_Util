package diagnostics;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DiagnosesController implements Initializable
{
    @FXML CheckBox powersOnBox, ledsOnBox, fpuOnBox, blackScreenBox, partialLoadBox, fullLoadBox, loopBox;
    @FXML CheckBox readerFunctionBox, fpuFunctionBox, keypadFunctionBox;
    @FXML CheckBox fadedLCDBox, brokenLCDBox, missingUSBCoverBox, backCaseBox, frontCaseBox, sidePanelBox;
    @FXML CheckBox noSDReadBox, error117Box, incorrectVersBox, corruptedImageBox;
    @FXML CheckBox other1Box, other2Box, other3Box, other4Box;

    @FXML TextField other1Field, other2Field, other3Field, other4Field;

    InitialPartsData currentParts;

    TimeClock SYnergy;

    DiagnosticData diagnoses;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        SYnergy = Context.getInstance().currentClock();
        currentParts = SYnergy.getInitialParts();

        other1Field.setVisible(false);
        other2Field.setVisible(false);
        other3Field.setVisible(false);
        other4Field.setVisible(false);

        other2Box.setVisible(false);
        other3Box.setVisible(false);
        other4Box.setVisible(false);

        if (currentParts.getReader().equals("None"))
        readerFunctionBox.setVisible(false);

        if (currentParts.getFpuType().equals("None"))
        fpuFunctionBox.setVisible(false);

        other1Box.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                if (other1Box.isSelected())
                {
                    other1Field.setVisible(true);
                    other2Box.setVisible(true);
                }
            }
        });

        other2Box.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                other2Field.setVisible(true);
                other3Box.setVisible(true);
            }
        });

        other3Box.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                other3Field.setVisible(true);
                other4Box.setVisible(true);
            }
        });

        other4Box.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
                other4Field.setVisible(true);
            }
        });
    }

    private boolean isOtherComplete()
    {
        boolean isOtherComplete = true;
        String errorMsg = "";
        System.out.println(other1Field.getText().length());
        if(other1Box.isSelected() && other1Field.getText().length() == 0)
        {
            isOtherComplete = false;
            errorMsg += "The first \'Other\' box is selected but the field has nothing in it.\n";
        }
        if(other2Box.isSelected() && other2Field.getText().length() == 0)
        {
            isOtherComplete = false;
            errorMsg += "The second \'Other\' box is selected but the field has nothing in it.\n";
        }
        if(other3Box.isSelected() && other3Field.getText().length() == 0)
        {
            isOtherComplete = false;
            errorMsg += "The third \'Other\' box is selected but the field has nothing in it.\n";
        }
        if(other4Box.isSelected() && other4Field.getText().length() == 0)
        {
            isOtherComplete = false;
            errorMsg += "The fourth \'Other\' box is selected but the field has nothing in it.\n";
        }
        if (!isOtherComplete)
        {
         Alert errorAlert = new Alert(Alert.AlertType.ERROR);

         errorAlert.setContentText(errorMsg);
         errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

         errorAlert.showAndWait();
        }
        return isOtherComplete;
    }

    private void setDiagnoses()
    {
        ArrayList<String> turnsOnList = new ArrayList<>();
        ArrayList<String> functionsList = new ArrayList<>();
        ArrayList<String> miscList = new ArrayList<>();
        ArrayList<String> imageIssuesList = new ArrayList<>();
        ArrayList<String> otherIssuesList = new ArrayList<>();

        if(!powersOnBox.isSelected())
            turnsOnList.add("Clock powers on,");
        if (!ledsOnBox.isSelected() && !fpuOnBox.isSelected())
            turnsOnList.add("all lights turn on,");
        else if (!ledsOnBox.isSelected() && fpuOnBox.isSelected())
            turnsOnList.add("all lights turn on except FPU light");
        if (!loopBox.isSelected() && partialLoadBox.isSelected())
            turnsOnList.add("partially loads image.");
        if (loopBox.isSelected())
            turnsOnList.add("clock partially loads and starts bootlooping.");
        if (blackScreenBox.isSelected())
            turnsOnList.add("goes to black screen.");
        if (fullLoadBox.isSelected())
            turnsOnList.add("Fully loads " + currentParts.getVersion() + " " + currentParts.getImage());

        if (readerFunctionBox.isVisible() && !readerFunctionBox.isSelected())
            functionsList.add(currentParts.getReader() + " functions\n");
        else if (readerFunctionBox.isVisible() && readerFunctionBox.isSelected())
            functionsList.add(currentParts.getReader() + " reader does not function\n");
        if (fpuFunctionBox.isVisible() && !fpuFunctionBox.isSelected())
            functionsList.add(currentParts.getFpuType() + " " + currentParts.getFpuSize() + " FPU functions\n");
        else if(fpuFunctionBox.isVisible() && fpuFunctionBox.isSelected())
            functionsList.add(currentParts.getFpuType() + " " + currentParts.getFpuSize() + " FPU reader does not function\n");
        if (keypadFunctionBox.isSelected())
            functionsList.add("Keypad functions\n");
        else functionsList.add("Keypad does not function\n");

        if (fadedLCDBox.isSelected())
            miscList.add("Screen is faded\n");
        if (brokenLCDBox.isSelected())
            miscList.add("Screen is broken\n");
        if (missingUSBCoverBox.isSelected())
            miscList.add("Missing Rubber USB Cover\n");
        if (frontCaseBox.isSelected())
            miscList.add("Front case is scratched\n");
        if (backCaseBox.isSelected())
            miscList.add("Back case is scratched\n");
        if (sidePanelBox.isSelected())
            miscList.add("Side panel is scratched\n");

        if (noSDReadBox.isSelected())
            imageIssuesList.add("No SD card for clock to read from\n");
        if (error117Box.isSelected())
            imageIssuesList.add("Clock cannot read from a block in memory, Error: -117\n");
        if (incorrectVersBox.isSelected())
            imageIssuesList.add("Clock has incorrect version loaded (Version " + currentParts.getVersion() + ")\n");
        if (corruptedImageBox.isSelected())
            imageIssuesList.add("Image is corrupted\n");

        if (other1Box.isSelected())
            otherIssuesList.add(other1Field.getText());
        if (other2Box.isSelected())
            otherIssuesList.add(other2Field.getText());
        if (other3Box.isSelected())
            otherIssuesList.add(other3Field.getText());
        if (other4Box.isSelected())
            otherIssuesList.add(other4Field.getText());

        diagnoses = new DiagnosticData(turnsOnList, functionsList, miscList, imageIssuesList, otherIssuesList);
    }

    @FXML
    private void sendToMain(ActionEvent event)
    {
        if(isOtherComplete())
        {
            setDiagnoses();
            Context.getInstance().currentClock().setDiagnoses(diagnoses);

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
}
