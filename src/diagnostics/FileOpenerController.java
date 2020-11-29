package diagnostics;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class FileOpenerController
{
    @FXML ListView rmaListView;


    @FXML
    public void readSYObjects(ActionEvent event) throws IOException, ClassNotFoundException
    {
        File dataDirectory = new File("." + File.separator + "data");
        if(!dataDirectory.exists())
        {
            dataDirectory.mkdir();
        }
            ObservableList<String> clockObjects = FXCollections.observableArrayList();
            ArrayList<String> fileList = new ArrayList<>();
            FileHandler handler = new FileHandler();

            ArrayList<TimeClock> clocks = new ArrayList<>();


            Iterator savedFiles = FileUtils.iterateFiles(dataDirectory, null, false);

            while (savedFiles.hasNext())
            {
                File temp = (File) savedFiles.next();
                if (temp.getName().substring(temp.getName().length() - 8).equals(".saclock"))
                {
                    clocks.add(handler.readSYObject(temp.getPath()));
                }
            }
            Collections.sort(fileList, Collections.reverseOrder());
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

            clocks.stream().forEach((n -> clockObjects.add(n.getInitialParts().getCoreboard())));
            // System.out.println(clocks.get(0).getInitialParts().getCoreboard());
            rmaListView.setItems(clockObjects);


    }
}
