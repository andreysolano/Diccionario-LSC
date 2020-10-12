package implementacionesED;

public class MyQueue {
    private static final int DEFAULT_SIZE = 10;
    private int next_item;
    private int next_spot;
    private int[] data;

    public MyQueue() {
        this(DEFAULT_SIZE);
    }

    public MyQueue(int size) {
        this.next_item = 0;
        this.next_spot = 0;
        this.data = new int[size];
    }

    private boolean isEmpty() {
        return (next_item == next_spot);
    }

    private boolean isFull() {
        int next = (next_spot + 1) % data.length;
        return (next_item == next);
    }

    public void enqueue(int i) {
        if (isFull()) throw new RuntimeException("Action failed. Queue is full");
        data[next_spot] = i;
        next_spot = (next_spot + 1) % data.length;
    }

    public int dequeue() {
        if (isEmpty()) throw new RuntimeException("Action failed. Queue is empty");
        int item = data[next_item];
        next_item = (next_item + 1) % data.length;
        return item;
    }

}
