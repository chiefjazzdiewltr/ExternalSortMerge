import java.io.*;

/**
 * Class responsible for splitting the runs into temporary files
 * @author Zac Gillions (1505717)
 * @author Linus Hauck (1505810)
 */
public class DistributeRuns {

    public File[] tmpFiles;

    /**
     * Constructor that is responsible for distributing the runs into temporary files
     * @param tempFileCount Specifies the temporary file count
     * @throws IOException Only throws if there is no standard input to get the runs from
     */
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
            boolean fileExists = newFile.createNewFile();
            if(!fileExists) {
                boolean isSucc = newFile.delete();
                if(!isSucc) {
                    System.err.println("Can't Delete");
                }
            }
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
            if(line.equals("NEXTRUN")) // If the line is NEXTRUN then increment the temp file index by one
            {
                writer.write("NEXTRUN\r\n");
                writer.close();
                fileIndex = (fileIndex + 1) % tmpFiles.length; // Uses modulus to essentially 'loop' through the files
                writer = new FileWriter(tmpFiles[fileIndex], true);
            }
            else writer.write(line + "\r\n");
        }
        writer.close();
    }
}