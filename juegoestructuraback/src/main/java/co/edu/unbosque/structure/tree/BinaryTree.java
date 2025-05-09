package co.edu.unbosque.structure.tree;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree<T> {
	/*
	 * EL ATRIBUTO QUE NOS DA LA RAIZ. EL NODO INICIAL DE NUESTRO ARBOL. EL HIJO
	 */
	private TreeNode<T> root;

	public BinaryTree() {
		this.root = null;
	}

	/*
	 * RECORRIDO DE PRE-ORDEN
	 * 
	 * NOS DICE DESDE EL NODO RAIZ (HIJO) A LA IZQUIERDA. Y HASTA QUE NO ACABE EL
	 * LADO IZQUIERDO NO PASA AL DERECHO.
	 * 
	 * NODO IZQUIERDA DERECHA
	 * 
	 */
	public void preorder() {
		preorderRec(root);
		System.out.println();
	}

	private void preorderRec(TreeNode<T> node) {
		if (node == null)
			return;
		System.out.print(node.getValue() + " ");
		preorderRec(node.getLeft());
		preorderRec(node.getRight());
	}

	/*
	 * RECORRIDO EN ORDEN
	 * 
	 * INICIA DESDE EL NODO MAS A LA IZQUIERDA HASTA LLEGAR AL MAS A LA DERECHA
	 * 
	 * IZQUIERDA NODO DERECHA
	 */
	public void inorder() {
		inorderRec(root);
		System.out.println();
	}

	private void inorderRec(TreeNode<T> node) {
		if (node == null)
			return;
		inorderRec(node.getLeft());
		System.out.print(node.getValue() + " ");
		inorderRec(node.getRight());
	}

	/*
	 * RECORRIDO POST-ORDEN
	 * 
	 * INICIA DESDE EL NODO MAS A LA IZQUIERDA Y TODA LA INFORMACION QUE ESTE A LA
	 * IZQUIERDA DE LA RAIZ, CONTINUA CON TODA LA INFORMACION A SU DERECHA Y ACABA
	 * CON EL NODO PRINCIPAL
	 * 
	 * IZQUIERDA DERECHA NODO
	 */
	public void postorder() {
		postorderRec(root);
		System.out.println();
	}

	private void postorderRec(TreeNode<T> node) {
		if (node == null)
			return;
		postorderRec(node.getLeft());
		postorderRec(node.getRight());
		System.out.print(node.getValue() + " ");
	}

	/*
	 * RECORRIDO POR NIVELES (BFS)
	 * 
	 * NOS MUESTRA LA INFORMACION RESPECTO A LOS NIVELES PRIMERO LA RAIZ DESPUES
	 * LOS/EL HIJO DE LA RAIZ SEGUNDA GENERACION LOS HIJOS DE LOS HIJOS DE LA RAIZ Y
	 * ASI
	 */
	public void levelOrder() {
		if (root == null)
			return;
		Queue<TreeNode<T>> queue = new LinkedList<>();
		queue.add(root);

		while (!queue.isEmpty()) {
			int levelSize = queue.size();

			for (int i = 0; i < levelSize; i++) {
				TreeNode<T> cur = queue.poll();
				System.out.print(cur.getValue());

				if (i < levelSize - 1) {
					System.out.print(" ");
				}
				if (cur.getLeft() != null) {
					queue.add(cur.getLeft());
				}
				if (cur.getRight() != null) {
					queue.add(cur.getRight());
				}
			}

			if (!queue.isEmpty()) {
				System.out.print(" -> ");
			}
		}
		System.out.println();
	}

	/*
	 * CALCULA LA CANTIDAD DE NIVELES QUE TIENE EL ARBOL. LA GENERACION DE LA
	 * FAMILIA
	 */
	public int height() {
		return heightRec(root);
	}

	private int heightRec(TreeNode<T> node) {
		if (node == null)
			return 0;
		int hl = heightRec(node.getLeft());
		int hr = heightRec(node.getRight());
		return Math.max(hl, hr) + 1;
	}

	/*
	 * CALCULA LA CANTIDAD DE NODOS QUE HACEN PARTE DEL ARBOL
	 */
	public int size() {
		return sizeRec(root);
	}

	private int sizeRec(TreeNode<T> node) {
		if (node == null)
			return 0;
		return 1 + sizeRec(node.getLeft()) + sizeRec(node.getRight());
	}

	/*
	 * ESTE METODO BUSCA SI EL VALOR DE UN NODO HACE PARTE DEL ARBOL TRUE SI HACE
	 * PARTE FALSE SI NO HACE PARTE
	 */
	public boolean find(T value) {
		return findRec(root, value);
	}

	private boolean findRec(TreeNode<T> node, T value) {
		if (node == null)
			return false;
		if (node.getValue().equals(value))
			return true;
		return findRec(node.getLeft(), value) || findRec(node.getRight(), value);
	}

	public TreeNode<T> getRoot() {
		return root;
	}

	public void setRoot(TreeNode<T> root) {
		this.root = root;
	}

}
