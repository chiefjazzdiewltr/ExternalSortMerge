import java.io.*;

public class MergeRuns
{
    public static void main(String[] args) // used for testing
    {
      try{MergeRuns mergeRuns = new MergeRuns(args[0]);}
      catch(Exception e) 
      {
        System.err.println(e);
      }
    }

    private class TempFileReader
    {
      private File file;
      private BufferedReader reader;
      private boolean endOfCurrentRun;
      private boolean fileEmpty;

      public TempFileReader(File f)
      {
        try
        {
          file = f;
          reader = new BufferedReader(new FileReader(f));
        }
        catch(Exception e)
        {
          System.err.println(e);
        }
        endOfCurrentRun = false;
        fileEmpty = false;
      }

      public String getNextLine()
      {
        if(fileEmpty) return null;
        if(endOfCurrentRun) return null;
        try
        {
          String line = reader.readLine();
          if(line == null)
          {
            endOfCurrentRun = true;
            fileEmpty = true;
            reader.close();
            return null;
          }
          if(line.equals("NEXTRUN"))
          {
            endOfCurrentRun = true;
            return null;
          }
          return line;
        }
        catch(Exception e)
        {
          System.err.println(e);
        }
        return null;
      }

      public boolean isEmpty()
      {
        return fileEmpty;
      }

      public boolean runDone()
      {
        return endOfCurrentRun;
      }

      public void nextRun()
      {
        if(fileEmpty) return; // if our file is empty, there are no more runs.
        endOfCurrentRun = false;
      }

      public void replaceFile(File newFile)
      {
        file.delete();
        newFile.renameTo(file);
        file = newFile;
      }

      public void deleteFile()
      {
        try{reader.close();}
        catch(Exception e)
        {
          System.err.println(e);
        }
        file.delete();
      }
    }

    private TempFileReader[] fileReaders;

    public MergeRuns(String tempFileCount) throws IOException
    {
      DistributeRuns dRuns = new DistributeRuns(tempFileCount);

      int fileRuns = 2; // we set this to 2 so our while loop runs at least once.
      while(fileRuns > 1) // loop until we only have 1 run
      {
        fileRuns = -1; // this is because it loops an extra time once we're done
        File[] outputFiles = new File[dRuns.tmpFiles.length]; // these are our new temp files
      
        for(int i = 0; i < outputFiles.length; i++) // let's create the files.
        {
          File newFile = new File(dRuns.tmpFiles[i].getName().replace(".tmp", "B.tmp"));
          if(newFile.exists()) newFile.delete();
          newFile.createNewFile();
          outputFiles[i] = newFile;
        }
  
        int outputFileIndex = 0; // what output file are we currently putting our run into?
  
        fileReaders = new TempFileReader[dRuns.tmpFiles.length];
        for(int i = 0; i < fileReaders.length; i++)
        {
          fileReaders[i] = new TempFileReader(dRuns.tmpFiles[i]); // load our file reader with our temp files.
        }
  
        while(!allFilesEmpty()) // while our files have content, let's put them into our new files.
        {
          FileWriter writer = new FileWriter(outputFiles[outputFileIndex], true); // we want to add to our current output file
  
          Heap heap = new Heap(fileReaders.length); // our heap used to sort our files.
    
          // first we fill up our heap.
  
          int fileIndex = 0;
          while(!heap.isFull()) // first we fill up our heap.
          {
            TempFileReader f = fileReaders[fileIndex];
            String nextLine = fileReaders[fileIndex].getNextLine();
            if(f.runDone()) // if our run is finished, we must check if all of our files are done with their runs.
            {
              if(allRunsDone()) break;
            }
            else heap.load(nextLine, fileIndex); // we only want to load the value into the heap if it's part of the run
            fileIndex = (fileIndex + 1) % fileReaders.length; // loop through every file, on repeat. This is incase files are already empty, so we still fill up the heap
          }
    
          heap.reHeap(); // sort our heap
  
          // now our heap should be full, let's start outputting to the current output temp file.
          while(!allRunsDone())
          {
            int fromFileIndex = heap.tPeek(); // look at the top of our heap, what file did this come from?
            int offset = 0;
            String nextValue = null;
            while(offset < fileReaders.length)
            {
              int checkIndex = (fromFileIndex + offset) % fileReaders.length;
              nextValue = fileReaders[checkIndex].getNextLine();
              if(nextValue != null) break; // we know at least one file contains a value, as otherwise our allRunsDone() would return true and we wouldn't execute this
              offset++; // we want to take the value from the file our old value came from, but it could be empty, so check next file.
            }
            if(nextValue == null) continue;
            nextValue = heap.replace(nextValue, fromFileIndex); // swap top of our heap with next value, and take the value that was there.
            writer.write(nextValue + "\r\n"); // write our value to our file.
          }
  
          // our runs are done, so we must empty our heap into our current run.
          while(!heap.isEmpty())
          {
            writer.write(heap.remove() + "\r\n");
          }
          fileRuns++;
          writer.write("NEXTRUN\r\n");
          writer.close();
    
          for(TempFileReader f: fileReaders) f.nextRun();
          outputFileIndex = (outputFileIndex + 1) % outputFiles.length; // loop through our output files with runs.
        }
  
        // now we've gone through our temp files, we can delete our old temp files, and rename our new ones. Then repeat until we only have 1 run!
        for(int i = 0; i < fileReaders.length; i++) fileReaders[i].replaceFile(outputFiles[i]);
      }
      
      // we should now only have 1 run in our first temp file.

      TempFileReader reader = new TempFileReader(dRuns.tmpFiles[0]);

      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
      while(true)
      {
        String output = reader.getNextLine();
        if(output == null) 
        {
          reader.deleteFile();
          break;
        }
        writer.write(output + "\r\n");
      }
      writer.close();

      for(int i = 0; i < dRuns.tmpFiles.length; i++) dRuns.tmpFiles[i].delete(); // delete our temp files

    }

    public boolean allRunsDone() // are all the runs finished?
    {
      for(TempFileReader fReader: fileReaders)
      {
        if(!fReader.runDone()) return false; // check if ANY file still has output
      }
      return true; // no files contain output, so all files have finished their runs.
    }

    public boolean allFilesEmpty()
    {
      for(TempFileReader fReader: fileReaders)
      {
        if(!fReader.isEmpty()) return false;
      }
      return true;
    }
  
}