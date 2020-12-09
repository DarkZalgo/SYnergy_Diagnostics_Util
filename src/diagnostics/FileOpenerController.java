package diagnostics;

import com.fazecast.jSerialComm.SerialPort;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.ResourceBundle;

public class FileOpenerController implements Initializable
{
    @FXML TableView rmaTableView;

    @FXML TableColumn dateColumn, caseNumColumn, customerNameColumn, serialNumColumn, clockTypeColumn;

    TimeClock clock;

    private static final Logger logger = LoggerFactory.getLogger(FileOpenerController.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try {
            readClocks();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (SerialPort port : SerialPort.getCommPorts())
        System.out.println(port);
    }


    @FXML
    public void refreshTable(ActionEvent event) throws IOException, ClassNotFoundException
    {
        readClocks();
    }

    @FXML
    public void getSelectedClock(ActionEvent event)
    {
        TimeClockStringProperties clockProperties = (TimeClockStringProperties) rmaTableView.getSelectionModel().getSelectedItem();
        if(clockProperties != null)
        {
            clock = clockProperties.getClock();
            Context.getInstance().setClock(clock);
        }

        Node node = (Node) event.getSource();
        Stage curStage = (Stage) node.getScene().getWindow();
        curStage.close();
    }
    @FXML public void getNoClock(ActionEvent event)
    {
        Node node = (Node) event.getSource();
        Stage curStage = (Stage) node.getScene().getWindow();
        curStage.close();
    }

    public void readClocks() throws IOException, ClassNotFoundException
    {
        File dataDirectory = new File("data");
        logger.info("Checking for data directory");
        if(!dataDirectory.exists())
        {
            logger.info("Data directory doesn't exist. Creating folder at " + dataDirectory.getAbsolutePath());
            dataDirectory.mkdir();
        }
        else
        {
            logger.info("RMA directory exists at " + dataDirectory.getAbsolutePath());
        }
        dateColumn.setCellValueFactory(new PropertyValueFactory<TimeClock, String>("date"));
        caseNumColumn.setCellValueFactory(new PropertyValueFactory<TimeClock, String>("caseNum"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<TimeClock, String>("custName"));
        serialNumColumn.setCellValueFactory(new PropertyValueFactory<TimeClock, String>("serialNum"));
        clockTypeColumn.setCellValueFactory(new PropertyValueFactory<TimeClock, String>("clockType"));

        clockTypeColumn.setSortable(false);

        dateColumn.setResizable(false);


        ObservableList<TimeClockStringProperties> clockObjects = FXCollections.observableArrayList();
        ArrayList<String> fileList = new ArrayList<>();
        FileHandler handler = new FileHandler();

        ArrayList<TimeClock> clocks = new ArrayList<>();


        Iterator savedFiles = FileUtils.iterateFiles(dataDirectory, null, false);

        while (savedFiles.hasNext())
        {
            File temp = (File) savedFiles.next();
            if (temp.getName().substring(temp.getName().length() - 8).equals(".syclock"))
            {
                logger.info("Loading " + temp.getName());
                clocks.add(handler.readSYObject(temp.getPath()));
            }
        }
        // Collections.sort(fileList, Collections.reverseOrder());
        fileList.stream().forEach((file ->
        {
            try
            {
                clocks.add(handler.readSYObject(file));
            } catch (IOException e)
            {
                e.printStackTrace();
            } catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }));

        clocks.stream().forEach((n -> clockObjects.add(new TimeClockStringProperties(
                n.getCaseData().getStartDate(),
                n.getCaseData().getCaseNum(),
                n.getCaseData().getCustomerName(),
                n.getCaseData().getSerialNum(),
                n.getInitialParts().getModel() + " " + n.getInitialParts().getFpuSize()
                        + "/" +n.getInitialParts().getReader() + "/" + n.getInitialParts().getCommunication(),
                n))));
        rmaTableView.setItems(clockObjects);
    }


}
