package co.edu.unbosque.structure.bipartito;

import co.edu.unbosque.util.structure.linkedlist.MyLinkedList;
import co.edu.unbosque.util.structure.linkedlist.Node;

public class GrafoBipartito {
	
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
