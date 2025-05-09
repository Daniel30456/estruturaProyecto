package co.edu.unbosque.structure.tree;

public class MainBinaryTree {
	public static void main(String[] args) {
		
		BinaryTree<Integer> tree = new BinaryTree<>();

		
		TreeNode<Integer> n1 = new TreeNode<>(1);
		TreeNode<Integer> n2 = new TreeNode<>(2);
		TreeNode<Integer> n3 = new TreeNode<>(3);
		TreeNode<Integer> n4 = new TreeNode<>(4);
		TreeNode<Integer> n5 = new TreeNode<>(5);
		TreeNode<Integer> n6 = new TreeNode<Integer>(6);
		
		tree.setRoot(n1);
		n1.setLeft(n2);
		n1.setRight(n3);
		n2.setLeft(n4);
		n2.setRight(n5);
		n5.setRight(n6);

		System.out.print("Pre‐orden: ");
		tree.preorder(); // 1 2 4 5 6 3

		System.out.print("In‐orden: ");
		tree.inorder(); // 4 2 5 6 1 3

		System.out.print("Post‐orden: ");
		tree.postorder(); // 4 6 5 2 3 1

		System.out.print("Por niveles: ");
		tree.levelOrder(); // 1 2 3 4 5 6

		System.out.println("Altura: " + tree.height()); // 4
		System.out.println("Tamaño: " + tree.size()); // 6
		System.out.println("Contiene 3? " + tree.find(3)); // true
		System.out.println("Contiene 7? " + tree.find(7)); // false
	}
}
