package diagnostics;

import com.fazecast.jSerialComm.SerialPort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.InputStream;

public class SerialHandler
{
    SerialPort comPort;
    public SerialHandler()
    {

    }

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

        
    }

    public void serialRead()
    {
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        InputStream serialIn = comPort.getInputStream();

        try
        {
            for (int i =0; i < 1000; ++i)
                System.out.println((char)serialIn.read());
            serialIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        comPort.closePort();
    }
    public void setCommPort(SerialPort serialPort)
    {
        this.comPort = serialPort;//SerialPort.getCommPort(serialPort);
    }
}
