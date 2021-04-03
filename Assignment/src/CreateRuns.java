import java.io.*;

import javax.crypto.interfaces.PBEKey;

public class CreateRuns {
    private int size;
    private MyMinHeap heap;

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
            System.err.println("Usage: java CreateRuns <int>");
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
            while(line != null) {
                heap.reheap();
                if(heap.length() == 0) {
                    writer.write("NEXTRUN\r\n");
                    heap.setHeapSize(size - 1);
                }

                String tmp = heap.replace(line);
                writer.write(tmp + "\r\n");
                if(line.compareTo(tmp) >= 0) {
                    tmp = heap.replace(line);
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