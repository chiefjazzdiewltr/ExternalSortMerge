public class MyMinHeap {
    private int heapSize;
    private String[] elements = new String[heapSize];
    private int head = 0;

    public MyMinHeap(String heapSize) {
        try {
            this.heapSize = Integer.parseInt(heapSize);
        }
        catch (NumberFormatException nfe) {
            this.heapSize = 32;
        }
    }

    public void insert(String item) {
        elements[head] = item;
        head++;
        upheap();
    }

    public String remove() {
        if(head == 0) {
            return null;
        }
        else {
            String item = elements[0];
            elements[0] = elements[head];
            elements[head] = null;
            head--;
            downheap();
            return item;
        }
    }

    public String replace(String item) {
        String tmp = "";
        if(head == 0) {
            insert(item);
        }
        else {
            tmp = elements[0];
            elements[0] = item;
            downheap();
        }
        return tmp;
    }

    public String peek() {
        return elements[head];
    }

    public void load(String item) {
        elements[head] = item;
        head++;
    }

    public void reheap() {
        for(int i = parent(head); i > 0; i--) {
            downheap();
        }
    }

    public void swap(int i, int j) {
        String tmp = elements[i];
        elements[i] = elements[j];
        elements[j] = tmp;
    }

    public void upheap() {
        int tmp = head;
        while((tmp > 1) && (elements[parent(tmp)].compareTo(elements[tmp]) > 0)) {
            int child = tmp;
            int parent = parent(tmp);
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

    public int parent(int i) {return (int)Math.ceil(i/2)-1;}
}
