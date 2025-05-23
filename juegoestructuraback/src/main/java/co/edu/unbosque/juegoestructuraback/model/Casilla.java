package co.edu.unbosque.juegoestructuraback.model;

import jakarta.persistence.*;

@Entity
@Table(name = "casillas")
public class Casilla {

    public enum TipoTerreno {
        LLANO, MONTAÑA, BOSQUE, AGUA, EDIFICIO, CARRETERA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer casillaId;

    private int x;
    private int y;

    @Enumerated(EnumType.STRING)
    private TipoTerreno tipo;

    private boolean obstaculo;

    @Column(nullable = true)
    private String propietario;  // ← nuevo campo

    public Casilla() {
        // propietario queda en null (neutral)
    }

    public Casilla(int x, int y, TipoTerreno tipo) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
        this.obstaculo = (tipo == TipoTerreno.AGUA);
        this.propietario = null;  // neutral al crear
    }

    // GETTERS & SETTERS EXISTENTES

    public Integer getCasillaId() {
        return casillaId;
    }

    public void setCasillaId(Integer casillaId) {
        this.casillaId = casillaId;
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

    public TipoTerreno getTipo() {
        return tipo;
    }

    public void setTipo(TipoTerreno tipo) {
        this.tipo = tipo;
        this.obstaculo = (tipo == TipoTerreno.AGUA);
    }

    public boolean isObstaculo() {
        return obstaculo;
    }

    public void setObstaculo(boolean obstaculo) {
        this.obstaculo = obstaculo;
    }

    // NUEVO getter/setter del propietario
    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    // lógica de bonificación de defensa
    public int getBonificacionDefensa() {
        return switch (tipo) {
            case MONTAÑA -> 5;
            case BOSQUE   -> 3;
            case EDIFICIO -> 2;
            default       -> 0;
        };
    }

    public boolean esTransitable() {
        return !obstaculo;
    }

    @Override
    public String toString() {
        return "Casilla [casillaId=" + casillaId
             + ", x=" + x
             + ", y=" + y
             + ", tipo=" + tipo
             + ", obstaculo=" + obstaculo
             + ", propietario=" + propietario  
             + "]";
    }
}
