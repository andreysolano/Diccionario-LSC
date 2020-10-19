package implementacionesED;

public class SimpleNodeGeneric<T> {
    private T data;
    private SimpleNodeGeneric next;

    public SimpleNodeGeneric(){
        this(null);
    }

    public SimpleNodeGeneric(T data){
        this.data = data;
        this.next = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public SimpleNodeGeneric getNext() {
        return next;
    }

    public void setNext(SimpleNodeGeneric next) {
        this.next = next;
    }
}
