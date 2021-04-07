public class Heap {
    private Node[] data; // the array used to store data
    private int size; // the current size of heap. NOT capacity

    public Heap(int capacity) // initialize a heap, set it's capacity
    {
        size = 0; 
        data = new Node[capacity]; 
        for(int i = 0; i < capacity; i++) data[i] = new Node(); // fill the heap with empty nodes
    }
    private class Node // Nodes are used to store data in our heap
    {
        public String value; // our node value
        public int tValue; // this is used to save temp file index

        public Node() // to initialize a node with NULL values
        {
            value = "NULL";
        } 
        public void set(String v, int tv) // to set values of node
        {
            value = v;
            tValue = tv;
        }
        public Node createCopy() // returns new node with same values
        {
            Node copy = new Node();// create new node, set to same values
            copy.set(value, tValue);
            return copy; 
        }
    }
    public void setSize(int newSize) // sets the size of our heap
    {
        if(newSize > data.length) size = data.length;
        else if(newSize < 0) size = 0;
        else size = newSize;
    }
    public void load(String v) // load the heap with a value, without a tvalue
    {
        load(v, -1); // call our load method with a tvalue of null
    }
    public void load(String v, int tv) // load a value and tvalue into the heap without sorting it
    {
        if(isFull()) // if our heap is full, print error and return
        {
            System.err.println("Heap is full!");
            return;
        }
        data[size].set(v, tv); // set the bottom of heap to new value
        size++;
    }
    public void reHeap() // re-sort our heap
    {
        for(int i = getParentIndex(size); i >= 0; i--) // start at our lowest node with a child
        {
            downHeap(i); // down heap all nodes with kids starting from bottom of heap.
        }
    }
    public boolean isFull() // returns true if our heap is at capacity
    {
        return size >= data.length;
    }
    public boolean isEmpty() // returns true if our heap is empty
    {
        return size <= 0;
    }
    public String replace(String v) // replaces with a value, without a tvalue
    {
        return replace(v, 0); // call our replace method with a tvalue of null
    }
    public String replace(String v, int tv) // replace top value of heap
    {
        String rValue = null; // set return value to null
        if(size != 0) rValue = remove(); // if heap is not empty, remove top value and set return value
        insert(v, tv); // insert new value
        return rValue; // return replaced value
    }
    public String remove() // remove top value of heap
    {
        if(isEmpty()) return null; // if heap is empty, there's nothing to do
        size--;
        swap(0, size); // move our old head out of the heap, move our bottom value to top
        downHeap(0); // downheap our new head
        return data[size].value;
    }
    public void printValues() // print our heap to the console
    {
        if(isEmpty()) return; // if the heap is empty, there's nothing to print
        int newLineAt = 2; // this will tell us when we need a new line
        System.err.println(data[0].value); // our head of the heap
        for(int i = 1; i < size; i++) // loop through heap
        {
            System.err.print(data[i].value + " "); // print current value
            if(i == newLineAt) // if end of line, print newLine character
            {
                System.err.println("");
                newLineAt = newLineAt * 2 + 2; // next time we need a newLine
            }
        }
        System.err.println("");
    }
    public String peek() // returns top of heap
    {
        if(size == 0) return null;
        return data[0].value;
    }
    public int tPeek() // returns tValue of top of heap
    {
        if(size == 0) return -1;
        return data[0].tValue;
    }
    public void upHeap(int index) // sort index upwards
    {
        if(index == 0) return; // cannot upheap top of heap
        int parentIndex = getParentIndex(index); // get parent index
        if(greaterThan(parentIndex, index))  // if parent is greater
        {
            swap(parentIndex, index); // swap parent with child
            upHeap(parentIndex); // upheap new parent
        }
    }
    public void downHeap(int index) // sort index downwards
    {
        int childIndex = getSmallestChildIndex(index);
        if(childIndex == -1) return; // cannot downHeap from node without children
        if(greaterThan(index, childIndex)) // if child is greater
        {
            swap(childIndex, index); // swap child with node
            downHeap(childIndex); // downheap new child
        }
    }
    public void insert(String v) // insert a value without a tValue
    {
        insert(v, -1); // call our sort method with a tValue of null
    }
    public void insert(String v, int tv) // insert a value into the heap
    {
        if(size >= data.length) // if our heap is full, print error to console and return
        {
            System.err.println("Heap is full!");
            return;
        }
        data[size].set(v, tv); // set the next value in heap to new value
        upHeap(size); // upheap our new value
        size++;
    }
    private int getParentIndex(int index) // gives Parent node index
    {
        return ((index + 1) / 2) - 1;
    }
    private int getSmallestChildIndex(int index) // gives smallest child node index
    {
        int cIndex = (index + 1) * 2; // index of right child (left is right - 1)
        if(cIndex - 1 >= size) return -1; // no kids, return -1
        if(cIndex >= size) return cIndex - 1; // no right kid, return left kid
        if(greaterThan(cIndex - 1, cIndex)) return cIndex; // lChild bigger, return rchild
        return cIndex - 1; // lChild smaller, return lChild
        
    }
    private void swap(int indexA, int indexB) // swaps two values in the heap
    {
        Node temp = data[indexA].createCopy();
        data[indexA] = data[indexB].createCopy();
        data[indexB] = temp;
    }
    private boolean greaterThan(int indexA, int indexB) // returns true if index A is greater than index B
    {
        if(data[indexA].value.equals("NULL")) return true;
        if(data[indexB].value.equals("NULL")) return false;
        return data[indexA].value.compareTo(data[indexB].value) > 0;
    }
}
