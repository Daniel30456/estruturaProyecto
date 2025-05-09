package co.edu.unbosque.structure.floydWarshall;

import co.edu.unbosque.util.structure.doublelinkedlist.MyDequeList;

public class FloydWarshall {
	private static MyDequeList<MyDequeList<Integer>> list = new MyDequeList<MyDequeList<Integer>>();
	private static MyDequeList<Integer> test = new MyDequeList<Integer>();

	public static void main(String[] args) {

		int INF = 100000000;

		test.insertLast(0);
		test.insertLast(4);
		test.insertLast(INF);
		test.insertLast(5);
		test.insertLast(INF);

		list.insertLast(test);
		test = new MyDequeList<Integer>();

		test.insertLast(INF);
		test.insertLast(0);
		test.insertLast(1);
		test.insertLast(INF);
		test.insertLast(6);

		list.insertLast(test);
		test = new MyDequeList<Integer>();

		test.insertLast(2);
		test.insertLast(INF);
		test.insertLast(0);
		test.insertLast(3);
		test.insertLast(INF);

		list.insertLast(test);
		test = new MyDequeList<Integer>();

		test.insertLast(INF);
		test.insertLast(INF);
		test.insertLast(1);
		test.insertLast(0);
		test.insertLast(2);

		list.insertLast(test);
		test = new MyDequeList<Integer>();

		test.insertLast(1);
		test.insertLast(INF);
		test.insertLast(INF);
		test.insertLast(4);
		test.insertLast(0);

		list.insertLast(test);

		floydWarshall(list);

		for (int i = 0; i < list.getSize(); i++) {
			for (int j = 0; j < list.get(i).getSize(); j++) {
				System.out.print(list.get(i).get(j) + " ");
			}
			System.out.println();
		}
	}

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
