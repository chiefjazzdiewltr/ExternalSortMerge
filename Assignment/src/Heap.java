/**
 * The class for the MinHeap, it stores nodes with 2 entries
 * @author Zac Gillions (1505717)
 * @author Linus Hauck (1505810)
 */
public class Heap {
    private Node[] data; // the array used to store data
    private int size; // the current size of heap. NOT capacity

    /**
     * Heap constructor that is responsible for filling the heap with nodes
     * @param capacity The size of the heap
     */
    public Heap(int capacity)
    {
        size = 0;
        data = new Node[capacity];
        for(int i = 0; i < capacity; i++) data[i] = new Node(); // fill the heap with empty nodes
    }

    /**
     * The node class responsible for storing the line and it's corresponding pointer to a temporary file
     */
    private class Node
    {
        public String value; // our node value
        public int tValue; // this is used to save temp file index

        /**
         * Node constructor just to initialize the node
         */
        public Node()
        {
            value = "NULL";
        }

        /**
         * Allows the node values to be set
         * @param v The line from the input stream
         * @param tv the temporary file index
         */
        public void set(String v, int tv)
        {
            value = v;
            tValue = tv;
        }

        /**
         * Returns a duplicate of the previous values
         * @return
         */
        public Node createCopy()
        {
            Node copy = new Node();// create new node, set to same values
            copy.set(value, tValue);
            return copy;
        }
    }

    /**
     * Allows the heap size to be changed
     * @param newSize The new size to be changed
     */
    public void setSize(int newSize)
    {
        if(newSize > data.length) size = data.length;
        else if(newSize < 0) size = 0;
        else size = newSize;
    }

    /**
     *  Loads the line onto the heap without putting it in heap order
     * @param v The line to be loaded
     */
    public void load(String v)
    {
        load(v, -1); // call our load method with a tvalue of null
    }

    /**
     * Loads a line and temporary file pointer onto the heap without putting it in heap order
     * @param v The line to be loaded
     * @param tv The temporary file pointer
     */
    public void load(String v, int tv)
    {
        if(isFull()) // if our heap is full, print error and return
        {
            System.err.println("Heap is full!");
            return;
        }
        data[size].set(v, tv); // set the bottom of heap to new value
        size++;
    }

    /**
     * Puts an unordered heap into heap order
     */
    public void reHeap()
    {
        for(int i = getParentIndex(size); i >= 0; i--) // start at our lowest node with a child
        {
            downHeap(i); // down heap all nodes with kids starting from bottom of heap.
        }
    }

    /**
     * Returns whether the heap is full
     * @return The status of the heap capacity
     */
    public boolean isFull() // returns true if our heap is at capacity
    {
        return size >= data.length;
    }

    /**
     * Returns whether the heap is empty
     * @return The 'emptiness' of the heap
     */
    public boolean isEmpty()
    {
        return size <= 0;
    }

    /**
     * Replaces a string in a node with the given line
     * @param v The line that is used to replace
     * @return Returns the string that was replaced
     */
    public String replace(String v)
    {
        return replace(v, 0); // call our replace method with a tvalue of null
    }

    /**
     * Replaces a string with a node and a temp file pointer
     * @param v The line that is used to replace
     * @param tv The temporary file pointer that is used to replace
     * @return Returns the line that was replaced
     */
    public String replace(String v, int tv)
    {
        String rValue = null; // set return value to null
        if(size != 0) rValue = remove(); // if heap is not empty, remove top value and set return value
        insert(v, tv); // insert new value
        return rValue; // return replaced value
    }

    /**
     * Removes the minimum value of the heap
     * @return Returns the line of the minimum value of the heap
     */
    public String remove()
    {
        if(isEmpty()) return null; // if heap is empty, there's nothing to do
        size--;
        swap(0, size); // move our old head out of the heap, move our bottom value to top
        downHeap(0); // downheap our new head
        return data[size].value;
    }

    /**
     * A 'dump' method used to output the heap values to standard output, used for testing purposes
     */
    public void printValues()
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

    /**
     * Returns the minimum value of the heap
     * @return The minimum value of the heap
     */
    public String peek()
    {
        if(size == 0) return null;
        return data[0].value;
    }

    /**
     * Returns the temporary file index at the top of the heap
     * @return The temporary file pointer at the top of the heap
     */
    public int tPeek()
    {
        if(size == 0) return -1;
        return data[0].tValue;
    }

    /**
     * Puts the heap in heap order upwards
     * @param index The starting point for the upheap method
     */
    public void upHeap(int index)
    {
        if(index == 0) return; // cannot upheap top of heap
        int parentIndex = getParentIndex(index); // get parent index
        if(greaterThan(parentIndex, index))  // if parent is greater
        {
            swap(parentIndex, index); // swap parent with child
            upHeap(parentIndex); // upheap new parent
        }
    }

    /**
     * Puts the heap in heap order downwards
     * @param index The starting point for the downheap method
     */
    public void downHeap(int index)
    {
        int childIndex = getSmallestChildIndex(index);
        if(childIndex == -1) return; // cannot downHeap from node without children
        if(greaterThan(index, childIndex)) // if child is greater
        {
            swap(childIndex, index); // swap child with node
            downHeap(childIndex); // downheap new child
        }
    }

    /**
     * Inserts a line without a temporary file pointer
     * @param v The line to be inserted
     */
    public void insert(String v)
    {
        insert(v, -1); // call our sort method with a tValue of null
    }

    /**
     * Inserts a line and a temporary file pointer
     * @param v The line to be inserted
     * @param tv The temporary file index
     */
    public void insert(String v, int tv)
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

    /**
     * Gets the parent node index of a given node
     * @param index The node index to find the parent for
     * @return The parent node index
     */
    private int getParentIndex(int index)
    {
        return ((index + 1) / 2) - 1;
    }

    /**
     * Gets the smallest child index from a given node index
     * @param index The node index to find the smallest child for
     * @return The smallest child index of the given index
     */
    private int getSmallestChildIndex(int index)
    {
        int cIndex = (index + 1) * 2; // index of right child (left is right - 1)
        if(cIndex - 1 >= size) return -1; // no kids, return -1
        if(cIndex >= size) return cIndex - 1; // no right kid, return left kid
        if(greaterThan(cIndex - 1, cIndex)) return cIndex; // lChild bigger, return rchild
        return cIndex - 1; // lChild smaller, return lChild

    }

    /**
     * Swaps the nodes at the two indexes
     * @param indexA The first index to be swapped
     * @param indexB The second index to be swapped
     */
    private void swap(int indexA, int indexB)
    {
        Node temp = data[indexA].createCopy();
        data[indexA] = data[indexB].createCopy();
        data[indexB] = temp;
    }

    /**
     * Returns true if index A is bigger than index B (lexicographically speaking)
     * @param indexA First index
     * @param indexB Second Index
     * @return True if Index A is bigger than Index b, False if less than or equal to
     */
    private boolean greaterThan(int indexA, int indexB)
    {
        if(data[indexA].value.equals("NULL")) return true;
        if(data[indexB].value.equals("NULL")) return false;
        return data[indexA].value.compareTo(data[indexB].value) > 0;
    }
}