package co.edu.unbosque.structure.tree;

public class Persona {
	public String nombre;
	public Persona madre;
	public Persona padre;

	public Persona(String nombre, Persona madre, Persona padre) {
		this.nombre = nombre;
		this.madre = madre;
		this.padre = padre;
	}
}
