import java.io.*;

/**
 * The class responsible for creating all the initial runs
 * @author Zac Gillions (1505717)
 * @author Linus Hauck (1505810)
 */
public class CreateRuns {
    private int size;
    private final Heap heap;

    /**
     * The main method responsible for initializing the runs
     * @param args The size of the heap to be used for the initial runs
     */
    public static void main(String[] args)
    {
        // Creating the initial set of runs
        CreateRuns c = new CreateRuns(args[0]);
        c.initRuns();
    }

    /**
     * The constructor to convert the size to an integer
     * @param initRunSize Converts the parameter into an integer, if it isn't an integer it will use the default size of 32
     */
    public CreateRuns(String initRunSize) {
        try {
            size = Integer.parseInt(initRunSize);
        }
        catch (NumberFormatException nfe) {
            size = 32;
        }
        heap = new Heap(size);
    }

    /**
     * The method responsible for initializing all the runs
     */
    private void initRuns() {
        try {
            // The base reader and writer interfaces
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
            String line;

            // fill heap
            while(!heap.isFull()){
                line = reader.readLine();
                if(line == null) break;
                heap.load(line);
            }
            // Puts the heap in heap order
            heap.reHeap();

            String topOfRun = "";

            // fill runs
            while(true)
            {
                if(topOfRun.compareTo(heap.peek()) > 0) // reduce heap size until smallest value goes into run
                {
                    heap.remove();
                    if(heap.isEmpty())// This section is ending the runs
                    {
                        writer.write("NEXTRUN\r\n");
                        heap.setSize(size);
                        heap.reHeap();
                    }
                }
                // Reads the lines and replaces the top of the heap with the new line
                line = reader.readLine();
                if(line == null) line = "NULL";
                topOfRun = heap.replace(line);
                if(topOfRun.equals("NULL")) // If the top of the heap is null then go to a new run
                {
                    heap.setSize(size);
                    heap.reHeap();
                    if(heap.peek().equals("NULL")) break;
                    if(topOfRun.compareTo(heap.peek()) > 0) writer.write("NEXTRUN\r\n");
                    topOfRun = heap.replace("NULL");
                }
                else writer.write(topOfRun + "\r\n");
            }
            reader.close();
            writer.close();
        }
        catch(IOException iException) {
            System.err.println(iException.getMessage());
        }
    }
}