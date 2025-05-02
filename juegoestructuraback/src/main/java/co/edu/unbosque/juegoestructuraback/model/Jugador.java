package co.edu.unbosque.juegoestructuraback.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "jugadores")
public class Jugador {
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id;
	private String nombre;
	private String email;
	private String contraseña;
	private boolean verificado;
	private String tokenVerificacion = UUID.randomUUID().toString();

	public Jugador() {
		// TODO Auto-generated constructor stub
	}

	public Jugador(String nombre, String email, String contraseña, boolean verificado) {
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

	public String getTokenVerificacion() {
		return tokenVerificacion;
	}

	public void setTokenVerificacion(String tokenVerificacion) {
		this.tokenVerificacion = tokenVerificacion;
	}

	@Override
	public String toString() {
		return "Jugador [id=" + id + ", nombre=" + nombre + ", email=" + email + ", contraseña=" + contraseña
				+ ", verificado=" + verificado + ", tokenVerificacion=" + tokenVerificacion + "]";
	}

}
