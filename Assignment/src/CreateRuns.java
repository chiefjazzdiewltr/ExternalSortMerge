import java.io.*;

public class CreateRuns {
    private int size;
    private MyMinHeap heap = new MyMinHeap("" + size);

    public CreateRuns(String initRunSize) {
        try {
            size = Integer.parseInt(initRunSize);
        }
        catch (NumberFormatException nfe) {
            System.err.println("Usage: java CreateRuns <int>");
        }
    }

    public void runs() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(!heap.isFull() || (reader.readLine() != null)) {
            String line = reader.readLine();
            if(!heap.isFull()){
                heap.load(line);
            }
            else {
                if(line.compareTo() > 0)
                {

                }
                else if(line.compareTo() < 0) {
                    heap.setHeapSize(-1);
                }
            }
        }
    }
}
