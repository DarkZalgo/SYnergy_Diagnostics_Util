package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.*;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;


public class MainController implements Initializable
{
    @FXML TextField serialNumField, caseNumField, initialsField;
    @FXML TextField connectionField;
    @FXML TextField otherCardField;
    @FXML TextField macField, imageField, versionField;

    @FXML TextArea diagnosesArea, solutionsArea, partsReplacedArea;

    @FXML CheckBox wifiCommBox, gprsBox;

    @FXML Label connectionLabel;

    @FXML Button connectBtn, diagnosesBtn, solutionsBtn;

    @FXML RadioButton SY_2416, SY_A20, SY_X;
    @FXML RadioButton barcodeReader, hidReader, iClassReader, magneticReader, proximityReader, noneReader, mifareReader;
    @FXML RadioButton casFPU, suprFPU, noneFPU;
    @FXML RadioButton casThreeK, casTenK, suprNineK, suprTwentyFiveK;
    @FXML RadioButton serialRadio, sshRadio;
    @FXML RadioButton POEComm, ethComm;
    @FXML RadioButton sdBoard, threeWire, twoWire, unmodified, A20mBoard;
    @FXML RadioButton oneThreeA6H, oneThreeANon6H, oneThreeNon6H, oneTwo, A20cBoard;
    @FXML RadioButton delkinRadio, noneCardRadio, otherCardRadio;
    @FXML RadioButton blueBattery, silverBattery, noneBattery;
    @FXML RadioButton switchNoClasp, switchNewClasp, jumperNewClasp, jumperOldClasp;

    ToggleGroup modelGroup = new ToggleGroup();
    ToggleGroup readerGroup = new ToggleGroup();
    ToggleGroup fpuTypeGroup = new ToggleGroup();
    ToggleGroup fpuSizeGroup = new ToggleGroup();
    ToggleGroup connectionGroup = new ToggleGroup();
    ToggleGroup communicationGroup = new ToggleGroup();
    ToggleGroup coreboardGroup = new ToggleGroup();
    ToggleGroup motherboardGroup = new ToggleGroup();
    ToggleGroup sdCardGroup = new ToggleGroup();
    ToggleGroup batteryGroup = new ToggleGroup();
    ToggleGroup interfaceGroup = new ToggleGroup();

    ToggleData modelData = new ToggleData("Clock Model","",false,true);
    ToggleData readerData = new ToggleData("Reader Type","",false,true);
    ToggleData fpuTypeData = new ToggleData("FPU Type","",false,true);
    ToggleData fpuSizeData = new ToggleData("FPU Size","",false,false);
    ToggleData connectionData = new ToggleData("Connection Type","",false,false);
    ToggleData communicationData = new ToggleData("Communication Type","",false,true);
    ToggleData coreboardData = new ToggleData("Coreboard Type","",false,true);
    ToggleData motherboardData = new ToggleData("Motherboard Type","",false,true);
    ToggleData sdCardData = new ToggleData("SD Card Type","",false,true);
    ToggleData batteryData = new ToggleData("Battery Type","",false,true);
    ToggleData interfaceData = new ToggleData("Interface Board Type","",false,true);

    Set<RadioButton> modelSet;
    Set<RadioButton> readerSet;
    Set<RadioButton> fpuTypeSet;
    Set<RadioButton> fpuSizeSet;
    Set<RadioButton> connectionSet;
    Set<RadioButton> communicationSet;
    Set<RadioButton> coreboardSet;
    Set<RadioButton> motherboardSet;
    Set<RadioButton> sdCardSet;
    Set<RadioButton> batterySet;
    Set<RadioButton> interfaceBoardSet;

    Set<ToggleGroup> toggleGroupSet;

    Stage diagnosesStage;

    boolean completion;
    boolean darkLight = false;

