import java.io.*;

public class MyMinHeap<T> {
    private int heapSize;
    @SuppressWarnings("unchecked")
    private T[] elements = (T[]) new Object[heapSize];
    private int head = 0;

    public MyMinHeap(String heapSize) {
        try {
            this.heapSize = Integer.parseInt(heapSize);
        }
        catch (NumberFormatException nfe) {
            this.heapSize = 32;
        }
    }

    public void insert(T item) {
        elements[head] = item;
        head++;
        reheap();
    }

    public void remove(T item) {

    }

    public void replace(T item, int place) {

    }

    public T peek() {
        return elements[head];
    }

    public void reheap() {

    }
}
