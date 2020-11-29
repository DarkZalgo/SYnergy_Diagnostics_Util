package diagnostics;

import java.io.*;
import java.nio.file.Path;

public class FileHandler
{


    public TimeClock readSYObject(String filePath) throws IOException, ClassNotFoundException
    {
        TimeClock clock;
        FileInputStream sacInStream = new FileInputStream(new File(filePath));
        ObjectInput clockInStream = new ObjectInputStream(sacInStream);

        clock = (TimeClock) clockInStream.readObject();

        return clock;
    }

    public void saveSYObject(TimeClock clock, String fileName) throws IOException
    {
        fileName = fileName + ".saclock";
        System.out.println("Starting save operation for " +fileName);
        FileOutputStream sacOutStream = new FileOutputStream(new File("." + File.separator+"data" + File.separator + fileName));
        ObjectOutputStream clockOutStream = new ObjectOutputStream(sacOutStream);

        clockOutStream.writeObject(clock);

        clockOutStream.close();
        sacOutStream.close();
        System.out.println("Successfully saved " + fileName);
    }

    public File getResourceAsFile(String resourcePath)
    {
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(resourcePath);
            if (in == null) {
                return null;
            }

            File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
            tempFile.deleteOnExit();

            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                //copy stream
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            return tempFile;
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