    TimeClock SYnergy;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        modelSet = new HashSet<>(Arrays.asList(SY_2416, SY_A20, SY_X));
        readerSet = new HashSet<>(Arrays.asList(barcodeReader, hidReader, iClassReader, magneticReader, proximityReader, noneReader, mifareReader));
        fpuTypeSet = new HashSet<>(Arrays.asList(casFPU, suprFPU, noneFPU));
        fpuSizeSet = new HashSet<>(Arrays.asList(casThreeK, casTenK, suprNineK, suprTwentyFiveK));
        connectionSet = new HashSet<>(Arrays.asList(serialRadio, sshRadio));
        communicationSet = new HashSet<>(Arrays.asList(POEComm, ethComm));
        coreboardSet = new HashSet<>(Arrays.asList(sdBoard, threeWire, twoWire, unmodified, A20mBoard));
        motherboardSet = new HashSet<>(Arrays.asList(oneThreeA6H, oneThreeANon6H, oneThreeNon6H, oneTwo, A20cBoard));
        sdCardSet = new HashSet<>(Arrays.asList(delkinRadio, noneCardRadio, otherCardRadio));
        batterySet = new HashSet<>(Arrays.asList(blueBattery, silverBattery, noneBattery));
        interfaceBoardSet = new HashSet<>(Arrays.asList(switchNoClasp, switchNewClasp, jumperNewClasp, jumperOldClasp));

        toggleGroupSet = new HashSet<>(Arrays.asList(modelGroup, readerGroup, fpuTypeGroup, fpuSizeGroup,
                connectionGroup, communicationGroup, coreboardGroup, motherboardGroup, sdCardGroup,
                batteryGroup, interfaceGroup));

        modelGroup.setUserData(modelData);
        readerGroup.setUserData(readerData);
        fpuTypeGroup.setUserData(fpuTypeData);
        fpuSizeGroup.setUserData(fpuSizeData);
        connectionGroup.setUserData(connectionData);
        communicationGroup.setUserData(communicationData);
        coreboardGroup.setUserData(coreboardData);
        motherboardGroup.setUserData(motherboardData);
        sdCardGroup.setUserData(sdCardData);
        batteryGroup.setUserData(batteryData);
        interfaceGroup.setUserData(interfaceData);

        diagnosesArea.setEditable(false);
        solutionsArea.setEditable(false);
        partsReplacedArea.setEditable(false);


        connectionLabel.setVisible(false);

        for(RadioButton modelRadio : modelSet)
        {
            modelRadio.setToggleGroup(modelGroup);
        }

        for(RadioButton readerRadio : readerSet)
        {
            readerRadio.setToggleGroup(readerGroup);
        }

        for(RadioButton fpuTypeRadio : fpuTypeSet)
        {
            fpuTypeRadio.setToggleGroup(fpuTypeGroup);
        }

        for(RadioButton fpuSizeRadio : fpuSizeSet)
        {
            fpuSizeRadio.setToggleGroup(fpuSizeGroup);
            fpuSizeRadio.setVisible(false);
        }

        for(RadioButton connectionRadio : connectionSet)
        {
            connectionRadio.setToggleGroup(connectionGroup);
        }

        for(RadioButton commRadio : communicationSet)
        {
            commRadio.setToggleGroup(communicationGroup);
        }

        for(RadioButton cbRadio : coreboardSet)
        {
            cbRadio.setToggleGroup(coreboardGroup);
            cbRadio.setVisible(false);
        }

        for(RadioButton mbRadio : motherboardSet)
        {
            mbRadio.setToggleGroup(motherboardGroup);
            mbRadio.setVisible(false);
        }

        for(RadioButton sdRadio : sdCardSet)
        {
            sdRadio.setToggleGroup(sdCardGroup);
        }

        for(RadioButton batteryRadio : batterySet)
        {
            batteryRadio.setToggleGroup(batteryGroup);
        }

        for(RadioButton interfaceRadio : interfaceBoardSet)
        {
            interfaceRadio.setToggleGroup(interfaceGroup);
        }


        modelGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1)
            {
                if (modelGroup.getSelectedToggle()!= null)
                {
                    RadioButton tmpBtn = (RadioButton) modelGroup.getSelectedToggle();

                    modelData.setToggled(true);
                    modelData.setToggleData(tmpBtn.getText());

                    tmpBtn = null;
                }

                if (modelGroup.getSelectedToggle() == SY_A20 || modelGroup.getSelectedToggle() == SY_X)
                {
                    A20cBoard.setVisible(true);
                    A20mBoard.setVisible(true);

                    sdBoard.setVisible(false);
                    threeWire.setVisible(false);
                    twoWire.setVisible(false);
                    unmodified.setVisible(false);

                    oneThreeA6H.setVisible(false);
                    oneThreeANon6H.setVisible(false);
                    oneThreeNon6H.setVisible(false);
                    oneTwo.setVisible(false);

                    motherboardData.clear();
                }
                else if(modelGroup.getSelectedToggle() == SY_2416)
                {
                    A20cBoard.setVisible(false);
                    A20mBoard.setVisible(false);

                    sdBoard.setVisible(true);
                    threeWire.setVisible(true);
                    twoWire.setVisible(true);
                    unmodified.setVisible(true);

                    oneThreeA6H.setVisible(true);
                    oneThreeANon6H.setVisible(true);
                    oneThreeNon6H.setVisible(true);
                    oneTwo.setVisible(true);

                    motherboardData.clear();
                }
            }
        });

        readerGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1)
            {
                if (readerGroup.getSelectedToggle()!= null)
                {
                    RadioButton tmpBtn = (RadioButton) readerGroup.getSelectedToggle();

                    readerData.setToggled(true);
                    readerData.setToggleData(tmpBtn.getText());

                    tmpBtn = null;
                }

            }
        });

        fpuTypeGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1)
            {
                if (fpuTypeGroup.getSelectedToggle() != null)
                {
                    RadioButton tmpBtn = (RadioButton) fpuTypeGroup.getSelectedToggle();

                    fpuTypeData.setToggled(true);
                    fpuTypeData.setToggleData(tmpBtn.getText());

                    tmpBtn = null;
                }

               if (fpuTypeGroup.getSelectedToggle() == casFPU)
                {
                    casThreeK.setVisible(true);
                    casTenK.setVisible(true);

                    suprNineK.setVisible(false);
                    suprTwentyFiveK.setVisible(false);

                    fpuSizeData.clear();
                    fpuSizeData.setRequired(true);

                    deSelectRadioSet(fpuSizeSet);
                }
                else if(fpuTypeGroup.getSelectedToggle() == suprFPU)
                {
                    casThreeK.setVisible(false);
                    casTenK.setVisible(false);

                    suprNineK.setVisible(true);
                    suprTwentyFiveK.setVisible(true);

                    fpuSizeData.clear();
                    fpuSizeData.setRequired(true);

                    deSelectRadioSet(fpuSizeSet);
                }
                else if(fpuTypeGroup.getSelectedToggle() == noneFPU)
                {
                    deSelectRadioSet(fpuSizeSet,true);

                    casThreeK.setVisible(false);
                    casTenK.setVisible(false);

                    suprNineK.setVisible(false);
                    suprTwentyFiveK.setVisible(false);

                    fpuSizeData.clear();
                    fpuSizeData.setRequired(false);

                    deSelectRadioSet(fpuSizeSet);

                }
            }
        });

        fpuSizeGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1)
            {
                if (fpuSizeGroup.getSelectedToggle()!= null)
                {
                    RadioButton tmpBtn = (RadioButton) fpuSizeGroup.getSelectedToggle();

                    fpuSizeData.setToggled(true);
                    fpuSizeData.setToggleData(tmpBtn.getText());

                    tmpBtn = null;
                }
            }
        });

        connectionGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1)
            {

                if (connectionGroup.getSelectedToggle()!= null)
                {
                    RadioButton tmpBtn = (RadioButton) connectionGroup.getSelectedToggle();

                    connectionData.setToggled(true);

                    tmpBtn = null;
                }
                if (connectionGroup.getSelectedToggle() == sshRadio)
                {
                    connectionLabel.setText("Enter IP Address");
                    connectionLabel.setVisible(true);

                    connectionData.setToggleData("SSH");
                }
                else if(connectionGroup.getSelectedToggle() == serialRadio)
                {
                    connectionLabel.setText("Enter COM Port");
                    connectionLabel.setVisible(true);

                    connectionData.setToggleData("COM");
                }
            }
        });

        communicationGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1)
            {
                if (communicationGroup.getSelectedToggle()!= null)
                {
                    RadioButton tmpBtn = (RadioButton) communicationGroup.getSelectedToggle();

                    communicationData.setToggled(true);
                    communicationData.setToggleData(tmpBtn.getText());

                    tmpBtn = null;
                }
            }
        });

        coreboardGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1)
            {
                if (coreboardGroup.getSelectedToggle()!= null)
                {
                    RadioButton tmpBtn = (RadioButton) coreboardGroup.getSelectedToggle();

                    coreboardData.setToggled(true);
                    coreboardData.setToggleData(tmpBtn.getText());

                    tmpBtn = null;
                }
            }
        });

        motherboardGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1)
            {
                if (motherboardGroup.getSelectedToggle()!= null)
                {
                    RadioButton tmpBtn = (RadioButton) motherboardGroup.getSelectedToggle();

                    motherboardData.setToggled(true);
                    motherboardData.setToggleData(tmpBtn.getText());

                    tmpBtn = null;
                }
            }
        });

        sdCardGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1)
            {
                if (sdCardGroup.getSelectedToggle()!= null)
                {
                    RadioButton tmpBtn = (RadioButton) sdCardGroup.getSelectedToggle();

                    sdCardData.setToggled(true);
                    sdCardData.setToggleData(tmpBtn.getText());

                    tmpBtn = null;
                }
            }
        });

        batteryGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1)
            {
                if (batteryGroup.getSelectedToggle()!= null)
                {
                    RadioButton tmpBtn = (RadioButton) batteryGroup.getSelectedToggle();

                    batteryData.setToggled(true);
                    batteryData.setToggleData(tmpBtn.getText());

                    tmpBtn = null;
                }
            }
        });

        interfaceGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1)
            {
                if (interfaceGroup.getSelectedToggle()!= null)
                {
                    RadioButton tmpBtn = (RadioButton) interfaceGroup.getSelectedToggle();

                    interfaceData.setToggled(true);
                    interfaceData.setToggleData(tmpBtn.getText());

                    tmpBtn = null;
                }
            }
        });

        macField.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                if (macField.getText().length() > 17)
                {
                    s = macField.getText().substring(0, 17);
                    macField.setText(s);
                }
            }
        });

        gprsBox.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1)
            {
             if (gprsBox.isSelected())
             {
                 for(RadioButton interfaceBtn : interfaceBoardSet)
                 {

                     interfaceBtn.setSelected(false);
                     interfaceBtn.setDisable(true);
                 }
                 interfaceData.setToggled(true);
                 interfaceData.setToggleData("GPRS");
             }
             else if(!gprsBox.isSelected())
             {
                 for(RadioButton interfaceBtn : interfaceBoardSet)
                 {
                     interfaceBtn.setDisable(false);
                 }
                 interfaceData.clear();
             }
            }
        });

    }

    @FXML
    private void refreshAll()
    {
        deSelectRadioSet(modelSet);
        deSelectRadioSet(readerSet);
        deSelectRadioSet(fpuTypeSet);
        deSelectRadioSet(fpuSizeSet, false);
        deSelectRadioSet(connectionSet);
        deSelectRadioSet(communicationSet);
        deSelectRadioSet(motherboardSet, false);
        deSelectRadioSet(coreboardSet, false);
        deSelectRadioSet(sdCardSet);
        deSelectRadioSet(batterySet);
        deSelectRadioSet(interfaceBoardSet);

        caseNumField.setText("");
        serialNumField.setText("");
        initialsField.setText("");

        connectionField.setText("");

        otherCardField.setText("");

        macField.setText("");
        imageField.setText("");
        versionField.setText("");

        connectionLabel.setVisible(false);

        wifiCommBox.setSelected(false);
        gprsBox.setSelected(false);

        modelData.clear();
        readerData.clear();
        fpuTypeData.clear();
        fpuSizeData.clear();
        connectionData.clear();
        communicationData.clear();
        coreboardData.clear();
        motherboardData.clear();
        sdCardData.clear();
        batteryData.clear();
        interfaceData.clear();
        SYnergy.clear();
    }

    @FXML
    private boolean isComplete()
    {
        boolean completion = true;
        ToggleData tmpData;
        int count = 0;
        String errorMsg = "The following sections are not completed\n";

        for(ToggleGroup group : toggleGroupSet)
        {
            tmpData = (ToggleData) group.getUserData();

            if (tmpData.isRequired() && !tmpData.isToggled())
            {
                count++;

                errorMsg += tmpData.getGroupName()+"\n";
            }
        }

        if(count > 0)
        {
            completion = false;
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);

            errorAlert.setContentText(errorMsg);
            errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

            errorAlert.showAndWait();
        }

        return completion;
    }

    @FXML
    private void addDiagnoses(ActionEvent event)
    {
        completion = isComplete();
        if (completion)
        {
            if (gprsBox.isSelected())
            {
                communicationData.setToggleData(communicationData.getToggleData() + "/GPRS");
            }
            if (wifiCommBox.isSelected())
            {
                communicationData.setToggleData(communicationData.getToggleData() + "/WIFI");
            }

            InitialPartsData synergyParts = new InitialPartsData(
                    modelData.getToggleData(),
                    readerData.getToggleData(),
                    fpuTypeData.getToggleData(),
                    fpuSizeData.getToggleData(),
                    communicationData.getToggleData(),
                    coreboardData.getToggleData(),
                    motherboardData.getToggleData(),
                    sdCardData.getToggleData(),
                    batteryData.getToggleData(),
                    interfaceData.getToggleData(),
                    macField.getText(),
                    imageField.getText(),
                    versionField.getText());



            Node node = (Node) event.getSource();


            try {
                diagnosesStage = new Stage();
                SYnergy = Context.getInstance().currentClock();
                SYnergy.setInitialParts(synergyParts);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("diagnosesWindow.fxml"));
                Parent root = loader.load();

                DiagnosesController diagnosesController = loader.getController();

                root.setStyle(node.getScene().getRoot().getStyle());
                diagnosesStage.setTitle("Diagnoses");
                diagnosesStage.setScene(new Scene(root, 600, 400));
                diagnosesStage.initModality(Modality.WINDOW_MODAL);
                diagnosesStage.initStyle(StageStyle.UNDECORATED);


                Window primaryWindow = node.getScene().getWindow();

                diagnosesStage.initOwner(primaryWindow);

                diagnosesStage.setX(primaryWindow.getX() + 200);
                diagnosesStage.setY(primaryWindow.getY() + 100);

                diagnosesStage.setResizable(false);

                diagnosesStage.show();
                diagnosesStage.setOnHiding(new EventHandler<WindowEvent>()
                {
                    @Override
                    public void handle(WindowEvent windowEvent)
                    {
                        diagnosesArea.clear();
                        String issues = "";
                        for(String s : SYnergy.getDiagnoses().getImageIssuesList())
                        {
                            issues += s + "\n";
                        }
                        for(String s : SYnergy.getDiagnoses().getFunctionsList())
                        {
                            issues += s + "\n";
                        }
                        for(String s : SYnergy.getDiagnoses().getMiscList())
                        {
                            issues += s + "\n";
                        }
                        for(String s : SYnergy.getDiagnoses().getOtherIssuesList())
                        {
                            issues += s + "\n";
                        }
                        diagnosesArea.appendText(issues);
                    }
                });
            } catch (IOException exception)
            {
                exception.printStackTrace();
            }
        }
    }

    private void deSelectRadioSet(Set<RadioButton> radioSet, boolean isVisibile)
    {
        if (!isVisibile)
        {
            for(RadioButton tmpBtn : radioSet)
            {
                tmpBtn.setSelected(false);
                tmpBtn.setVisible(false);
            }
        }
        else
            {
            for (RadioButton tmpBtn : radioSet)
            {
                tmpBtn.setSelected(false);
            }
        }
    }

    private void deSelectRadioSet(Set<RadioButton> radioSet)
    {
        for (RadioButton tmpBtn : radioSet)
        {
            tmpBtn.setSelected(false);
        }
    }

    @FXML
    private void darkMode(ActionEvent event)
    {
        Node node = (Node)event.getSource();
        if (!darkLight)
        {

            node.getScene().getRoot().setStyle("-fx-base:black");
            darkLight = true;
        }
        else if(darkLight)
        {
            node.getScene().getRoot().setStyle("");
            darkLight = false;
        }

    }
}
