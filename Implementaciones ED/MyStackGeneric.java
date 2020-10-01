public class MyStackGeneric<T> {
    public static final int DEFAULT_SIZE = 10;

    private int top;
    private T[] data;

    public MyStackGeneric(){
        this(DEFAULT_SIZE);
    }

    public MyStackGeneric(int size){
        this.top = -1;
        this.data = (T[])new Object[size];
    }

    private boolean isEmpty(){
        return (this.top <= -1);
    }

    private boolean isFull(){
        return (this.top >= data.length-1);
    }

    public T pop(){
        if(isEmpty()) throw new RuntimeException("Action failed. Stack is empty");
        top--;
        return data[top+1];
    }

    public void push(T i){
        if(isFull()) throw new RuntimeException("Action failed. Stack is full");
        top++;
        data[top] = i;
    }
    
}