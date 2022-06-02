package PriorityQueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.BufferUnderflowException;
import java.util.Scanner;

/**
 * @className: BinaryHeap
 * @description: TODO 类描述
 * @author: YORE
 * @date: 2022/5/3
 **/
public class BinaryHeap<T extends Comparable<? super T>> {

    /* Debug test */
    public interface Debug {
        boolean DEBUG_PRINT = true;
        boolean DEBUG_TEST = true;
    }

    private T [] array;
    private int currentSize;	// # of elements in PQ

    private static final int DEFAULT_CAPACITY = 10;

    public BinaryHeap() {
        currentSize = 0;
        array = (T[]) new Comparable[DEFAULT_CAPACITY];
    }

    public BinaryHeap(int capacity) {
        currentSize = 0;
        array = (T []) new Comparable[capacity];
    }

    /**
     * Help method
     * Calculate the index of parent node
     */
    private int parent(int i) {
        return (i - 1) / 2;
    }


    /**
     * Help method
     * Calculate the index of left child
     */
    private int left(int i) {
        return 2 * i + 1;
    }


    /**
     * Help method
     * Calculate the index of right child
     */
    private int right(int i) {
        return 2 * i + 2;
    }


    /**
     * Add a new element to PQ
     */
    public boolean add(T x) {
        if(currentSize == array.length) {
            if(Debug.DEBUG_PRINT)
                System.out.println("[STAT]: add - double enlarge the size of PQ array");
            doubleEnlargeArray();
        }

        array[currentSize] = x;
        percolateUp(currentSize);
        currentSize++;
        return true;
    }

    /**
     *  Find the smallest item in the priority queue
     *  @return the smallest item, or -1 if the array is empty
     */
    public T findMin() {
        if(isEmpty())
            throw new BufferUnderflowException();

        return array[0];
    }


    // remove the minimum node: array[0]
    public T remove() {
        return remove(0);
    }

    // remove the index i
    public T remove(int i) {
        if(isEmpty())
            throw new BufferUnderflowException();

        T rm = array[i];
        array[i] = array[currentSize - 1];	// copy the last node to this to-be-deleted node
        currentSize--;
        percolateDown(i);
        return rm;
    }


    private void percolateUp(int i) {
        T tmp = array[i];
        while(i > 0) {
            if(array[parent(i)].compareTo(tmp) > 0) {
                // parent node > node, swap
                array[i] = array[parent(i)];
                i = parent(i);
            } else {
                break;
            }
        }
        array[i] = tmp;
    }


    private void percolateDown(int i) {
        T tmp = array[i];
        int small = left(i);
        while(small <= currentSize - 1) {
            if((small < currentSize - 1) && (array[small].compareTo(array[small + 1]) > 0)) {
                small = small + 1; //right child
            }
            if(tmp.compareTo(array[small]) < 0)
                break;
            array[i] = array[small];
            i = small;
            small = left(i);
        }
        array[i] = tmp;
    }


    /**
     * Test if the Priority Queue is empty.
     *
     * @return true if it is empty, else false
     */
    public boolean isEmpty() {
        return (currentSize == 0);
    }


    /**
     * Make the Priority Queue empty
     */
    public void makeEmpty() {
        currentSize = 0;
    }


    /**
     * Get the index of last node
     */
    public int indexOfLastNode() {
        return (currentSize - 1);
    }


    /**
     * Get the size of the array
     */
    private int getArraySzie() {
        return array.length;
    }


    /**
     *
     */
    public void printPQArray() {
        System.out.print("[" + currentSize + "]: ");
        for(int i = 0; i <= indexOfLastNode(); i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }


    /**
     * Enlarge the size of the array
     *
     * @param newSize - new size of the new array
     * @return -1	: false or invalid size
     * 			i	: new size of the new array
     */
    @SuppressWarnings({"unchecked", "JavadocReference"})
    private int enlargeArray(int newSzie) {
        if(newSzie < array.length) {
            if(Debug.DEBUG_PRINT) {
                System.out.println("[ERR]: enlargeArray - newSize < array.length!");
            }
            return -1;
        }

        T [] oldArray = array;
        array = (T []) new Comparable [newSzie];
        for(int i = 0; i < oldArray.length; i++) {
            array[i] = oldArray[i];
        }

        if(Debug.DEBUG_PRINT)
            System.out.println("[STAT]: enlargeArray - array new size = " + getArraySzie());

        return newSzie;
    }

    /**
     * Double the size of the PQ array
     */
    private int doubleEnlargeArray() {
        return enlargeArray(array.length * 2);
    }

    public static void main(String[] args) throws FileNotFoundException {
        BinaryHeap<Integer> pq = new BinaryHeap<>();

        Scanner in;
        if (args.length > 0) {
            File file = new File(args[0]);
            in = new Scanner(file);
        } else {
            in = new Scanner(System.in);
        }

        in = new Scanner(new File("PQ_test.txt"));

        String s = "";
        while(!((s = in.next()).equals("End"))) {
            switch(s) {
                case "Add" :
                    if(pq.currentSize == 0) {
                        System.out.println("PQ array is empty");
                    }

                    int x = in.nextInt();
                    pq.add(x);

                    pq.printPQArray();

                    break;

                case "Remove" :
                    if(pq.currentSize == 0) {
                        System.out.println("PQ array is empty");
                    } else {
                        pq.remove();
                    }
                    pq.printPQArray();

                    break;

            }
        }
        in.close();
    }

}
