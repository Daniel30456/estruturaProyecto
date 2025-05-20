package co.edu.unbosque.structure.floydWarshall;

import co.edu.unbosque.util.structure.doublelinkedlist.MyDequeList;

public class FloydWarshall {

	public static void floydWarshall(MyDequeList<MyDequeList<Integer>> list) {
		int V = list.getSize();

		for (int k = 0; k < V; k++) {
			for (int i = 0; i < V; i++) {
				for (int j = 0; j < V; j++) {
					if (list.get(i).get(k) != 1e8 && list.get(k).get(j) != 1e8) {
						int d = list.get(i).get(k) + list.get(k).get(j);
						if (d < list.get(i).get(j)) {
							list.get(i).set(j, d);
						}
					}
				}
			}
		}
	}
}
