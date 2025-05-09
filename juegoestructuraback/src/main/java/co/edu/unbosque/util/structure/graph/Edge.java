package co.edu.unbosque.util.structure.graph;


public class Edge<T> {
    private final T origen;     
    private final T destino;   
    private final double peso;  


    public Edge(T origen, T destino, double peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

  
    public T getOrigen() {
        return origen;
    }

    
    public T getDestino() {
        return destino;
    }

   
    public double getPeso() {
        return peso;
    }

  
    @Override
    public String toString() {
        return origen + " -> " + destino + " (" + peso + ")";
    }
}