package implementacionesED;

public class SimpleNode {
    private int data;
    private SimpleNode next;

    public SimpleNode(){
        this(0);
    }

    public SimpleNode(int data){
        this.data = data;
        this.next = null;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public SimpleNode getNext() {
        return next;
    }

    public void setNext(SimpleNode next) {
        this.next = next;
    }
}
