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
    public ResponseEntity<List<Point>> mover(
            @RequestParam Long id,
            @RequestParam int x,
            @RequestParam int y) {
        // 1) Calcula la ruta ligada
        MyLinkedList<Point> rutaLinked = unidadService.moverUnidad(id, x, y);
        // 2) Pasa de MyLinkedList<Node<Point>> a List<Point>
        List<Point> ruta = new ArrayList<>(rutaLinked.size());
        for (int i = 0; i < rutaLinked.size(); i++) {
            ruta.add(rutaLinked.get(i).getInfo());
        }
        // 3) Devuelve 200 OK con la ruta en JSON
        return ResponseEntity.ok(ruta);
    }
    
    /**
     * GET /unidad/rango?id=<id>
     * Devuelve el listado de puntos accesibles según el atributo movimiento de la unidad.
     */
    @GetMapping("/rango")
    public ResponseEntity<List<Point>> rango(
            @RequestParam Long id) {
        // calcula con tu servicio los puntos accesibles
        MyLinkedList<Point> rangoLinked = unidadService.calcularRango(id);
        List<Point> rango = new ArrayList<>(rangoLinked.size());
        for (int i = 0; i < rangoLinked.size(); i++) {
            rango.add(rangoLinked.get(i).getInfo());
        }
        return ResponseEntity.ok(rango);
    }

}