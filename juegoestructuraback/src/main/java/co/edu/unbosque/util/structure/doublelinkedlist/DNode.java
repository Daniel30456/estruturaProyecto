package co.edu.unbosque.util.structure.doublelinkedlist;

public class DNode<E> {
	private DNode<E> next;
	private DNode<E> previous;
	private E info;

	public DNode() {

	}

	public DNode(DNode<E> next, DNode<E> previous, E info) {
		this.next = next;
		this.previous = previous;
		this.info = info;
	}

	public DNode(DNode<E> next, E info) {
		this.next = next;
		this.info = info;
	}

	public DNode(E info) {
		this(null, info);
	}

	public DNode<E> getNext() {
		return next;
	}

	public void setNext(DNode<E> next) {
		this.next = next;
	}

	public DNode<E> getPrevious() {
		return previous;
	}

	public void setPrevious(DNode<E> previous) {
		this.previous = previous;
	}

	public E getInfo() {
		return info;
	}

	public void setInfo(E info) {
		this.info = info;
	}

	@Override
	public String toString() {
		if (info != null) {

			return info.toString();

		}

		return null;

	}

}