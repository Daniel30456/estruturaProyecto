package co.edu.unbosque.juegoestructuraback.controller;

import co.edu.unbosque.juegoestructuraback.dto.UnidadDTO;
import co.edu.unbosque.juegoestructuraback.service.UnidadService;
import co.edu.unbosque.juegoestructuraback.model.Unidad;
import co.edu.unbosque.util.structure.linkedlist.MyLinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.Point;
import java.util.*;

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

    @GetMapping("/mover")
    public List<Point> mover(
            @RequestParam Long id,
            @RequestParam int x,
            @RequestParam int y) {

        MyLinkedList<Point> rutaLinked = unidadService.moverUnidad(id, x, y);
        List<Point> ruta = new ArrayList<Point>(rutaLinked.size());
        for (int i = 0; i < rutaLinked.size(); i++) {
            ruta.add(rutaLinked.get(i).getInfo());
        }
        return ruta;	
    }
}