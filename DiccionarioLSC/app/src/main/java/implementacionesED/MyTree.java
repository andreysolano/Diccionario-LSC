package implementacionesED;

import data.Palabra;

public class MyTree {

    private String palAleatoria;

    private class Node {
        public Palabra data;
        public Node left;
        public Node right;
        public int altura;

        public Node(Palabra data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.altura = 1;
        }
    }

    public Node root;
    public int capacity = 0;

    public MyTree() {
        root = null;
    }

    public String getPalAleatoria() {
        return palAleatoria;
    }

    public void setPalAleatoria(String palAleatoria) {
        this.palAleatoria = palAleatoria;
    }

    public Palabra find(String palabra) {
        return search(palabra, root);
    }

    private Palabra search(String palabra, Node p) {
        if (p != null) {
            if (p.data.getContenido().equalsIgnoreCase(palabra)) {
                return p.data;
            } else if (p.data.getContenido().compareToIgnoreCase(palabra) > 0) {
                return search(palabra, p.left);
            } else {
                return search(palabra, p.right);
            }
        } else return null;
    }


    public void add(Palabra data) {
        root = insert(data, root);
    }

    private Node insert(Palabra palabra, Node p) {
        if (p == null) {
            p = new Node(palabra);
            capacity++;
        } else if (palabra.getContenido().compareToIgnoreCase(p.data.getContenido()) > 0) {
            p.right = insert(palabra, p.right);
        } else if (palabra.getContenido().compareToIgnoreCase(p.data.getContenido()) < 0) {
            p.left = insert(palabra, p.left);
        } else return p;

        p.altura = Math.max(height(p.left), height(p.right)) + 1;

        int balance = getBalance(p);

        if (balance > 1 && palabra.getContenido().compareToIgnoreCase(p.data.getContenido()) < 0) {
            return rightRotate(p);
        }
        if (balance < -1 && palabra.getContenido().compareToIgnoreCase(p.data.getContenido()) > 0) {
            return leftRotate(p);
        }
        if (balance > 1 && palabra.getContenido().compareToIgnoreCase(p.data.getContenido()) > 0) {
            p.left = leftRotate(p.left);
            return rightRotate(p);
        }
        if (balance < -1 && palabra.getContenido().compareToIgnoreCase(p.data.getContenido()) < 0) {
            p.right = rightRotate(p.right);
            return leftRotate(p);
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
        } else {
            System.out.println("El elemento no esta en el arbol");
            return p;
        }

        p.altura = Math.max(height(p.left), height(p.right)) + 1;

        int balance = getBalance(p);

        if (balance > 1 && data.compareToIgnoreCase(p.data.getContenido()) < 0) {
            return rightRotate(p);
        }
        if (balance < -1 && data.compareToIgnoreCase(p.data.getContenido()) > 0) {
            return leftRotate(p);
        }
        if (balance > 1 && data.compareToIgnoreCase(p.data.getContenido()) > 0) {
            p.left = leftRotate(p.left);
            return rightRotate(p);
        }
        if (balance < -1 && data.compareToIgnoreCase(p.data.getContenido()) < 0) {
            p.right = rightRotate(p.right);
            return leftRotate(p);
        }

        return p;
    }

    private Node findMin(Node p) {
        if (p != null) {
            while (p.left != null) p = p.left;
        }
        return p;
    }

    public void print() {
        System.out.println("Current capacity: " + capacity);
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

    //AVL METHODS
    private int height(Node n) {
        if (n == null) return 0;
        return n.altura;
    }

    private int getBalance(Node n) {
        if (n == null) return 0;
        return height(n.left) - height(n.right);
    }

    private Node rightRotate(Node a) {
        Node b = a.left;
        Node temp = b.right;

        b.right = a;
        a.left = temp;

        a.altura = Math.max(height(a.left), height(a.right)) + 1;
        b.altura = Math.max(height(b.left), height(b.right)) + 1;

        return b;
    }

    private Node leftRotate(Node a) {
        Node b = a.right;
        Node temp = b.left;

        b.left = a;
        a.right = temp;

        a.altura = Math.max(height(a.left), height(a.right)) + 1;
        b.altura = Math.max(height(b.left), height(b.right)) + 1;

        return b;
    }


}
