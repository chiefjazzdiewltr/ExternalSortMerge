import java.io.*;

public class MyMinHeap<T extends Comparable<T>> {
    private int heapSize;
    @SuppressWarnings("unchecked")
    private T[] elements = (T[]) new Comparable[heapSize];
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
        upheap();
    }

    public T remove() {
        if(head == 0) {
            return null;
        }
        else {
            T item = elements[0];
            elements[0] = elements[head];
            elements[head] = null;
            head--;
            downheap();
            return item;
        }
    }

    public void replace(T item, int place) {

    }

    public T peek() {
        return elements[head];
    }

    public void reheap() {

    }

    public void swap(int i, int j) {
        T tmp = elements[i];
        elements[i] = elements[j];
        elements[j] = tmp;
    }

    public void upheap() {
        int tmp = head;
        while((tmp > 1) && (elements[tmp/2].compareTo(elements[tmp]) > 0)) {
            int child = tmp;
            int parent = tmp/2;
            swap(child, parent);
            tmp = parent;
        }
    }

    public void downheap() {
        int tmp = 1;
        while(leftIndex(tmp) < head) {
            int child =  leftIndex(tmp);
            if(rightIndex(tmp) <= head && (elements[leftIndex(tmp)].compareTo(elements[rightIndex(tmp)]) > 0)) {
                child = rightIndex(tmp);
            }

            if(elements[tmp].compareTo(elements[child]) > 0) {
                swap(tmp, child);
            }
            else return;
            tmp = child;
        }
    }

    public int leftIndex(int i) {
        return i*2;
    }

    public int rightIndex(int i) {
        return 2*i + 1;
    }
}
