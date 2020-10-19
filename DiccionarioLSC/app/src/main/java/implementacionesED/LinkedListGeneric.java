package implementacionesED;

public class LinkedListGeneric<T> {
    private SimpleNodeGeneric head;
    private SimpleNodeGeneric tail;

    public LinkedListGeneric(){
        this.head = null;
        this.tail = null;
    }

//    Add methods (Insert)
    public void pushFront(T data){
        SimpleNodeGeneric node = new SimpleNodeGeneric(data);
        if(head == null){
            head = node;
            tail = node;
        }else{
            node.setNext(head);
            head = node;
        }
    }

    public void pushBack(SimpleNodeGeneric node){
        if(head == null){
            head = node;
        }else tail.setNext(node);
        tail = node;
    }

    public void addBefore(SimpleNodeGeneric node1, T data){
        SimpleNodeGeneric node2 = new SimpleNodeGeneric<>(data);
        node2.setNext(node1);
        SimpleNodeGeneric p = head;
        while(p.getNext() != node1) p = p.getNext();
        p.setNext(node2);
    }

    public void addAfter(SimpleNodeGeneric node1, T data){
        SimpleNodeGeneric node2 = new SimpleNodeGeneric<>(data);
        node2.setNext(node1.getNext());
        node1.setNext(node2);
        if(node1 == tail) tail = node2;
    }

//    Pop & Peek methods (Extract)
    public SimpleNodeGeneric peekFront(){
        return head;
    }

    public SimpleNodeGeneric peekBack(){
        return tail;
    }

    public SimpleNodeGeneric popFront(){
        if(head == null) throw new RuntimeException("Action failed. List is empty");
        SimpleNodeGeneric p = head;
        if(head.getNext() == null){
            head = null;
            tail = null;
        }
        else head = head.getNext();
        return p;
    }

    public SimpleNodeGeneric popBack(){
        SimpleNodeGeneric p = head;
        while(p.getNext().getNext() != null) p = p.getNext();
        tail = p;
        tail.setNext(null);
        return tail;
    }

//    Find methods
    public boolean find(T i){
        boolean found = false;
        if(head == null) return false;
        SimpleNodeGeneric p = head;
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
    public void delete(T i){
        if(!find(i)) throw new RuntimeException("Action failed. List does not contain the specified element");
        else if(head.getData() == i){
            head = head.getNext();
        }else{
            SimpleNodeGeneric p = head;
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
