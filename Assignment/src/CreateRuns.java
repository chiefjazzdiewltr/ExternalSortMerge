import java.io.*;

/**
 * The class responsible for creating all the initial runs
 * @author Zac Gillions (1505717)
 * @author Linus Hauck (1505810)
 */
public class CreateRuns {
    private int size;
    private Heap heap;

    public static void main(String[] args) // used for testing
    {
        CreateRuns c = new CreateRuns(args[0]);
        c.initRuns();
    }

    public CreateRuns(String initRunSize) {
        try {
            size = Integer.parseInt(initRunSize);
        }
        catch (NumberFormatException nfe) {
            size = 32;
        }
        heap = new Heap(size);
    }

    public void initRuns() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
            String line = "";

            // fill heap
            while(!heap.isFull()){
                line = reader.readLine();
                if(line == null) break;
                heap.load(line);
            }
            heap.reHeap();

            String topOfRun = "";

            // fill runs
            while(true)
            {
                if(topOfRun.compareTo(heap.peek()) > 0) // reduce heap size until smallest value goes into run
                {
                    heap.remove();
                    if(heap.isEmpty())
                    {
                        writer.write("NEXTRUN\r\n");
                        heap.setSize(size);
                        heap.reHeap();
                    }
                }
                line = reader.readLine();
                if(line == null) line = "NULL";
                topOfRun = heap.replace(line);
                if(topOfRun.equals("NULL"))
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