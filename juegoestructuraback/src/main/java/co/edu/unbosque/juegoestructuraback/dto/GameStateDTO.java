package co.edu.unbosque.juegoestructuraback.dto;

import java.util.List;

public class GameStateDTO {
    private List<CasillaDTO> mapa;
    private List<UnidadDTO> unidades;

    public GameStateDTO() {}

    public GameStateDTO(List<CasillaDTO> mapa, List<UnidadDTO> unidades) {
        this.mapa     = mapa;
        this.unidades = unidades;
    }

    public List<CasillaDTO> getMapa() {
        return mapa;
    }
    public void setMapa(List<CasillaDTO> mapa) {
        this.mapa = mapa;
    }

    public List<UnidadDTO> getUnidades() {
        return unidades;
    }
    public void setUnidades(List<UnidadDTO> unidades) {
        this.unidades = unidades;
    }
}
