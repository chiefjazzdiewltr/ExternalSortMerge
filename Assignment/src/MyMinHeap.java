import java.io.*;

public class MyMinHeap {

    public MyMinHeap(String heapSize) {
        try {
            int s = Integer.parseInt(heapSize);
        }
        catch (NumberFormatException nfe) {
            int s = 32;
        }
    }

    public <T> void insert(T item) {

    }
}
