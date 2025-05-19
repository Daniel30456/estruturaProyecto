package co.edu.unbosque.juegoestructuraback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.juegoestructuraback.dto.GameStateDTO;
import co.edu.unbosque.juegoestructuraback.service.GameService;

@RestController
@RequestMapping("/api/game")
public class GameController {

	@Autowired
	private GameService gameService;

	/**
	 * Llama a iniciarJuego y devuelve el estado completo: { "mapa": [
	 * ...CasillaDTO... ], "unidades": [ ...UnidadDTO... ] }
	 */
	@GetMapping("/iniciar")
	public ResponseEntity<GameStateDTO> iniciar(
			@RequestParam(name = "tropas", defaultValue = "5") int tropasPorJugador) {
		GameStateDTO estado = gameService.iniciarJuego(tropasPorJugador);
		return ResponseEntity.ok(estado);
	}
	@GetMapping("/estado")
	public ResponseEntity<GameStateDTO> estado() {
	    return ResponseEntity.ok(gameService.getEstadoActual());
	}
}
