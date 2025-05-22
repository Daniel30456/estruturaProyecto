package co.edu.unbosque.juegoestructuraback.controller;

import co.edu.unbosque.juegoestructuraback.dto.GameStateDTO;
import co.edu.unbosque.juegoestructuraback.service.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "http://localhost:8080")  // Ajusta esto al puerto/origen de tu frontend
public class GameController {

    @Autowired
    private GameService gameService;

    /**
     * Inicia una nueva partida con X tropas por jugador.
     * GET /api/game/iniciar?tropas=5
     */
    @GetMapping("/iniciar")
    public ResponseEntity<GameStateDTO> iniciar(
            @RequestParam(name = "tropas", defaultValue = "5") int tropasPorJugador) {
        GameStateDTO estado = gameService.iniciarJuego(tropasPorJugador);
        return ResponseEntity.ok(estado);
    }

    /**
     * Devuelve el estado actual sin reiniciar (útil para refrescar).
     * GET /api/game/estado
     */
    @GetMapping("/estado")
    public ResponseEntity<GameStateDTO> estado() {
        GameStateDTO estado = gameService.getEstadoActual();
        return ResponseEntity.ok(estado);
    }

    /**
     * Avanza al siguiente turno. 
     * POST /api/game/next-turn
     * Body: el GameStateDTO actual en JSON.
     */
    @PostMapping("/next-turn")
    public ResponseEntity<GameStateDTO> nextTurn(@RequestBody GameStateDTO prevState) {
        GameStateDTO siguiente = gameService.nextTurn(prevState);
        return ResponseEntity.ok(siguiente);
    }
    
    @PostMapping("/move")
    public ResponseEntity<GameStateDTO> move(
            @RequestParam Long unidadId,
            @RequestParam int x,
            @RequestParam int y,
            @RequestBody GameStateDTO estadoActual) {

        GameStateDTO siguiente = gameService.moveUnidad(unidadId, x, y, estadoActual);
        return ResponseEntity.ok(siguiente);
    }
    
 // En GameController.java

    @PostMapping("/attack")
    public ResponseEntity<GameStateDTO> attack(
            @RequestParam Long attackerId,
            @RequestParam Long defenderId,
            @RequestBody GameStateDTO state) {

        GameStateDTO next = gameService.attackUnidad(attackerId, defenderId, state);
        return ResponseEntity.ok(next);
    }


}
