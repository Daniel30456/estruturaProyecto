package co.edu.unbosque.util.structure.graph;

import co.edu.unbosque.util.structure.linkedlist.MyLinkedList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int numeroCasos = sc.nextInt();
		sc.nextLine();

		for (int caso = 0; caso < numeroCasos; caso++) {
			
			int n = sc.nextInt();
			int m = sc.nextInt();
			sc.nextLine();

			MyLinkedList<Integer> sistemasEstelares = new MyLinkedList<>();
			MyLinkedList<Edge<Integer>> wormholes = new MyLinkedList<>();

			for (int i = 0; i < n; i++) {
				sistemasEstelares.add(i);
			}

			for (int wh = 0; wh < m; wh++) {

				while (!sc.hasNextInt()) {
					sc.next();
				}
				int origen = sc.nextInt();
				int destino = sc.nextInt();
				int tiempo = sc.nextInt();
				wormholes.add(new Edge<>(origen, destino, tiempo));
			}

			BellmanFord<Integer> bf = new BellmanFord<>(sistemasEstelares, wormholes);
			boolean tieneCicloTemporal = !bf.encontrarCaminosCortos(0);

			System.out.println(tieneCicloTemporal ? "possible" : "not possible");
		}

		sc.close();
	}
}