package co.edu.unbosque.util.structure.doublelinkedlist;

//base para la cola y fila
public interface Dqueue<E> {

	public void insertLast(E info);

	public E removeLast();
	
	public void insertFirst (E info );

	public E removeFirst();

	public int size();
}
