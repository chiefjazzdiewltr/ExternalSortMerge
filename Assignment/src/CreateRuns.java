import java.io.*;

/**
 * The class responsible for creating all the initial runs
 * @author Zac Gillions (1505717)
 * @author Linus Hauck ()
 */
public class CreateRuns {
    private int size;
    private final MyMinHeap heap;

    public static void main(String[] args)
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
        heap = new MyMinHeap(size);
    }

    public void initRuns() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
            String line = reader.readLine();
            while(!heap.isFull()){
                heap.load(line);
            }
            heap.reheap();
            String tmp = "";
            while(line != null) {

                if(heap.length() == 0) {
                    heap.setHeapSize(size - 1);
                    heap.reheap();
                    writer.write("NEXTRUN\r\n");
                    writer.write(tmp + "\r\n");
                    line = reader.readLine();
                }

                tmp = heap.replace(line);
                if(line.compareTo(tmp) >= 0) {
                    writer.write(tmp + "\r\n");
                }
                else {
                    heap.setHeapSize(-1);
                }

                line = reader.readLine();
            }
            reader.close();
            writer.close();
        }
        catch(IOException iException) {
            System.err.println(iException.getMessage());
        }
    }
}