import java.io.*;

public class CreateRuns {
    private int size;
    private MyMinHeap heap;

    public CreateRuns(String initRunSize) {
        try {
            size = Integer.parseInt(initRunSize);
        }
        catch (NumberFormatException nfe) {
            System.err.println("Usage: java CreateRuns <int>");
        }
        heap = new MyMinHeap(size);
    }

    public void initRuns() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        while(reader.readLine() != null) {
            String line = reader.readLine();
            if(!heap.isFull()) {
                heap.load(line);
            }
            else {
                if(line.compareTo(heap.peek()) > 0) {
                    String output = heap.replace(line);
                    writer.write(output + "\n\r");
                }
                if(line.compareTo(heap.peek()) < 0) {
                    heap.setHeapSize(-1);
                }
            }

            if(heap.length() == 0) {
                writer.write("NEXTRUN\n\r");
                heap.setHeapSize(size);
            }
        }
        reader.close();
        writer.close();
    }
}
