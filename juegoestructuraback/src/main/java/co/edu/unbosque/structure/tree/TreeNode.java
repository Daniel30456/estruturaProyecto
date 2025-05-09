package co.edu.unbosque.structure.tree;


public class TreeNode<T> {
	/*
	 * ESTOS SON LOS NODOS QUE HACEN PARTE DE UN ARBOL DE NODOS BINARIOS.
	 * DONDE NOS DAN EL VALOR DEL NODO Y CUAL ES EL NODO QUE TIENE DE
	 * HIJOS
	 */
	
	/*
	 * ES EL TIPO DE DATO QUE TIENE EL NODO 
	 */
	private T value;
	/*
	 * ES EL NODO QUE ESTARA A LA IZQUIERDA DEL NODO
	 * EL HIJO IZQUIERDO
	 */
	private TreeNode<T> left;
	/*
	 * ES EL NODO QUE ESTARA A LA DERECHO DEL NODO
	 * EL HIJO DERECHO
	 */
	private TreeNode<T> right;

	public TreeNode(T value) {
		this.value = value;
	}

	
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public TreeNode<T> getLeft() {
		return left;
	}

	public void setLeft(TreeNode<T> left) {
		this.left = left;
	}

	public TreeNode<T> getRight() {
		return right;
	}

	public void setRight(TreeNode<T> right) {
		this.right = right;
	}
}
