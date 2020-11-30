package diagnostics;

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

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.docx4j.Docx4J;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.text.DateFormatter;

public class MainController implements Initializable
{
    @FXML TextField serialNumField, caseNumField, initialsField;
    @FXML TextField connectionField;
    @FXML TextField otherCardField;
    @FXML TextField reportedIssueField, macField, imageField, versionField, custNameField;
    @FXML TextField qtyOneField, qtyTwoField;

    @FXML TextArea diagnosesArea, solutionsArea, partsReplacedArea;

    @FXML CheckBox wifiCommBox, gprsBox;

    @FXML Label connectionLabel, qtyOfLabel;

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

    @FXML DatePicker recvDatePicker;

    @FXML TabPane rmaPane;

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

    ToggleData modelData = new ToggleData("Clock Model","N/A",false,true);
    ToggleData readerData = new ToggleData("Reader Type","N/A",false,true);
    ToggleData fpuTypeData = new ToggleData("FPU Type","N/A",false,true);
    ToggleData fpuSizeData = new ToggleData("FPU Size","N/A",false,false);
    ToggleData connectionData = new ToggleData("Connection Type","N/A",false,false);
    ToggleData communicationData = new ToggleData("Communication Type","N/A",false,true);
    ToggleData coreboardData = new ToggleData("Coreboard Type","N/A",false,true);
    ToggleData motherboardData = new ToggleData("Motherboard Type","N/A",false,true);
    ToggleData sdCardData = new ToggleData("SD Card Type","N/A",false,true);
    ToggleData batteryData = new ToggleData("Battery Type","N/A",false,true);
    ToggleData interfaceData = new ToggleData("Interface Board Type","N/A",false,true);

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

    Stage diagnosesStage, fileOpenStage;

    boolean completion;
    boolean darkLight = false;

    TimeClock SYnergy;

    Logger logger = LoggerFactory.getLogger("Log");

    FileHandler handler = new FileHandler();

    SimpleDateFormat simpleFormat = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        modelSet = new HashSet<>(Arrays.asList(SY_2416, SY_A20, SY_X));
        readerSet = new HashSet<>(Arrays.asList(barcodeReader, hidReader, iClassReader, magneticReader, proximityReader, noneReader, mifareReader));
        fpuTypeSet = new HashSet<>(Arrays.asList(casFPU, suprFPU, noneFPU));
        fpuSizeSet = new HashSet<>(Arrays.asList(casThreeK, casTenK, suprNineK, suprTwentyFiveK));
        connectionSet = new HashSet<>(Arrays.asList(serialRadio, sshRadio));
        communicationSet = new HashSet<>(Arrays.asList(POEComm, ethComm));
        motherboardSet = new HashSet<>(Arrays.asList(sdBoard, threeWire, twoWire, unmodified, A20mBoard));
        coreboardSet = new HashSet<>(Arrays.asList(oneThreeA6H, oneThreeANon6H, oneThreeNon6H, oneTwo, A20cBoard));
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

        rmaPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        connectionLabel.setVisible(false);
       // qtyOfLabel.setVisible(false);
        //qtyTwoField.setVisible(false);

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

