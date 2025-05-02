package co.edu.unbosque.juegoestructuraback.dto;

public class JugadorDTO {
	private Integer id;
	private String nombre;
	private String email;
	private String contraseña;
	private boolean verificado;
	
	public JugadorDTO() {
		// TODO Auto-generated constructor stub
	}

	public JugadorDTO(String nombre, String email, String contraseña, boolean verificado) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.contraseña = contraseña;
		this.verificado = verificado;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public boolean isVerificado() {
		return verificado;
	}

	public void setVerificado(boolean verificado) {
		this.verificado = verificado;
	}

	@Override
	public String toString() {
		return "JugadorDTO [id=" + id + ", nombre=" + nombre + ", email=" + email + ", contraseña=" + contraseña
				+ ", verificado=" + verificado + "]";
	}
	
}
