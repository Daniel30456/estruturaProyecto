package co.edu.unbosque.util.structure.doublelinkedlist;

public class MyDoubleLinkedList<E> {
	protected DNode<E> head;
	protected DNode<E> currentPosition;

	public MyDoubleLinkedList() {

	}

	public void moveForward(int numPos) {
		if (numPos > 0 && head != null) {
			int posForward = numPos;
			if (currentPosition == null) {
				currentPosition = head;
				posForward--;
			}
			while (currentPosition.getNext() != null && posForward > 0) {
				currentPosition = currentPosition.getNext();
				posForward--;
			}
		}
	}

	public void moveBack(int numPos) {
		if (numPos <= 0 || head == null || currentPosition == null)
			return;
		int posBack = numPos;
		while (currentPosition != null && posBack > 0) {
			currentPosition = currentPosition.getPrevious();
		}
	}

	// Si el current position es nulo, los nodos que ingresen entran a remplazar la
	// cabeza
	public void insert(E data) {
		DNode<E> node = new DNode<>(data);
		if (currentPosition == null) {
			node.setNext(head);
			if (head != null) {
				head.setPrevious(node);
			}
			head = node;
		} else {
			node.setNext(currentPosition.getNext());
			node.setPrevious(currentPosition);

			if (currentPosition.getNext() != null) {
				currentPosition.getNext().setPrevious(node);
			}
			currentPosition.setNext(node);
		}
		currentPosition = node;
	}

	public E extract() {
		E info = null;
		if (currentPosition != null) {
			info = currentPosition.getInfo();
			if (head == currentPosition) {
				head = currentPosition.getNext();
			} else {
				currentPosition.getPrevious().setNext(currentPosition.getNext());
			}
			if (currentPosition.getNext() != null) {
				currentPosition.getNext().setPrevious(currentPosition.getPrevious());
			}
			currentPosition = currentPosition.getNext();
		}
		return info;
	}
	
	public String toString () {
		DNode <E> aux = head;
		StringBuilder sb = new StringBuilder();
		while(aux != null) {
			sb.append(aux.getInfo().toString());
			if(aux.getNext() != null) {
				sb.append(" <->");
			}
			aux = aux.getNext();
		}
		return sb.toString();
	}
	
}
