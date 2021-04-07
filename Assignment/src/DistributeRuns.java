import java.io.*;

public class DistributeRuns {

    public File[] tmpFiles;

    public DistributeRuns(String tempFileCount) throws IOException {

        // Set size to 2, check if input is correct, if so, overwrite size to input value
        int size = 2;
        try
        {
            size = Integer.parseInt(tempFileCount);
        }
        catch (NumberFormatException nfe)
        {
            System.err.println("Usage: java DistributeRuns <int>");
        }

        // generate an array of our temp files
        tmpFiles = new File[size];
        for(int i = 0; i < size; i++)
        {

            String fileName = "temp" + i;
            File newFile = new File(fileName + ".tmp");
            if(newFile.exists()) newFile.delete();
            newFile.createNewFile();
            tmpFiles[i] = newFile;
        }

        // check input, fill up temp files with runs
        int fileIndex = 0;
        FileWriter writer = new FileWriter(tmpFiles[fileIndex], true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true)
        {
            String line = reader.readLine();
            if(line == null) break;
            if(line.equals("NEXTRUN"))
            {
                writer.write("NEXTRUN\r\n");
                writer.close();
                fileIndex = (fileIndex + 1) % tmpFiles.length;
                writer = new FileWriter(tmpFiles[fileIndex], true);
            }
            else writer.write(line + "\r\n");
        }
        writer.close();
    }
}