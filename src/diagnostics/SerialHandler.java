package diagnostics;

import com.fazecast.jSerialComm.SerialPort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SerialHandler
{
    public ObservableList<SerialPort> getPorts()
    {
        ObservableList<SerialPort> commPorts = FXCollections.observableArrayList();
        for (SerialPort commPort : SerialPort.getCommPorts())
        {
            commPorts.add(commPort);
        }

        return commPorts;
    }
    public void sendSerialCmd(String cmd)
    {
        SerialPort port = SerialPort.getCommPort("asdf");
        
    }
}
