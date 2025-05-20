package co.edu.unbosque.juegoestructuraback.model;

import jakarta.persistence.*;
import java.util.EnumSet;
import java.util.Set;

/**
 * Entidad que representa una unidad en el mapa con sus propias reglas de paso.
 */
@Entity
@Table(name = "unidades")
public class Unidad {

	public enum TipoUnidad {
		INFANTERIA, TANQUE, BARCO, HELICOPTERO;

		/**
		 * Define para cada tipo de unidad los terrenos transitables.
		 */
		public Set<Casilla.TipoTerreno> getAllowedTerrains() {
			return switch (this) {
			case INFANTERIA, TANQUE -> EnumSet.of(Casilla.TipoTerreno.LLANO, Casilla.TipoTerreno.BOSQUE,
					Casilla.TipoTerreno.MONTAÑA, Casilla.TipoTerreno.CARRETERA, Casilla.TipoTerreno.EDIFICIO);
			case BARCO -> EnumSet.of(Casilla.TipoTerreno.AGUA);
			case HELICOPTERO -> EnumSet.allOf(Casilla.TipoTerreno.class);
			};
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private TipoUnidad tipo;
	private int vida;
	private int ataque;
	private int defensa;
	private int alcance;
	private int movimiento;
	private int x;
	private int y;

	private String jugador;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "casillaId", nullable = false)
	private Casilla casilla;

	public Unidad() {
	}

	public Unidad(TipoUnidad tipo, int vida, int ataque, int defensa, int alcance, int movimiento, int x, int y,
			String jugador, Casilla casilla) {
		super();
		this.tipo = tipo;
		this.vida = vida;
		this.ataque = ataque;
		this.defensa = defensa;
		this.alcance = alcance;
		this.movimiento = movimiento;
		this.x = x;
		this.y = y;
		this.jugador = jugador;
		this.casilla = casilla;
	}

	public Long getId() {
		return id;
	}

	public TipoUnidad getTipo() {
		return tipo;
	}

	public void setTipo(TipoUnidad tipo) {
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

	public Set<Casilla.TipoTerreno> getAllowedTerrains() {
		return tipo.getAllowedTerrains();
	}
	

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Unidad[id=" + id + ", tipo=" + tipo + ", x=" + casilla.getX() + ", y=" + casilla.getY() + "]";
	}
}
