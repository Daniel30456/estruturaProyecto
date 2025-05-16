package co.edu.unbosque.juegoestructuraback.controller;

import co.edu.unbosque.juegoestructuraback.dto.UnidadDTO;
import co.edu.unbosque.juegoestructuraback.service.UnidadService;
import co.edu.unbosque.juegoestructuraback.model.Unidad;
import co.edu.unbosque.util.structure.linkedlist.MyLinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.Point;

@RestController
@RequestMapping("/unidad")
@CrossOrigin(origins = "http://localhost:8080")
public class UnidadController {

    @Autowired
    private UnidadService unidadService;

    @GetMapping("/listar")
    public ResponseEntity<UnidadDTO[]> listar() {
        MyLinkedList<UnidadDTO> lista = unidadService.listarUnidades();
        UnidadDTO[] array = new UnidadDTO[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            array[i] = lista.get(i).getInfo();
        }
        return ResponseEntity.ok(array);
    }

   
    @PostMapping("/generar")
    public ResponseEntity<UnidadDTO[]> generar(
            @RequestParam int n,
            @RequestParam String tipo,
            @RequestParam String jugador) {
        Unidad.TipoUnidad tu = Unidad.TipoUnidad.valueOf(tipo.toUpperCase());
        MyLinkedList<UnidadDTO> lista = unidadService.generarUnidades(n, tu, jugador);
        UnidadDTO[] array = new UnidadDTO[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            array[i] = lista.get(i).getInfo();
        }
        return ResponseEntity.status(201).body(array);
    }

    @PostMapping("/mover/{id}")
    public ResponseEntity<Point[]> mover(
            @PathVariable Long id,
            @RequestParam int x,
            @RequestParam int y) {
        MyLinkedList<Point> ruta = unidadService.moverUnidad(id, x, y);
        Point[] array = new Point[ruta.size()];
        for (int i = 0; i < ruta.size(); i++) {
            array[i] = ruta.get(i).getInfo();
        }
        return ResponseEntity.ok(array);
    }
}