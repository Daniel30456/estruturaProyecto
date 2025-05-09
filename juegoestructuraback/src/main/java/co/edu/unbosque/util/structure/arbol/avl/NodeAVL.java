package co.edu.unbosque.util.structure.arbol.avl;

public class NodeAVL {
    
    private int dato;
    private int altura;
    private NodeAVL izquierda, derecha;

    public NodeAVL(int item) {
        dato = item;
        altura = 1;
    }
    
    public int getDato() {
        return dato;
    }

    public void setDato(int dato) {
        this.dato = dato;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public NodeAVL getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(NodeAVL izquierda) {
        this.izquierda = izquierda;
    }

    public NodeAVL getDerecha() {
        return derecha;
    }

    public void setDerecha(NodeAVL derecha) {
        this.derecha = derecha;
    }
}