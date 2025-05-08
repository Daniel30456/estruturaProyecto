package co.edu.unbosque.juegoestructuraback.controller;

import co.edu.unbosque.juegoestructuraback.dto.UnidadDTO;
import co.edu.unbosque.juegoestructuraback.service.UnidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unidad")
public class UnidadController {

    @Autowired
    private UnidadService unidadService;

    @PostMapping("/crear")
    public ResponseEntity<UnidadDTO> crearUnidad(@RequestBody UnidadDTO dto) {
        UnidadDTO creada = unidadService.crearUnidad(dto);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UnidadDTO>> listarUnidades() {
        return ResponseEntity.ok(unidadService.listarUnidades());
    }
}
