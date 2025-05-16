package co.edu.unbosque.juegoestructuraback.controller;

import co.edu.unbosque.juegoestructuraback.dto.CasillaDTO;
import co.edu.unbosque.juegoestructuraback.service.CasillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/casilla")
@CrossOrigin(origins = "http://localhost:8080")
public class CasillaController {

    @Autowired
    private CasillaService casillaService;

    
    @GetMapping("/ver")
    public ResponseEntity<List<CasillaDTO>> verMapa() {
        List<CasillaDTO> mapa = casillaService.obtenerMapa();
        return new ResponseEntity<>(mapa, HttpStatus.OK);
    }

    
    @PostMapping("/reiniciar")
    public ResponseEntity<List<CasillaDTO>> reiniciar() {
        List<CasillaDTO> nuevoMapa = casillaService.reiniciarMapa();
        return new ResponseEntity<>(nuevoMapa, HttpStatus.CREATED);
    }
}
