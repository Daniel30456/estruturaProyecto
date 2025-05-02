package co.edu.unbosque.juegoestructuraback.controller;

import co.edu.unbosque.juegoestructuraback.dto.CasillaDTO;
import co.edu.unbosque.juegoestructuraback.service.CasillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/casilla")
public class CasillaController {

    @Autowired
    private CasillaService mapaService;

    @PostMapping("/generar")
    public ResponseEntity<List<CasillaDTO>> generar(@RequestParam int filas, @RequestParam int columnas) {
        List<CasillaDTO> mapa = mapaService.generarMapa(filas, columnas);
        return new ResponseEntity<>(mapa, HttpStatus.CREATED);
    }

    @GetMapping("/ver")
    public ResponseEntity<List<CasillaDTO>> verMapa() {
        List<CasillaDTO> mapa = mapaService.obtenerMapaGuardado();
        return new ResponseEntity<>(mapa, HttpStatus.OK);
    }
}
