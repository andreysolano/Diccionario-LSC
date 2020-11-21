package implementacionesED;

import data.Palabra;

public class MyTree {
    private class Node {
        public Palabra data;
        public Node left;
        public Node right;

        public Node(Palabra data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public Node root;
    public int capacity = 0;

    public MyTree() {
        root = null;
    }

    public Palabra find(String palabra) {
        return search(palabra, root);
    }

    private Palabra search(String palabra, Node p){
        if (p != null) {
            if(p.data.getContenido().equalsIgnoreCase(palabra)){
                return p.data;
            }else if(p.data.getContenido().compareToIgnoreCase(palabra) > 0){
                return search(palabra, p.left);
            }else{
                return search(palabra, p.right);
            }
        } else return null;
    }


    public void add(Palabra data) {
        root = insert(data, root);
    }

    private Node insert(Palabra palabra, Node p) {
        if (p == null){
            p = new Node(palabra);
            capacity++;
        }else if (palabra.getContenido().compareToIgnoreCase(p.data.getContenido()) > 0) {
            p.right = insert(palabra, p.right);
        } else if (palabra.getContenido().compareToIgnoreCase(p.data.getContenido()) < 0) {
            p.left = insert(palabra, p.left);
        }
        return p;
    }

    public void remove(String data) {
        root = delete(data, root);
    }

    private Node delete(String data, Node p) {
        if (p != null) {
            if (data.compareToIgnoreCase(p.data.getContenido()) > 0) {
                p.right = delete(data, p.right);
            } else if (data.compareToIgnoreCase(p.data.getContenido()) < 0) {
                p.left = delete(data, p.left);
            } else {
                capacity--;
                if (p.left == null && p.right == null) p = null;
                else {
                    if (p.left == null) p = p.right;
                    else if (p.right == null) p = p.left;
                    else {
                        Node temp = findMin(p.right);
                        p.data = temp.data;
                        p.right = delete(p.data.getContenido(), p.right);
                    }
                }
            }
        } else System.out.println("El elemento no esta en el arbol");
        return p;
    }

    private Node findMin(Node p) {
        if (p != null) {
            while (p.left != null) p = p.left;
        }
        return p;
    }

    public void print() {
        System.out.println("Current capacity: "+capacity);
        System.out.println("Contenidos del arbol:");
        if (root == null) System.out.println("Ninguno");
        else {
            recursivePrint(root);
        }
    }

    private void recursivePrint(Node p) {
        if (p.left != null) recursivePrint(p.left);
        System.out.println(p.data.getContenido());
        if (p.right != null) recursivePrint(p.right);
    }
}
