package co.edu.unbosque.structure.bipartito;

import java.util.Scanner;
import co.edu.unbosque.util.structure.linkedlist.MyLinkedList;
import co.edu.unbosque.util.structure.linkedlist.Node;

public class GrafoBipartito {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		while (scanner.hasNext()) {
			int vertice = scanner.nextInt();
			int aristas = scanner.nextInt(); 

			MyLinkedList<MyLinkedList<Integer>> grafo = new MyLinkedList<>();
			for (int i = 0; i < vertice; i++) {
				grafo.add(new MyLinkedList<Integer>());
			}


			for (int i = 0; i < aristas; i++) {
				int u = scanner.nextInt(); 
				int v = scanner.nextInt(); 

				grafo.get(u).getInfo().add(v); 
				grafo.get(v).getInfo().add(u); 
			}

			if (esBipartito(grafo, vertice)) {
				System.out.println("SI");
			} else {
				System.out.println("NO");
			}
		}
		scanner.close();
	}

	public static boolean esBipartito(MyLinkedList<MyLinkedList<Integer>> grafo, int vertice) {
		int[] color = new int[vertice];

		MyLinkedList<Integer> lista = new MyLinkedList<>();

		for (int i = 0; i < vertice; i++) {
			if (color[i] == 0) {
				color[i] = 1;
				lista.addLast(i);

				while (!lista.isEmpty()) {
					int actual = lista.extract();

					MyLinkedList<Integer> vecinos = grafo.get(actual).getInfo();

					Node<Integer> currentVecino = vecinos.getFirst();
					while (currentVecino != null) {
						int vecino = currentVecino.getInfo();

						if (color[vecino] == 0) {
							color[vecino] = (color[actual] == 1) ? 2 : 1;
							lista.addLast(vecino);
						}
						else if (color[vecino] == color[actual]) {
							return false;
						}

						currentVecino = currentVecino.getNext();
					}
				}
			}
		}

		return true;
	}
}
