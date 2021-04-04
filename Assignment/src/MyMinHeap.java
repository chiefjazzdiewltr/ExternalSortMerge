public class MyMinHeap {
    private final int heapSize; // Initial Size of the Heap
    private final String[] elements; // Base Array for the heap
    private int head = 0; // The pointer for the head of the heap

    /**
     * Constructor that gets the size of the heap and stores it globally in the class
     * @param size Size of the heap
     */
    public MyMinHeap(int size) { // Stored as a string because it needs to handle non integer types instead of failing
        heapSize = size;
        elements = new String[heapSize];
    }

    /**
     * Adds an item to the heap and puts it in heap order
     * @param item Some string item as input
     */
    public void insert(String item) {
        elements[head] = item;
        head++;
        upheap();
    }

    /**
     * Removes the top most heap item and puts it in heap order
     * @return Returns the top most heap item
     */
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

    /**
     * Replaces the top most heap item with the parameter, puts it back in heap order
     * @param item Some string input
     * @return Returns the top most heap item
     */
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

    /**
     * Shows the top most heap item without changing the heap order
     * @return Returns top most heap item
     */
    public String peek() {
        return elements[0];
    }

    /**
     * Similar to the insert method but doesn't apply heap order to the array
     * @param item Takes a string input
     */
    public void load(String item) {
        elements[head] = item;
        head++;
    }

    /**
     * Goes through each sub-parent and puts them in heap order
     */
    public void reheap() {
        for(int i = parent(head); i > 0; i--) {
            downheap();
        }
    }

    /**
     * Returns if the heap is full or not
     * @return Boolean state of the fullness of the heap
     */
    public boolean isFull() {
        return head + 1 >= heapSize;
    }

    /**
     * Sets the size of the heap pointer to make sure the runs are handled correctly
     * @param change Increases or decreases the heap size by some integer
     */
    public void setHeapSize(int change) {
        head += change;
    }

    /**
     * A simple public method to return the size of the heap
     * @return Returns the current size of the heap
     */
    public int length() {
        return head;
    }

    /**
     * Swaps two items in the heap
     * @param i Item 1
     * @param j Item 2
     */
    private void swap(int i, int j) {
        String tmp = elements[i];
        elements[i] = elements[j];
        elements[j] = tmp;
    }

    /**
     * Puts the heap in heap order when adding items to the heap
     */
    private void upheap() {
        int tmp = head;
        while((tmp > 1) && (elements[parent(tmp)].compareTo(elements[tmp]) > 0)) {
            int child = tmp;
            int parent = parent(tmp);
            swap(child, parent);
            tmp = parent;
        }
    }

    /**
     * Puts the heap in heap order when removing or replacing items in the heap
     */
    private void downheap() {
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

    /**
     * Just gives the left index from the parent
     * @param i Takes some parent node
     * @return returns the position of the left sub-node
     */
    private int leftIndex(int i) {
        return i*2 + 1;
    }

    /**
     * Just gives the right index from the parent
     * @param i Takes some parent node
     * @return returns the position of the right sub-node
     */
    private int rightIndex(int i) {
        return 2*i + 2;
    }

    /**
     * An extra method to return the parent node of a given sub-node
     * @param i Takes some sub-node
     * @return Returns it's parent
     */
    private int parent(int i) {return (int)Math.ceil(i/2.0)-1;}
}