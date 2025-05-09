package co.edu.unbosque.util.structure.doublelinkedlist;


public class MyDequeList<E> extends MyDoubleLinkedList<E> implements Dqueue<E> {
	private DNode<E> tail;
	private int size;

	public MyDequeList() {
		// TODO Auto-generated constructor stub
		this.head = new DNode<>();
		this.tail = new DNode<>();
		head.setNext(tail);
		tail.setPrevious(head);
		this.size = 0;
	}

	@Override
	public void insertLast(E info) {
		// TODO Auto-generated method stub
		DNode<E> t = tail;
		DNode<E> newNode = new DNode<>(info);
		newNode.setPrevious(t);
		tail.setNext(newNode);
		tail = newNode;
		if (size == 0) {
			head = newNode;
		}
		size++;
	}

	@Override
	public E removeLast() {
		// TODO Auto-generated method stub
		if (tail == null) {
			return null;
		} else {
			E info = tail.getInfo();
			tail = tail.getPrevious();
			size--;
			return info;
		}
	}

	@Override
	public void insertFirst(E info) {
		// TODO Auto-generated method stub
		DNode<E> h = this.head;
		DNode<E> newNode = new DNode<>(info);
		newNode.setNext(h);
		h.setPrevious(newNode);
		head = newNode;
		if (size == 0) {
			tail = newNode;
		}
		size++;
	}

	@Override
	public E removeFirst() {
		// TODO Auto-generated method stub
		if (head == null) {
			return null;
		} else {
			E info = head.getInfo();
			head = head.getNext();
			size--;
			return info;
		}

	}

	@Override
	public int size() {

		return size;
	}

	public DNode<E> getTail() {
		return tail;
	}

	public void setTail(DNode<E> tail) {
		this.tail = tail;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		String s = "cabeza [";
		DNode<E> actual = head;
		for (int i = 0; i < size; i++) {
			s += actual.getInfo();
			if (actual == tail) {
				break;
			}
			s += "<->";
			actual = actual.getNext();

		}
		return s + "] cola";

	}

	public void updateFirst(E info) {
		if (head == null) {
			return;
		}
		head.setInfo(info);

	}

	public void updateLast(E info) {
		if (tail == null) {
			return;

		}
		tail.setInfo(info);

	}

	public E get(int pos) {
		DNode<E> currentNode = new DNode<>();
		currentNode = head;
		int currentPos = 0;
		for (int i = 0; i < pos; i++) {
			if (currentPos == pos) {
				return currentNode.getInfo();
			}
			currentNode = currentNode.getNext();
			currentPos++;
		}
		return null;

	}

	public E getLast(int pos) {
		DNode<E> currentNode = new DNode<>();
		currentNode = tail;
		int currentPos = size;
		for (int i = 0; i < size; i++) {
			if (currentPos == pos) {
				return currentNode.getInfo();
			}
			currentNode = currentNode.getPrevious();
			currentPos--;
		}
		return null;

	}

	public int indexOf(E info) {
		DNode<E> currentNode = new DNode<>();
		currentNode = head;
		int currentPos = 0;
		for (int i = 0; i < size; i++) {
			if (currentNode.getInfo().equals(info)) {
				return currentPos;
			}
			currentNode = currentNode.getNext();
			currentPos++;
		}
		return -1;

	}

	public void set(int index, E info) {
	    if (index < 0 || index >= size) {
	        throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
	    }

	    DNode<E> currentNode = head;
	    for (int i = 0; i < index; i++) {
	        currentNode = currentNode.getNext();
	    }
	    currentNode.setInfo(info);
	}

	
	public int LastIndexOf(E info) {
		DNode<E> currentNode = new DNode<>();
		currentNode = tail;

		for (int i = 0; i < size; i++) {
			if (currentNode.getInfo().equals(info)) {
				return i;
			}

			currentNode = currentNode.getPrevious();

		}
		return -1;

	}
}
