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
            File newFile = File.createTempFile(fileName,".tmp");
            tmpFiles[i] = newFile;
        }

        // check input, fill up temp files with runs
        int fileIndex = 0;
        FileWriter myWriter = new FileWriter(tmpFiles[fileIndex], true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true)
        {
            String line = reader.readLine();
            if(line == null) break;
            if(line.equals("NEXTRUN"))
            {
                myWriter.close();
                fileIndex = (fileIndex + 1) % tmpFiles.length;
                myWriter = new FileWriter(tmpFiles[fileIndex], true);
            }
            myWriter.write(line + "\r\n");
        }
        myWriter.close();
    }
}