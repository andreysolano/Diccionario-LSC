package implementacionesED;

import data.Palabra;

public class DoubleLinkedNodePalabra {
    private Palabra data;
    private DoubleLinkedNodePalabra next;
    private DoubleLinkedNodePalabra prev;

    public DoubleLinkedNodePalabra(Palabra data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    public Palabra getData() {
        return data;
    }

    public void setData(Palabra data) {
        this.data = data;
    }

    public DoubleLinkedNodePalabra getNext() {
        return next;
    }

    public void setNext(DoubleLinkedNodePalabra next) {
        this.next = next;
    }

    public DoubleLinkedNodePalabra getPrev() {
        return prev;
    }

    public void setPrev(DoubleLinkedNodePalabra prev) {
        this.prev = prev;
    }
}
