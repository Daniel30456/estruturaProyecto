package co.edu.unbosque.structure.queue;

import co.edu.unbosque.util.structure.doublelinkedlist.MyDequeList;

public class QueueImpl<E> implements Queue<E> {

	private MyDequeList<E> data;

	public QueueImpl() {

		// TODO Auto-generated constructor stub
		data = new MyDequeList<>();
	}

	@Override
	public void enqueue(E info) {
		// TODO Auto-generated method stub
		data.insertFirst(info);
	}

	@Override
	public E dequeue() {
		// TODO Auto-generated method stub
		return data.removeLast();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public String toString() {
		return data.toString();
	}

}
