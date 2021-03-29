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

    public void runs() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(!(heap.isFull()) || (reader.readLine() != null)) {
            String line = reader.readLine();
            System.out.println(line);
            heap.load(line);
        }
    }

    public static void main(String[] args) throws IOException {
        String heapSize = args[0];
        CreateRuns runs = new CreateRuns(heapSize);
        runs.runs();
    }
}
