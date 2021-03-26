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
}
