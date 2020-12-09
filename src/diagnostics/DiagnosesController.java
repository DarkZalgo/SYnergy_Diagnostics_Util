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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DiagnosesController implements Initializable
{
    @FXML CheckBox powersOnBox, ledsOnBox, fpuOnBox, blackScreenBox, partialLoadBox, fullLoadBox, loopBox, noProblemsBox;
    @FXML CheckBox readerFunctionBox, fpuFunctionBox, keypadFunctionBox;
    @FXML CheckBox fadedLCDBox, brokenLCDBox, missingUSBCoverBox, backCaseBox, frontCaseBox, sidePanelBox, keyCapsDamagedBox, windowDamagedBox, keypadIncorrectVersionBox;
    @FXML CheckBox noSDReadBox, error117Box, incorrectVersBox, corruptedImageBox;
    @FXML CheckBox badCoreboardBox, badMotherboardBox, badPOEBox, badKeypadBoardBox, badInterfaceRibbonBox,badMotherboardRibbonBox, badInterfaceBoardBox, badBatteryBox;
    @FXML CheckBox outdatedBatteryBox, outdatedInterfaceBoardBox;
    @FXML CheckBox other1Box, other2Box, other3Box, other4Box;

    @FXML TextField other1Field, other2Field, other3Field, other4Field;

    InitialPartsData currentParts;

    TimeClock SYnergy;

    private static final Logger logger = LoggerFactory.getLogger(DiagnosesController.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        SYnergy = Context.getInstance().currentClock();
        DiagnosticData diagnoses = SYnergy.getDiagnoses();
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

    private DiagnosticData createDiagnoses()
    {
        ArrayList<String> turnsOnList = new ArrayList<>();
        ArrayList<String> functionsList = new ArrayList<>();
        ArrayList<String> miscList = new ArrayList<>();
        ArrayList<String> imageIssuesList = new ArrayList<>();
        ArrayList<String> otherIssuesList = new ArrayList<>();
        ArrayList<String> badPartsList = new ArrayList<>();

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
            turnsOnList.add("fully loads " + currentParts.getVersion() + " " + currentParts.getImage() + " image");

        if (readerFunctionBox.isVisible() && !readerFunctionBox.isSelected())
            functionsList.add(currentParts.getReader() + " reader functions\n");
        else if (readerFunctionBox.isVisible() && readerFunctionBox.isSelected())
            functionsList.add(currentParts.getReader() + " reader does not function\n");
        if (fpuFunctionBox.isVisible() && !fpuFunctionBox.isSelected())
            functionsList.add(currentParts.getFpuType() + " " + currentParts.getFpuSize() + " FPU reader functions\n");
        else if(fpuFunctionBox.isVisible() && fpuFunctionBox.isSelected())
            functionsList.add(currentParts.getFpuType() + " " + currentParts.getFpuSize() + " FPU reader does not function\n");
        if (keypadFunctionBox.isSelected())
            functionsList.add("Keypad does not function\n");
        else functionsList.add("Keypad functions\n");
        if (noProblemsBox.isSelected())
            functionsList.add("Unable to replicate problem; clock appears fully functional");

        if (fadedLCDBox.isSelected())
            miscList.add("Screen is faded\n");
        if (brokenLCDBox.isSelected())
            miscList.add("Screen is broken\n");
        if (missingUSBCoverBox.isSelected())
            miscList.add("Missing Rubber USB Cover\n");
        if (frontCaseBox.isSelected())
            miscList.add("Front case is scratched/damaged\n");
        if (backCaseBox.isSelected())
            miscList.add("Back case is scratched/damaged\n");
        if (sidePanelBox.isSelected())
            miscList.add("Side panel is scratched/damaged\n");
        if (keyCapsDamagedBox.isSelected())
            miscList.add("Keycaps are damaged\n");
        if (windowDamagedBox.isSelected())
            miscList.add("LCD Window is scratched/damaged");
        if (keypadIncorrectVersionBox.isSelected())
            miscList.add(SYnergy.getInitialParts().getReader() + " keypad board is incorrect version");

        if (noSDReadBox.isSelected())
            imageIssuesList.add("No SD card for clock to read from\n");
        if (error117Box.isSelected())
            imageIssuesList.add("Clock cannot read from a block in memory, Error: -117\n");
        if (incorrectVersBox.isSelected())
            imageIssuesList.add("Clock has incorrect version loaded (Version " + currentParts.getVersion() + ")\n");
        if (corruptedImageBox.isSelected())
            imageIssuesList.add("Image is corrupted\n");

        if(badCoreboardBox.isSelected())
            badPartsList.add("Bad "+ SYnergy.getInitialParts().getCoreboard() + " coreboard");
        if(badMotherboardBox.isSelected())
            badPartsList.add("Bad " + SYnergy.getInitialParts().getMotherboard() + " motherboard");
        if (badPOEBox.isSelected())
            badPartsList.add("Bad POE module");
        if (badKeypadBoardBox.isSelected())
            badPartsList.add("Bad Keypad Board");
        if (badInterfaceRibbonBox.isSelected())
            badPartsList.add("Bad motherboard to interface board ribbon cable");
        if (badMotherboardRibbonBox.isSelected())
            badPartsList.add("Bad motherboard to keypad board ribbon cable");
        if (badInterfaceBoardBox.isSelected())
            badPartsList.add("Bad " + SYnergy.getInitialParts().getInterfaceBoard() + " interface board");
        if (badBatteryBox.isSelected() && !outdatedBatteryBox.isSelected())
            badPartsList.add("Bad battery backup");
        if (outdatedBatteryBox.isSelected())
            badPartsList.add("Silver battery backup is outdated");
        if (outdatedInterfaceBoardBox.isSelected())
            badPartsList.add("Jumper Old Clasp interface board is outdated");

        if (other1Box.isSelected())
            otherIssuesList.add(other1Field.getText() + "\n");
        if (other2Box.isSelected())
            otherIssuesList.add(other2Field.getText() + "\n");
        if (other3Box.isSelected())
            otherIssuesList.add(other3Field.getText() + "\n");
        if (other4Box.isSelected())
            otherIssuesList.add(other4Field.getText() + "\n");

        return new DiagnosticData(turnsOnList, functionsList, miscList, imageIssuesList, badPartsList, otherIssuesList);
    }

    @FXML
    private void sendToMain(ActionEvent event)
    {
        if(isOtherComplete())
        {

            Context.getInstance().currentClock().setDiagnoses(createDiagnoses());

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
