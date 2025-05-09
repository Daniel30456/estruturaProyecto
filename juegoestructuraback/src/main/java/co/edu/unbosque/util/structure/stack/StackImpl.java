package co.edu.unbosque.util.structure.stack;

import co.edu.unbosque.util.structure.doublelinkedlist.MyDequeList;

public class StackImpl<E> implements Stack<E> {

	private MyDequeList<E> data;

	public StackImpl() {
		data = new MyDequeList<>();
	}
	

	@Override
	public void push(E info) {
		// TODO Auto-generated method stub
		data.insertFirst(info);
	}

	@Override
	public E pop() {
		// TODO Auto-generated method stub
		return data.removeFirst();
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
