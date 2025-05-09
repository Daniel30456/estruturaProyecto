package co.edu.unbosque.util.structure.arbol.avl;

public class MainArbol {

	public static void main(String[] args) {
		ArbolAVL tree = new ArbolAVL();
		
		// Inserción de nodos
		tree.insertar(10);
		tree.insertar(20);
		tree.insertar(30);
		tree.insertar(40);
		tree.insertar(50);


		// Mostrar la altura final del árbol
		System.out.println("\nLa altura final del árbol es: " + tree.obtenerAlturaDelArbol());
	}
}
