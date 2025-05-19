package co.edu.unbosque.juegoestructuraback.util;

import co.edu.unbosque.util.structure.linkedlist.MyLinkedList;
import co.edu.unbosque.structure.queue.QueueImpl;
import co.edu.unbosque.util.structure.stack.StackImpl;

public class FunGraph {

	private MyLinkedList<MyLinkedList<Integer>> listaAdyacencia;
	private int cantidadNodos;

	public FunGraph(int cantidadNodos) {
		this.cantidadNodos = cantidadNodos;
		listaAdyacencia = new MyLinkedList<>();
		for (int i = 0; i < cantidadNodos; i++) {
			listaAdyacencia.add(new MyLinkedList<>());
		}
	}

// Agrega arista
	public void agregarArista(int nodoOrigen, int nodoDestino) {
		listaAdyacencia.get(nodoOrigen).getInfo().add(nodoDestino);
		listaAdyacencia.get(nodoDestino).getInfo().add(nodoOrigen);
	}

//busqueda en profundidad
	public void DFSRecursivo(int nodo, boolean[] visitados) {
		if (visitados[nodo]) {
			return;
		}
		visitados[nodo] = true;
		System.out.print(nodo + "-> ");

		MyLinkedList<Integer> conectados = listaAdyacencia.get(nodo).getInfo();
		recorrerConectados(conectados, 0, visitados);
	}

	private void recorrerConectados(MyLinkedList<Integer> conectados, int indice, boolean[] visitados) {
		if (indice >= conectados.size()) {
			return;
		}
		int conectado = conectados.get(indice).getInfo();
		if (!visitados[conectado]) {
			DFSRecursivo(conectado, visitados);
		}
		recorrerConectados(conectados, indice + 1, visitados);
	}

//busqueda en profundidad (ciclo)
	public void DFSCiclo(int nodo) {
		boolean[] visitados = new boolean[cantidadNodos];
		StackImpl<Integer> pila = new StackImpl<>();

		pila.push(nodo);

		while (pila.size() > 0) {
			int actual = pila.pop();

			if (!visitados[actual]) {
				visitados[actual] = true;
				System.out.print(actual + "-> ");

				MyLinkedList<Integer> conectados = listaAdyacencia.get(actual).getInfo();
				for (int i = 0; i < conectados.size(); i++) {
					int conectado = conectados.get(i).getInfo();
					if (!visitados[conectado]) {
						pila.push(conectado);
					}
				}
			}
		}
	}

// busqueda en anchura

	public int BFS(int nodoObjetivo) {
		boolean[] visitados = new boolean[cantidadNodos];
		QueueImpl<Integer> cola = new QueueImpl<>();
		int[] niveles = new int[cantidadNodos];

		cola.enqueue(0); // Empezar desde el piso 0
		visitados[0] = true;

		return BFSRecursivo(cola, visitados, niveles, nodoObjetivo);
	}

	private int BFSRecursivo(QueueImpl<Integer> cola, boolean[] visitados, int[] niveles, int nodoObjetivo) {
		if (cola.size() == 0) {
			return -1; // No encontrado
		}

		int actual = cola.dequeue();
		if (actual == nodoObjetivo) {
			return niveles[actual]; // Se encontró el nodo objetivo
		}

		MyLinkedList<Integer> vecinos = listaAdyacencia.get(actual).getInfo();
		procesarVecinos(cola, visitados, niveles, vecinos, actual, 0); // Procesar vecinos recursivamente

		return BFSRecursivo(cola, visitados, niveles, nodoObjetivo);
	}

	private void procesarVecinos(QueueImpl<Integer> cola, boolean[] visitados, int[] niveles,
			MyLinkedList<Integer> vecinos, int actual, int indice) {
		if (indice >= vecinos.size()) {
			return; // Caso base
		}

		int vecino = vecinos.get(indice).getInfo();
		if (!visitados[vecino]) {
			visitados[vecino] = true;
			niveles[vecino] = niveles[actual] + 1; // Incrementar nivel
			cola.enqueue(vecino);
		}

		procesarVecinos(cola, visitados, niveles, vecinos, actual, indice + 1); // Llamado recursivo
	}

}