package co.edu.unbosque.juegoestructuraback.service;

import co.edu.unbosque.juegoestructuraback.dto.CasillaDTO;
import co.edu.unbosque.juegoestructuraback.dto.GameStateDTO;
import co.edu.unbosque.juegoestructuraback.dto.UnidadDTO;
import co.edu.unbosque.juegoestructuraback.model.Unidad;
import co.edu.unbosque.util.structure.linkedlist.MyLinkedList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GameService {

    private final CasillaService casillaService;
    private final UnidadService unidadService;

    public GameService(CasillaService casillaService,
                       UnidadService unidadService) {
        this.casillaService = casillaService;
        this.unidadService  = unidadService;
    }

  
    @Transactional
    /**
     * Reinicia el mapa y genera tropas en las esquinas,
     * luego empaqueta todo en un GameStateDTO.
     */
    public GameStateDTO iniciarJuego(int tropasPorJugador) {
        // 1) Reinicia y obtiene lista de casillas
        List<CasillaDTO> mapaDto = casillaService.reiniciarMapa();

        // 2) Elige el tipo de unidad (p. ej. TANQUE)
        Unidad.TipoUnidad tipo = Unidad.TipoUnidad.TANQUE;

        // 3) Genera tropas de jugador1 en esquina superior izquierda
        MyLinkedList<UnidadDTO> j1 = unidadService.generarUnidadesEnRegion(
            tropasPorJugador, tipo, "jugador1",
            0, 2,   // filas 0..2
            0, 2    // columnas 0..2
        );

        // 4) Genera tropas de jugador2 en esquina inferior derecha
        MyLinkedList<UnidadDTO> j2 = unidadService.generarUnidadesEnRegion(
            tropasPorJugador, tipo, "jugador2",
            9, 11,  // filas 9..11
            9, 11   // columnas 9..11
        );

        // 5) Combina resultado en una lista Java normal
        List<UnidadDTO> todas = new ArrayList<>();
        j1.forEach(todas::add);
        j2.forEach(todas::add);

        // 6) Construye y devuelve el DTO
        return new GameStateDTO(mapaDto, todas);
    }

}
