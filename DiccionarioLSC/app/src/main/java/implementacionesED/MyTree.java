public class MyTree {
    private class Node {
        public String data;
        public Node left;
        public Node right;

        public Node(String data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public Node root;

    public MyTree() {
        root = null;
    }

    public void add(String data) {
        if (data != "") {
            root = insert(data, root);
        }else System.out.println("Entrada invalida");
    }

    public Node insert(String data, Node p) {
        if (p == null) p = new Node(data);
        else if (data.compareToIgnoreCase(p.data) > 0) {
            p.right = insert(data, p.right);
        } else if (data.compareToIgnoreCase(p.data) < 0) {
            p.left = insert(data, p.left);
        } else System.out.println("El elemento ya esta en el arbol");
        return p;
    }

    public void remove(String data) {
        root = delete(data, root);
    }

    public Node delete(String data, Node p) {
        if (p != null) {
            if (data.compareToIgnoreCase(p.data) > 0) {
                p.right = delete(data, p.right);
            } else if (data.compareToIgnoreCase(p.data) < 0) {
                p.left = delete(data, p.left);
            } else {
                if (p.left == null && p.right == null) p = null;
                else {
                    if (p.left == null) p = p.right;
                    else if (p.right == null) p = p.left;
                    else {
                        Node temp = findMin(p.right);
                        p.data = temp.data;
                        p.right = delete(p.data, p.right);
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
        System.out.println("Contenidos del arbol:");
        if (root == null) System.out.println("Ninguno");
        else {
            recursivePrint(root);
        }
    }

    public void recursivePrint(Node p) {
        if (p.left != null) recursivePrint(p.left);
        System.out.println(p.data);
        if (p.right != null) recursivePrint(p.right);
    }
}
