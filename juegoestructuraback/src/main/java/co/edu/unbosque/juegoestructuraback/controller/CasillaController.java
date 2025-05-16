package co.edu.unbosque.juegoestructuraback.controller;

import co.edu.unbosque.juegoestructuraback.dto.CasillaDTO;
import co.edu.unbosque.juegoestructuraback.service.CasillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/casillas")
public class CasillaController {

    private final CasillaService casillaService;

    public CasillaController(CasillaService casillaService) {
        this.casillaService = casillaService;
    }

    /**  
     * Obtiene el mapa actual; si no existe, reinicia uno.  
     */
    @GetMapping
    public List<CasillaDTO> obtenerMapa() {
        return casillaService.obtenerMapa();
    }

    /**  
     * Reinicia la base de datos y devuelve un nuevo mapa.  
     */
    @PostMapping("/reiniciar")
    public List<CasillaDTO> reiniciarMapa() {
        return casillaService.reiniciarMapa();
    }
}
