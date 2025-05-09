package co.edu.unbosque.util.structure.graph;

import co.edu.unbosque.util.structure.linkedlist.MyLinkedList;

public class Main2 {
    public static void main(String[] args) {
        // Crear los vértices (nombres de los vértices)
        MyLinkedList<String> vertices = new MyLinkedList<>();
        vertices.add("A");
        vertices.add("B");
        vertices.add("C");

        // Crear las aristas
        MyLinkedList<Edge<String>> aristas = new MyLinkedList<>();
        aristas.add(new Edge<>("A", "B", 4.0));  // A -> B con peso 4.0
        aristas.add(new Edge<>("B", "C", 1.0));  // B -> C con peso 1.0
        aristas.add(new Edge<>("A", "C", 5.0));  // A -> C con peso 5.0

        // Crear el algoritmo de Bellman-Ford
        BellmanFord<String> bellmanFord = new BellmanFord<>(vertices, aristas);

        // Ejecutar Bellman-Ford desde el vértice "A"
        if (bellmanFord.encontrarCaminosCortos("A")) {
            System.out.println("Distancias más cortas desde 'A':");
            MyLinkedList<Double> distancias = bellmanFord.getDistancias();
            MyLinkedList<String> predecesores = bellmanFord.getPredecesores();

            // Imprimir resultados
            for (int i = 0; i < vertices.size(); i++) {
                String vertice = vertices.get(i).getInfo();
                Double distancia = distancias.get(i).getInfo();
                String predecesor = predecesores.get(i).getInfo();
                System.out.println("Vértice: " + vertice + ", Distancia: " + distancia + ", Predecesor: " + predecesor);
            }
        } else {
            System.out.println("El grafo tiene un ciclo negativo.");
        }
    }
}
