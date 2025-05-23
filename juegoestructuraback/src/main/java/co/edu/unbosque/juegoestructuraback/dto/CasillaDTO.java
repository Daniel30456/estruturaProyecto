package co.edu.unbosque.juegoestructuraback.dto;

public class CasillaDTO {
    private Integer casillaId;
    private int x;
    private int y;
    private String tipo;
    private String propietario;  // ← nuevo campo

    public CasillaDTO() {
    }

    public CasillaDTO(Integer id, int x, int y, String tipo, String propietario) {
        this.casillaId   = id;
        this.x           = x;
        this.y           = y;
        this.tipo        = tipo;
        this.propietario = propietario;
    }

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // GETTER / SETTER del propietario
    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }
}
