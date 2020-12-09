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

public class SolutionsController implements Initializable
{
    @FXML CheckBox reimagedGivenSDBox, reflashedKeypadBox, newImagedBox, reflashedCoreboardBox;
    @FXML CheckBox replacedCoreboardBox, replacedMotherboardBox, replacedPOEBox, replacedKeypadBoardBox, replacedFPUBoardBox, replacedInterfaceBoardBox, replacedBatteryBackupBox, replacedFPUSensorBox, replacedLCDBox, replacedCardReaderBox;
    @FXML CheckBox replacedFrontCaseBox, replacedBackCaseBox, replacedSidePanelBox, replacedKeycapsBox, replacedUSBCoverBox, replacedLCDWindowBox;
    @FXML CheckBox replacedInterfaceRibbonBox, replacedMotherboardRibbonBox;
    @FXML CheckBox other1Box, other2Box, other3Box, other4Box;

    @FXML TextField givenSDImageField, sdTypeField, newSDImageField;
    @FXML TextField other1Field, other2Field, other3Field, other4Field;


    InitialPartsData currentParts;

    TimeClock SYnergy;

    private static final Logger logger = LoggerFactory.getLogger(SolutionsController.class);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
      SYnergy = Context.getInstance().currentClock();

      currentParts = SYnergy.getInitialParts();
      SolutionData solutions = SYnergy.getSolutions();

      other1Field.setVisible(false);
      other2Field.setVisible(false);
      other3Field.setVisible(false);
      other4Field.setVisible(false);

      other2Box.setVisible(false);
      other3Box.setVisible(false);
      other4Box.setVisible(false);


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

        if (other1Box.isSelected() && other1Field.getText().length() == 0)
        {
            isOtherComplete = false;
            errorMsg += "The first \'Other\' box is selected but the field has nothing in it.\n";
        }
        if (other2Box.isSelected() && other2Field.getText().length() == 0)
        {
            isOtherComplete = false;
            errorMsg += "The second \'Other\' box is selected but the field has nothing in it.\n";
        }
        if (other3Box.isSelected() && other3Field.getText().length() == 0)
        {
            isOtherComplete = false;
            errorMsg += "The third \'Other\' box is selected but the field has nothing in it.\n";
        }
        if (other4Box.isSelected() && other4Field.getText().length() == 0)
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


    @FXML
    private void sendToMain(ActionEvent event)
    {
        if (isOtherComplete())
        {
            Context.getInstance().currentClock().setSolutions(createSolutions());

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

    private SolutionData createSolutions()
    {
        ArrayList<String> imageSolutionsList = new ArrayList<>();
        ArrayList<String> replacedHardwareList = new ArrayList<>();
        ArrayList<String> replacedCosmeticsList = new ArrayList<>();
        ArrayList<String> otherIssuesList = new ArrayList<>();

        if (reimagedGivenSDBox.isSelected() && givenSDImageField.getText().length() > 0)
            imageSolutionsList.add("Reimaged given SD card to " + givenSDImageField.getText() + " image and installed to clock\n");
        if (newImagedBox.isSelected() && sdTypeField.getText().length() > 0 && newSDImageField.getText().length() > 0)
            imageSolutionsList.add("Imaged new " + sdTypeField.getText() + " SD card to " + newSDImageField.getText() + " image and installed to clock\n");
        if (reflashedKeypadBox.isSelected())
            imageSolutionsList.add("Reflashed " + SYnergy.getInitialParts().getReader() + " keypad board to version 11\n");
        if (reflashedCoreboardBox.isSelected())
            imageSolutionsList.add("Reflashed Coreboard\n");

        if (replacedCoreboardBox.isSelected())
            replacedHardwareList.add("Replaced coreboard\n");
        if (replacedMotherboardBox.isSelected())
            replacedHardwareList.add("Replaced motherboard\n");
        if (replacedLCDBox.isSelected())
            replacedHardwareList.add("Replaced LCD\n");
        if (replacedPOEBox.isSelected())
            replacedHardwareList.add("Replaced POE Module\n");
        if (replacedKeypadBoardBox.isSelected())
            replacedHardwareList.add("Replaced " + currentParts.getReader() + " keypad board\n");
        if (replacedFPUBoardBox.isSelected())
            replacedHardwareList.add("Replaced " + currentParts.getFpuType() + " " + currentParts.getFpuSize() + " FPU board\n");
        if(replacedInterfaceBoardBox.isSelected())
            replacedHardwareList.add("Replaced " + currentParts.getInterfaceBoard() + "\n");
        if (replacedBatteryBackupBox.isSelected())
            replacedHardwareList.add("Replaced battery backup\n");
        if (replacedFPUSensorBox.isSelected())
            replacedHardwareList.add("Replaced " + currentParts.getFpuType() + " " + currentParts.getFpuSize() + " FPU Sensor\n");
        if (replacedCardReaderBox.isSelected())
            replacedHardwareList.add("Replaced " + currentParts.getReader() + " reader\n");
        if (replacedInterfaceRibbonBox.isSelected())
            replacedHardwareList.add("Replaced motherboard to interface board ribbon cable\n");
        if (replacedMotherboardRibbonBox.isSelected())
            replacedHardwareList.add("Replaced motherboard to keypad board ribbon cable\n");

        if (replacedFrontCaseBox.isSelected())
            replacedCosmeticsList.add("Replaced front case\n");
        if (replacedBackCaseBox.isSelected())
            replacedCosmeticsList.add("Replaced back case\n");
        if (replacedSidePanelBox.isSelected())
            replacedCosmeticsList.add("Replaced side panel\n");
        if (replacedKeycapsBox.isSelected())
            replacedCosmeticsList.add("Replaced keycaps\n");
        if (replacedUSBCoverBox.isSelected())
            replacedCosmeticsList.add("Replaced Rubber USB Cover\n");
        if (replacedLCDWindowBox.isSelected())
            replacedCosmeticsList.add("Replaced LCD window\n");

        if (other1Box.isSelected())
            otherIssuesList.add(other1Field.getText() + "\n");
        if (other2Box.isSelected())
            otherIssuesList.add(other2Field.getText() + "\n");
        if (other3Box.isSelected())
            otherIssuesList.add(other3Field.getText() + "\n");
        if (other4Box.isSelected())
            otherIssuesList.add(other4Field.getText() + "\n");

        return new SolutionData(imageSolutionsList, replacedHardwareList, replacedCosmeticsList, otherIssuesList);
    }
    
}
