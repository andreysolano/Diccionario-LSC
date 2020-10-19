package implementacionesED;

public class LinkedList {
    private SimpleNode head;
    private SimpleNode tail;

    public LinkedList(){
        this.head = null;
        this.tail = null;
    }

//    Add methods (Insert)
    public void pushFront(SimpleNode node){
        if(head == null){
            head = node;
            tail = node;
        }else{
            node.setNext(head);
            head = node;
        }
    }

    public void pushBack(SimpleNode node){
        if(head == null){
            head = node;
        }else tail.setNext(node);
        tail = node;
    }

    public void addBefore(SimpleNode node1, int data){
        SimpleNode node2 = new SimpleNode(data);
        node2.setNext(node1);
        SimpleNode p = head;
        while(p.getNext() != node1) p = p.getNext();
        p.setNext(node2);
    }

    public void addAfter(SimpleNode node1, int data){
        SimpleNode node2 = new SimpleNode(data);
        node2.setNext(node1.getNext());
        node1.setNext(node2);
        if(node1 == tail) tail = node2;
    }

//    Pop & Peek methods (Extract)
    public SimpleNode peekFront(){
        return head;
    }

    public SimpleNode peekBack(){
        return tail;
    }

    public SimpleNode popFront(){
        if(head == null) throw new RuntimeException("Action failed. List is empty");
        SimpleNode p = head;
        if(head.getNext() == null){
            head = null;
            tail = null;
        }
        else head = head.getNext();
        return p;
    }

    public SimpleNode popBack(){
        SimpleNode p = head;
        while(p.getNext().getNext() != null) p = p.getNext();
        tail = p;
        tail.setNext(null);
        return tail;
    }

//    Find methods
    public boolean find(int i){
        boolean found = false;
        if(head == null) return false;
        SimpleNode p = head;
        while(p.getNext() != null){
            if(p.getData() == i){
                found = true;
                break;
            }
            p = p.getNext();
        }
        return found;
    }

//    Delete methods
    public void delete(int i){
        if(!find(i)) throw new RuntimeException("Action failed. List does not contain the specified element");
        else if(head.getData() == i){
            SimpleNode temp = popFront();
        }else{
            SimpleNode p = head;
            while(p.getNext().getNext() != null){
                if(p.getNext().getData() == i){
                    p.setNext(p.getNext().getNext());
                }else p = p.getNext();
            }
            if(p.getNext().getData() == i){
                p.setNext(null);
                tail = p;
            }
        }
    }
}
