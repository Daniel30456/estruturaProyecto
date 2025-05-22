package co.edu.unbosque.juegoestructuraback.dto;

import java.util.List;

public class GameStateDTO {

    private List<CasillaDTO> mapa;
    private List<UnidadDTO> unidades;
    
    // — Nuevos campos para control de turnos —
    private String currentPlayer;  // “Jugador 1” o “Jugador 2”
    private int turnNumber;        // contador de turnos

    public GameStateDTO() { }

    // getters y setters existentes
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

    // — Getters y setters nuevos —
    public String getCurrentPlayer() {
        return currentPlayer;
    }
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    public int getTurnNumber() {
        return turnNumber;
    }
    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }
}
