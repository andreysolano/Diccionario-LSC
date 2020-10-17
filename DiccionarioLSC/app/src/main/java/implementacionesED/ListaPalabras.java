package implementacionesED;

import data.Palabra;

public class ListaPalabras {
    private DoubleLinkedNodePalabra head;

    public ListaPalabras() {
        this.head = null;
    }

    //    metodos de insercion
    public void push(Palabra p) {
        DoubleLinkedNodePalabra node = new DoubleLinkedNodePalabra(p);
        if (head == null) head = node;
        else {
            boolean done = false;
            String data = p.getContenido();
            DoubleLinkedNodePalabra ptr = head;
            while ((data.compareTo(ptr.getData().getContenido())) < 0) {
                if (ptr.getNext() == null) {
                    ptr.setNext(node);
                    node.setPrev(ptr);
                    done = true;
                    break;
                } else ptr = ptr.getNext();
            }
            if (!done) {
                ptr.setPrev(node);
                ptr.getPrev().setNext(node);
            }
        }
    }

    public Palabra get(int i) {
        DoubleLinkedNodePalabra p = head;
        for (int j = 0; j < i; j++) {
            p = p.getNext();
        }
        return p.getData();
    }
}
