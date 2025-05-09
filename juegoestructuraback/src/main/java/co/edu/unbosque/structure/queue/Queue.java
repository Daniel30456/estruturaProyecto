package co.edu.unbosque.structure.queue;

public interface Queue<E> {
	public void enqueue(E info);

	public E dequeue();

	public int size();

}
