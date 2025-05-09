	package co.edu.unbosque.util.structure.linkedlist;
	
	public class MyLinkedList<E> {
	
	    protected Node<E> first;
	
	    public MyLinkedList() {
	        this.first = null;
	    }
	
	    public Node<E> getFirst() {
	        return first;
	    }
	
	    public void setFirst(Node<E> first) {
	        this.first = first;
	    }
	    //verificar si esta vacia
	    public boolean isEmpty() {
	        return (this.first == null);
	    }
	 // agregar datos
	    public void add(E info) {
	        Node<E> newNode = new Node<E>(info);
	        newNode.setNext(this.first);
	        first = newNode;
	    }
	    
	    public void insert(E info, Node<E> previous) {
	        if (previous != null) {
	            Node<E> newNode = new Node<E>(info);
	            newNode.setNext(previous.getNext());
	            previous.setNext(newNode);
	        }
	    }
	
	    public void addLast(E info) {
	        if (isEmpty()) {
	            this.first = new Node<E>(info);
	        } else {
	            addLastRecursive(this.first, info);
	        }
	    }
	
	    private void addLastRecursive(Node<E> current, E info) {
	        if (current.getNext() == null) {
	            current.setNext(new Node<E>(info));
	        } else {
	            addLastRecursive(current.getNext(), info);
	        }
	    }
	
	    public Node<E> getLastNode() {
	        return getLastNodeRecursive(this.first);
	    }
	
	    private Node<E> getLastNodeRecursive(Node<E> current) {
	        if (current == null || current.getNext() == null) {
	            return current;
	        }
	        return getLastNodeRecursive(current.getNext());
	    }
	
	    public E extract() {
	        if (this.first == null) return null;
	        E data = this.first.getInfo();
	        this.first = this.first.getNext();
	        return data;
	    }
	
	    public E extract(Node<E> previous) {
	        if (previous != null && previous.getNext() != null) {
	            E data = previous.getNext().getInfo();
	            previous.setNext(previous.getNext().getNext());
	            return data;
	        }
	        return null;
	    }
	
	    public int size() {
	        return sizeRecursive(this.first);
	    }
	
	    private int sizeRecursive(Node<E> current) {
	        if (current == null) {
	            return 0;
	        }
	        return 1 + sizeRecursive(current.getNext());
	    }
	
	    public Node<E> get(E info) {
	        return getRecursive(this.first, info);
	    }
	
	    private Node<E> getRecursive(Node<E> current, E info) {
	        if (current == null || current.getInfo().equals(info)) {
	            return current;
	        }
	        return getRecursive(current.getNext(), info);
	    }
	
	    public Node<E> get(int n) {
	        return getRecursiveByIndex(this.first, n, 0);
	    }
	
	    private Node<E> getRecursiveByIndex(Node<E> current, int n, int counter) {
	        if (current == null || counter == n) {
	            return current;
	        }
	        return getRecursiveByIndex(current.getNext(), n, counter + 1);
	    }
	
	    public int indexOf(E info) {
	        return indexOfRecursive(this.first, info, 0);
	    }
	
	    private int indexOfRecursive(Node<E> current, E info, int position) {
	        if (current == null) return -1;
	        if (current.getInfo().equals(info)) return position;
	        return indexOfRecursive(current.getNext(), info, position + 1);
	    }
	
	    public int numberOfOcurrence(E info) {
	        return countOccurrencesRecursive(this.first, info);
	    }
	
	    private int countOccurrencesRecursive(Node<E> current, E info) {
	        if (current == null) return 0;
	        int count = current.getInfo().equals(info) ? 1 : 0;
	        return count + countOccurrencesRecursive(current.getNext(), info);
	    }
	
	    public E extractLast() {
	        if (isEmpty()) return null;
	        if (this.first.getNext() == null) {
	            E info = this.first.getInfo();
	            this.first = null;
	            return info;
	        }
	        return extractLastRecursive(this.first);
	    }
	
	    private E extractLastRecursive(Node<E> current) {
	        if (current.getNext().getNext() == null) {
	            E info = current.getNext().getInfo();
	            current.setNext(null);
	            return info;
	        }
	        return extractLastRecursive(current.getNext());
	    }
	
	    public String print() {
	        return toString();
	    }
	
	    @Override
	    public String toString() {
	        return toStringRecursive(this.first);
	    }
	
	    private String toStringRecursive(Node<E> current) {
	        if (current == null) return "";
	        String result = current.getInfo().toString();
	        if (current.getNext() != null) {
	            result += " --> " + toStringRecursive(current.getNext());
	        }
	        return result;
	    }
	}
			