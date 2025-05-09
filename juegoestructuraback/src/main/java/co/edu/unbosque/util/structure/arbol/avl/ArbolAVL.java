package co.edu.unbosque.util.structure.arbol.avl;

public class ArbolAVL {

    private NodeAVL raiz;

    private int altura(NodeAVL N) {
        if (N == null)
            return 0;
        return N.getAltura();
    }

    private int obtenerBalance(NodeAVL N) {
        if (N == null)
            return 0;
        return altura(N.getIzquierda()) - altura(N.getDerecha());
    }

    // Rotación simple a la derecha (Caso 1)
    private NodeAVL rotarDerecha(NodeAVL y) {
        NodeAVL x = y.getIzquierda();
        NodeAVL hdx = x.getDerecha();

        x.setDerecha(y);
        y.setIzquierda(hdx);

        y.setAltura(Math.max(altura(y.getIzquierda()), altura(y.getDerecha())) + 1);
        x.setAltura(Math.max(altura(x.getIzquierda()), altura(x.getDerecha())) + 1);

        System.out.println("Rotación simple a la derecha en el nodo " + y.getDato());
        return x;
    }

    // Rotación simple a la izquierda (Caso 2)
    private NodeAVL rotarIzquierda(NodeAVL x) {
        NodeAVL y = x.getDerecha();
        NodeAVL T2 = y.getIzquierda();

        y.setIzquierda(x);
        x.setDerecha(T2);

        x.setAltura(Math.max(altura(x.getIzquierda()), altura(x.getDerecha())) + 1);
        y.setAltura(Math.max(altura(y.getIzquierda()), altura(y.getDerecha())) + 1);

        System.out.println("Rotación simple a la izquierda en el nodo " + x.getDato());
        return y;
    }

    // Rotación doble izquierda-derecha (Caso 3)
    private NodeAVL rotarIzquierdaDerecha(NodeAVL nodo) {
        System.out.println("Rotación doble izquierda-derecha en el nodo " + nodo.getDato());
        nodo.setIzquierda(rotarIzquierda(nodo.getIzquierda()));
        return rotarDerecha(nodo);
    }

    // Rotación doble derecha-izquierda (Caso 4)
    private NodeAVL rotarDerechaIzquierda(NodeAVL nodo) {
        System.out.println("Rotación doble derecha-izquierda en el nodo " + nodo.getDato());
        nodo.setDerecha(rotarDerecha(nodo.getDerecha()));
        return rotarIzquierda(nodo); 
    }

    // Función de inserción de nodo con regla estandar y verifica equilibrio
    private NodeAVL insertar(NodeAVL nodo, int dato) {
        // Inserción tradicional del BST
        if (nodo == null)
            return new NodeAVL(dato);

        if (dato < nodo.getDato())
            nodo.setIzquierda(insertar(nodo.getIzquierda(), dato));
        else if (dato > nodo.getDato())
            nodo.setDerecha(insertar(nodo.getDerecha(), dato));
        else
            return nodo; // Revisa si hay dupe

        // Hace un set de la nueva altura
        nodo.setAltura(Math.max(altura(nodo.getIzquierda()), altura(nodo.getDerecha())) + 1);

        //Verifica balance
        int balance = obtenerBalance(nodo);

        // Caso 1: Rotación simple a la derecha
        if (balance > 1 && dato < nodo.getIzquierda().getDato()) {
            return rotarDerecha(nodo);
        }

        // Caso 2: Rotación simple a la izquierda
        if (balance < -1 && dato > nodo.getDerecha().getDato()) {
            return rotarIzquierda(nodo);
        }

        // Caso 3: Rotación doble izquierda-derecha
        if (balance > 1 && dato > nodo.getIzquierda().getDato()) {
            return rotarIzquierdaDerecha(nodo);
        }

        // Caso 4: Rotación doble derecha-izquierda
        if (balance < -1 && dato < nodo.getDerecha().getDato()) {
            return rotarDerechaIzquierda(nodo);
        }

        return nodo;
    }

    // Método público para insertar
    public void insertar(int dato) {
        raiz = insertar(raiz, dato);
        
    }

    // Método para obtener la altura del árbol
    public int obtenerAlturaDelArbol() {
        return altura(raiz);
    }

    // Método para verificar si el árbol está balanceado
    public boolean estaBalanceado() {
        return estaBalanceado(raiz);
    }

    private boolean estaBalanceado(NodeAVL nodo) {
        if (nodo == null)
            return true;

        int balance = obtenerBalance(nodo);

        return (balance >= -1 && balance <= 1) && estaBalanceado(nodo.getIzquierda()) && estaBalanceado(nodo.getDerecha());
    }
}
