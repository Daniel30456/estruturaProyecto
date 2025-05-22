package co.edu.unbosque.juegoestructuraback.service;

import co.edu.unbosque.juegoestructuraback.dto.CasillaDTO;
import co.edu.unbosque.juegoestructuraback.dto.GameStateDTO;
import co.edu.unbosque.juegoestructuraback.dto.UnidadDTO;
import co.edu.unbosque.juegoestructuraback.model.Casilla;
import co.edu.unbosque.juegoestructuraback.model.Unidad;
import co.edu.unbosque.juegoestructuraback.repository.CasillaRepository;
import co.edu.unbosque.juegoestructuraback.repository.UnidadRepository;
import co.edu.unbosque.util.structure.linkedlist.MyLinkedList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final CasillaRepository casillaRepository;
    private final UnidadRepository unidadRepository;
    private final CasillaService casillaService;
    private final UnidadService unidadService;

    public GameService(
        CasillaRepository casillaRepository,
        UnidadRepository unidadRepository,
        CasillaService casillaService,
        UnidadService unidadService
    ) {
        this.casillaRepository  = casillaRepository;
        this.unidadRepository   = unidadRepository;
        this.casillaService     = casillaService;
        this.unidadService      = unidadService;
    }

    @Transactional
    /**
     * Reinicia el mapa, genera tropas, e inicializa control de turnos.
     */
    public GameStateDTO iniciarJuego(int tropasPorJugador) {
        // 1) Reinicia y obtiene lista de casillas
        List<CasillaDTO> mapaDto = casillaService.reiniciarMapa();

        // 2) Elige el tipo de unidad (p. ej. TANQUE)
        Unidad.TipoUnidad tipo = Unidad.TipoUnidad.TANQUE;

        // 3) Genera tropas de jugador1
        MyLinkedList<UnidadDTO> j1 = unidadService.generarUnidadesEnRegion(
            tropasPorJugador, 
            tipo, 
            "Jugador 1", 
            0, 2,  // filas 0..2
            0, 2   // columnas 0..2
        );

        // 4) Genera tropas de jugador2
        MyLinkedList<UnidadDTO> j2 = unidadService.generarUnidadesEnRegion(
            tropasPorJugador, 
            tipo, 
            "Jugador 2", 
            9, 11, // filas 9..11
            9, 11  // columnas 9..11
        );

        // 5) Combina en lista Java normal
        List<UnidadDTO> todas = new ArrayList<>();
        j1.forEach(todas::add);
        j2.forEach(todas::add);

        // 6) Inicializa flags de acción
        todas.forEach(u -> u.setHasActed(false));

        // 7) Construye el DTO incluyendo turno
        GameStateDTO state = new GameStateDTO();
        state.setMapa(mapaDto);
        state.setUnidades(todas);
        state.setCurrentPlayer("Jugador 1");  
        state.setTurnNumber(1);

        return state;
    }

    /**
     * Devuelve el estado actual (útil para refrescar sin reiniciar)
     */
    public GameStateDTO getEstadoActual() {
        List<CasillaDTO> casillas = casillaRepository.findAll()
                                       .stream().map(this::toDTO).collect(Collectors.toList());
        List<UnidadDTO> unidades = unidadRepository.findAll()
                                       .stream().map(this::toDTO).collect(Collectors.toList());
        // — IMPORTANTE: aquí deberías orquestar también currentPlayer y turnNumber 
        // si guardases en BD, pero como es memoria, lo manejaremos sólo vía iniciarJuego/nextTurn.
        GameStateDTO state = new GameStateDTO();
        state.setMapa(casillas);
        state.setUnidades(unidades);
        return state;
    }

    /**
     * Cambia de turno: alterna currentPlayer, incrementa turnNumber
     * y resetea hasActed sólo para el jugador que arranca.
     */
    public GameStateDTO nextTurn(GameStateDTO prevState) {
        String siguiente = prevState.getCurrentPlayer().equals("Jugador 1")
                            ? "Jugador 2"
                            : "Jugador 1";

        // 1) Actualiza identificador de jugador y número de turno
        GameStateDTO state = new GameStateDTO();
        state.setMapa(prevState.getMapa());
        state.setUnidades(prevState.getUnidades());
        state.setCurrentPlayer(siguiente);
        state.setTurnNumber(prevState.getTurnNumber() + 1);

        // 2) Resetear hasActed para las unidades del nuevo jugador
        state.getUnidades().stream()
             .filter(u -> u.getJugador().equals(siguiente))
             .forEach(u -> u.setHasActed(false));

        return state;
    }

    // Método helper de mapeo
    private CasillaDTO toDTO(Casilla c) {
        return new CasillaDTO(
            c.getCasillaId(),
            c.getX(),
            c.getY(),
            c.getTipo() != null ? c.getTipo().name() : null
        );
    }

    private UnidadDTO toDTO(Unidad u) {
        Casilla c = u.getCasilla();
        UnidadDTO dto = new UnidadDTO(
            u.getId(),
            u.getTipo().name(),
            u.getVida(),
            u.getAtaque(),
            u.getDefensa(),
            u.getAlcance(),
            u.getMovimiento(),
            u.getJugador(),
            c.getX(),
            c.getY(),
            false  // por defecto no ha actuado si vienes de BD (reinicio)
        );
        return dto;
    }

    @Transactional
    public GameStateDTO moveUnidad(
            Long unidadId,
            int xDestino,
            int yDestino,
            GameStateDTO prevState) {

        // 1) Localiza la unidad en prevState
        UnidadDTO uDto = prevState.getUnidades().stream()
            .filter(u -> u.getId().equals(unidadId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unidad no encontrada"));

        // 2) Validaciones de turno y flag
        if (!uDto.getJugador().equals(prevState.getCurrentPlayer())) {
            throw new IllegalStateException("No es tu turno");
        }
        if (uDto.isHasActed()) {
            throw new IllegalStateException("Esta unidad ya actuó");
        }

        // 3) Delegar la lógica de movimiento real
        MyLinkedList<Point> ruta = unidadService.moverUnidad(unidadId, xDestino, yDestino);
        // — ruta se usa en el frontend si quieres mostrar animación —

        // 4) Marca la unidad como actuada y actualiza sus coords
        uDto.setHasActed(true);
        uDto.setX(xDestino);
        uDto.setY(yDestino);

        // 5) Devuelve prevState modificado (manteniendo turnos)
        return prevState;
    }

}
