package co.edu.unbosque.juegoestructuraback.model;

import jakarta.persistence.*;

@Entity
@Table(name = "unidades")
public class Unidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;        // infantería, tanque, artillería, etc.
    private int vida;           // puntos de vida
    private int ataque;         // poder ofensivo
    private int defensa;        // resistencia
    private int alcance;        // alcance de ataque (1 para cuerpo a cuerpo)
    private int movimiento;     // cuántas casillas puede moverse

    private String jugador;     // jugador que controla la unidad

    @ManyToOne
    @JoinColumn(name = "casilla_id", nullable = false)
    private Casilla casilla;    // ubicación actual en el mapa

    public Unidad() {}

    public Unidad(String tipo, int vida, int ataque, int defensa, int alcance, int movimiento, String jugador, Casilla casilla) {
        this.tipo = tipo;
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        this.alcance = alcance;
        this.movimiento = movimiento;
        this.jugador = jugador;
        this.casilla = casilla;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}

	public int getDefensa() {
		return defensa;
	}

	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}

	public int getAlcance() {
		return alcance;
	}

	public void setAlcance(int alcance) {
		this.alcance = alcance;
	}

	public int getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(int movimiento) {
		this.movimiento = movimiento;
	}

	public String getJugador() {
		return jugador;
	}

	public void setJugador(String jugador) {
		this.jugador = jugador;
	}

	public Casilla getCasilla() {
		return casilla;
	}

	public void setCasilla(Casilla casilla) {
		this.casilla = casilla;
	}

   
}