        serialNumField.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                if (!t1.matches("\\d*"))
                {
                    serialNumField.setText(t1.replaceAll("[^\\d]", ""));
                }
            }
        });

        qtyOneField.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                if (!t1.matches("\\d*"))
                {
                    qtyOneField.setText(t1.replaceAll("[^\\d]", ""));
                }

            }
        });

        qtyTwoField.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                if (!t1.matches("\\d*"))
                {
                    qtyTwoField.setText(t1.replaceAll("[^\\d]", ""));
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

        reportedIssueField.setText("");
        macField.setText("");
        imageField.setText("");
        versionField.setText("");
        qtyOneField.setText("");
        qtyTwoField.setText("");
        custNameField.setText("");

        diagnosesArea.setText("");
        solutionsArea.setText("");
        partsReplacedArea.setText("");

        connectionLabel.setVisible(false);

        wifiCommBox.setSelected(false);
        gprsBox.setSelected(false);

        recvDatePicker.setValue(null);

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
        try
        {
        SYnergy.clear();
        }
        catch(NullPointerException ignored)
        {
        }
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
        if (recvDatePicker.getValue() == null)
        {
            count++;
            errorMsg+="Receive Date Field\n";
        }
        if (!(imageField.getText().length() > 0))
        {
            count++;
            errorMsg+="Image Field\n";
        }
        if (!(versionField.getText().length() > 0))
        {
            count++;
            errorMsg+="Version Field\n";
        }
        if (!(custNameField.getText().length() > 0))
        {
            count++;
            errorMsg+="Customer Name Field\n";
        }
        if (!(caseNumField.getText().length() > 0))
        {
            count++;
            errorMsg+="Case Number Field\n";
        }
        if (!(serialNumField.getText().length() > 0))
        {
            count++;
            errorMsg+="Serial Number Field\n";
        }
        if (!(initialsField.getText().length() > 0))
        {
            count++;
            errorMsg+="Initials Field\n";
        }
        if (!(qtyOneField.getText().length() > 0))
        {
            count++;
            errorMsg+="Quantity Field";
        }
        if((qtyOneField.getText().length()  > 0 && qtyTwoField.getText().length() > 0) && Integer.parseInt(qtyOneField.getText()) > Integer.parseInt(qtyTwoField.getText()))
        {
            count++;
            errorMsg+="First quantity field is larger second quantity field";
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
                        if (SYnergy.getDiagnoses() !=null)
                        {
                            diagnosesArea.clear();
                            String issues = "";
                            for (String s : SYnergy.getDiagnoses().getImageIssuesList()) {
                                issues += s + "\n";
                            }
                            for (String s : SYnergy.getDiagnoses().getFunctionsList()) {
                                issues += s + "\n";
                            }
                            for (String s : SYnergy.getDiagnoses().getMiscList()) {
                                issues += s + "\n";
                            }
                            for (String s : SYnergy.getDiagnoses().getOtherIssuesList()) {
                                issues += s + "\n";
                            }
                            diagnosesArea.appendText(issues);
                        }
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
    private void writeToFile(ActionEvent event) throws Exception
    {
        String outputFile ="RMA CAS " + caseNumField.getText() + ".docx";
        String firmwareVers = "N/A";
        String netBoard = "N/A";
        String lithium = "N/A";
        String quantity = "";
        SYnergy = Context.getInstance().currentClock();
        CaseData caseData = new CaseData(
                reportedIssueField.getText(),
                new Date(),
                recvDatePicker.getValue(),
                custNameField.getText().toUpperCase(),
                caseNumField.getText(),
                serialNumField.getText(),
                initialsField.getText(),
                qtyOneField.getText(),
                qtyTwoField.getText());
        SYnergy.setCaseData(caseData);

        StringBuilder builder = new StringBuilder();
        builder.append(String.join(" ", SYnergy.getDiagnoses().getImageIssuesList()));
        builder.append(String.join(" ", SYnergy.getDiagnoses().getFunctionsList()));
        builder.append(String.join(" ", SYnergy.getDiagnoses().getOtherIssuesList()));
        builder.append(String.join(" ", SYnergy.getDiagnoses().getMiscList()));

        File rmaDir = new File("./RMAs");
        if (!rmaDir.exists())
        {
            rmaDir.mkdir();
        }

        if (SYnergy.getCaseData().getQtyTwo().length() > 0 && Integer.parseInt(SYnergy.getCaseData().getQtyTwo()) > 1)
        {
            quantity = " " + SYnergy.getCaseData().getQtyOne() + " of " + SYnergy.getCaseData().getQtyTwo();
        }
        else
        {
             quantity = SYnergy.getCaseData().getQtyOne();
        }
        String dataOutputFileName = new SimpleDateFormat("yyyy-dd-MM").format(new Date()) + "_" + custNameField.getText() + "_CAS-" + caseNumField.getText() + quantity;


        System.out.println(SYnergy.getCaseData().getStartDate());



        Node node = (Node) event.getSource();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(outputFile);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Word Document Files (*.docx)","*.docx");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(rmaDir);
        WordprocessingMLPackage wordMLPackage = Docx4J.load(handler.getResourceAsFile("resources/template.docx"));

        HashMap wordMappings = new HashMap();
        VariablePrepare.prepare(wordMLPackage);
        wordMappings.put("casenum", SYnergy.getCaseData().getCaseNum());
        wordMappings.put("qty", quantity);
        wordMappings.put("recvdate", SYnergy.getCaseData().getReceiveDate());
        wordMappings.put("startdate", SYnergy.getCaseData().getStartDate());
        wordMappings.put("model", SYnergy.getInitialParts().getModel() + " " + SYnergy.getInitialParts().getFpuSize()
                + "/" +SYnergy.getInitialParts().getReader() + "/" + SYnergy.getInitialParts().getCommunication());
        wordMappings.put("serialnum", SYnergy.getCaseData().getSerialNum());
        wordMappings.put("firmwarevers",firmwareVers);
        wordMappings.put("reportedproblem", reportedIssueField.getText());
        wordMappings.put("netboard", netBoard);
        wordMappings.put("lithium", lithium);
        wordMappings.put("battery", SYnergy.getInitialParts().getBattery());
        wordMappings.put("coreboard", SYnergy.getInitialParts().getCoreboard());
        wordMappings.put("sdcard", SYnergy.getInitialParts().getSdCard());
        wordMappings.put("motherboard", SYnergy.getInitialParts().getMotherboard());
        wordMappings.put("interfaceboard", SYnergy.getInitialParts().getInterfaceBoard());
        wordMappings.put("firstboot", String.join(" ",SYnergy.getDiagnoses().getTurnsOnList()));
        wordMappings.put("diagnoses", builder);
        wordMappings.put("solutions", "solutions");
        wordMappings.put("partsreplaced", "parts replaced");
        wordMappings.put("initials", SYnergy.getCaseData().getInitials());

        wordMLPackage.getMainDocumentPart().variableReplace(wordMappings);

        File rmaNotes = fileChooser.showSaveDialog(node.getScene().getWindow());
        logger.info("Starting save operation");
        Docx4J.save(wordMLPackage, rmaNotes, Docx4J.FLAG_NONE);
        logger.info("Successfully saved "+outputFile + " at "+rmaNotes.getPath());


        handler.saveSYObject(SYnergy, dataOutputFileName);
    }

    @FXML
    private void readFromFile(ActionEvent event)
    {
        Node node = (Node) event.getSource();


        try {
            fileOpenStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fileOpenerWindow.fxml"));
            Parent root = loader.load();

            root.setStyle(node.getScene().getRoot().getStyle());
            fileOpenStage.setTitle("Open");
            fileOpenStage.setScene(new Scene(root, 780, 400));
            fileOpenStage.initModality(Modality.WINDOW_MODAL);
            //fileOpenStage.initStyle(StageStyle.UNDECORATED);


            Window primaryWindow = node.getScene().getWindow();

            fileOpenStage.initOwner(primaryWindow);

            fileOpenStage.setX(primaryWindow.getX() + 200);
            fileOpenStage.setY(primaryWindow.getY() + 100);

            fileOpenStage.setResizable(false);

            fileOpenStage.show();
            fileOpenStage.setOnHiding(new EventHandler<WindowEvent>()
            {
                @Override
                public void handle(WindowEvent windowEvent)
                {
                    wifiCommBox.setSelected(false);
                    gprsBox.setSelected(false);

                    SYnergy = Context.getInstance().currentClock();
                    if (SYnergy.getInitialParts() == null || SYnergy.getDiagnoses() == null)
                    {
                        return;
                    }
                    setClockData(SYnergy.getInitialParts().getModel(), modelSet, modelGroup);
                    setClockData(SYnergy.getInitialParts().getReader(), readerSet, readerGroup);
                    setClockData(SYnergy.getInitialParts().getFpuType(), fpuTypeSet, fpuTypeGroup);
                    setClockData(SYnergy.getInitialParts().getFpuSize(), fpuSizeSet, fpuSizeGroup);
                    setClockData(SYnergy.getInitialParts().getCommunication(), communicationSet, communicationGroup);

                    String tempStr =  SYnergy.getInitialParts().getCommunication();
                    if (tempStr.toUpperCase().contains("WIFI"))
                    {
                        wifiCommBox.setSelected(true);
                    }
                    if (tempStr.toUpperCase().contains("GPRS"))
                    {
                        gprsBox.setSelected(true);
                    }

                    if (SYnergy.getInitialParts().getInterfaceBoard() != null)
                    {
                        setClockData(SYnergy.getInitialParts().getInterfaceBoard(),interfaceBoardSet,interfaceGroup);
                    }

                    setClockData(SYnergy.getInitialParts().getMotherboard(), motherboardSet, motherboardGroup);
                    setClockData(SYnergy.getInitialParts().getCoreboard(), coreboardSet, coreboardGroup);
                    setClockData(SYnergy.getInitialParts().getSdCard(), sdCardSet, sdCardGroup);
                    setClockData(SYnergy.getInitialParts().getBattery(), batterySet, batteryGroup);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

                    reportedIssueField.setText(SYnergy.getCaseData().getProblem());
                    recvDatePicker.setValue(SYnergy.getCaseData().getReceiveLocalDate());
                    macField.setText(SYnergy.getInitialParts().getMac());
                    imageField.setText(SYnergy.getInitialParts().getImage());
                    versionField.setText(SYnergy.getInitialParts().getVersion());
                    custNameField.setText(SYnergy.getCaseData().getCustomerName());
                    caseNumField.setText(SYnergy.getCaseData().getCaseNum());
                    serialNumField.setText(SYnergy.getCaseData().getSerialNum());
                    initialsField.setText(SYnergy.getCaseData().getInitials());
                    qtyOneField.setText(SYnergy.getCaseData().getQtyOne());
                    qtyTwoField.setText(SYnergy.getCaseData().getQtyTwo());

                }
            });
        } catch (IOException exception)
        {
            exception.printStackTrace();
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

    private void setClockData(String data, Set<RadioButton> set, ToggleGroup group )
    {
        for (RadioButton tempBtn : set)
        {
            if (tempBtn.getText().equals(data))
            {
                group.selectToggle(tempBtn);
            }
        }
    }


}
