import java.io.*;

public class MergeRuns
{
    public MergeRuns(String tempFileCount)
    {
        try
        {
            DistributeRuns d = new DistributeRuns(tempFileCount);

            // printing out file info for testing
            for(File tmpFile: d.tmpFiles)
            {
                BufferedReader reader = new BufferedReader(new FileReader(tmpFile));
                while(true)
                {
                    String line = reader.readLine();
                    if(line == null) break;
                    System.out.println(line);
                }
                reader.close();
            }
        }
        catch(Exception e){System.err.println(e);}
    }
    public static void main(String[] args)
    {
        MergeRuns mergeRuns = new MergeRuns(args[0]);
    }
}