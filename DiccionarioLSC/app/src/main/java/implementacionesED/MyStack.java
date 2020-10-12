package implementacionesED;

public class MyStack {
    public static final int DEFAULT_SIZE = 10;

    private int top;
    private int[] data;

    public MyStack(){
        this(DEFAULT_SIZE);
    }

    public MyStack(int size){
        this.top = -1;
        this.data = new int[size];
    }

    private boolean isEmpty(){
        return (this.top <= -1);
    }

    private boolean isFull(){
        return (this.top >= data.length-1);
    }

    public int pop(){
        if(isEmpty()) throw new RuntimeException("Action failed. Stack is empty");
        top--;
        return data[top+1];
    }

    public void push(int i){
        if(isFull()) throw new RuntimeException("Action failed. Stack is full");
        top++;
        data[top] = i;
    }
    
}