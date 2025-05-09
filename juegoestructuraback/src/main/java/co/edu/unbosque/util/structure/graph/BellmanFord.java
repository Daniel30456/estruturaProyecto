package co.edu.unbosque.util.structure.graph;

import co.edu.unbosque.util.structure.linkedlist.MyLinkedList;

public class BellmanFord<T> {
	private MyLinkedList<T> vertices;
	private MyLinkedList<Edge<T>> aristas;
	private MyLinkedList<Double> distancias;
	private MyLinkedList<T> predecesores;

	public BellmanFord(MyLinkedList<T> vertices, MyLinkedList<Edge<T>> aristas) {
		this.vertices = vertices;
		this.aristas = aristas;
		this.distancias = new MyLinkedList<>();
		this.predecesores = new MyLinkedList<>();
	}

	public boolean encontrarCaminosCortos(T verticeInicial) {
		inicializarFuenteUnica(verticeInicial);

		for (int i = 0; i < vertices.size() - 1; i++) {
			boolean algunCambio = false;

			for (int j = 0; j < aristas.size(); j++) {
				if (relajar(aristas.get(j).getInfo())) {
					algunCambio = true;
				}
			}

			if (!algunCambio)
				break;
		}

		return !tieneCicloNegativo();
	}

	private int buscarIndiceVertice(T vertice) {
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).getInfo().equals(vertice)) {
				return i;
			}
		}
		return -1;
	}

	private void inicializarFuenteUnica(T verticeInicial) {

		distancias = new MyLinkedList<>();
		predecesores = new MyLinkedList<>();

		for (int i = 0; i < vertices.size(); i++) {
			distancias.add(Double.POSITIVE_INFINITY);
			predecesores.add(null);
		}

		int indiceInicial = buscarIndiceVertice(verticeInicial);
		if (indiceInicial != -1) {
			distancias.get(indiceInicial).setInfo(0.0);
		}
	}

	private boolean relajar(Edge<T> arista) {
		int indiceOrigen = buscarIndiceVertice(arista.getOrigen());
		int indiceDestino = buscarIndiceVertice(arista.getDestino());

		if (indiceOrigen == -1 || indiceDestino == -1) {
			return false;
		}

		double distanciaOrigen = (Double) distancias.get(indiceOrigen).getInfo();
		double distanciaDestino = (Double) distancias.get(indiceDestino).getInfo();

		if (distanciaDestino > distanciaOrigen + arista.getPeso()) {
			distancias.get(indiceDestino).setInfo(distanciaOrigen + arista.getPeso());
			predecesores.get(indiceDestino).setInfo(arista.getOrigen());
			return true;
		}
		return false;
	}

	private boolean tieneCicloNegativo() {
		for (int j = 0; j < aristas.size(); j++) {
			Edge<T> arista = aristas.get(j).getInfo();
			int indiceOrigen = buscarIndiceVertice(arista.getOrigen());
			int indiceDestino = buscarIndiceVertice(arista.getDestino());

			if (indiceOrigen == -1 || indiceDestino == -1)
				continue;

			double distanciaOrigen = (Double) distancias.get(indiceOrigen).getInfo();
			double distanciaDestino = (Double) distancias.get(indiceDestino).getInfo();

			if (distanciaDestino > distanciaOrigen + arista.getPeso()) {
				return true;
			}
		}
		return false;
	}

	public MyLinkedList<Double> getDistancias() {
		return distancias;
	}

	public MyLinkedList<T> getPredecesores() {
		return predecesores;
	}
}