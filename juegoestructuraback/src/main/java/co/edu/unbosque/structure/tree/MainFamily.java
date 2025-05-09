package co.edu.unbosque.structure.tree;

public class MainFamily {
	public static void main(String[] args) {
		// Generacion 3.
		Persona abuelaMaterna = new Persona("María", null, null);
		Persona abueloMaterno = new Persona("José", null, null);
		Persona abuelaPaterna = new Persona("Luisa", null, null);
		Persona abueloPaterno = new Persona("Carlos", null, null);

		// Generación 2.
		Persona madre = new Persona("Marcela", abuelaMaterna, abueloMaterno);
		Persona padre = new Persona("Martin", abuelaPaterna, abueloPaterno);

		// Generación 1: tú
		Persona yo = new Persona("Jesus", madre, padre);

		// Construir árbol
		FamilyTree arbol = new FamilyTree(yo);

		arbol.preOrder();

		int generaciones = arbol.depth();
		System.out.println("Número de generaciones (niveles): " + generaciones);
	}
}
