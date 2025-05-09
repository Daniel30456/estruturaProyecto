package co.edu.unbosque.juegoestructuraback.service;

import co.edu.unbosque.juegoestructuraback.dto.CasillaDTO;
import co.edu.unbosque.juegoestructuraback.model.Casilla;
import co.edu.unbosque.juegoestructuraback.repository.CasillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CasillaService {

    private static final int FILAS_FIJAS = 12;
    private static final int COLUMNAS_FIJAS = 12;

    @Autowired
    private CasillaRepository casillaRepository;

    /**
     * Genera el mapa una sola vez con tamaño fijo y terrenos aleatorios.
     * Si ya existe un mapa, devuelve el guardado.
     */
    public List<CasillaDTO> obtenerMapa() {
        List<Casilla> casillasGuardadas = casillaRepository.findAll();
        if (!casillasGuardadas.isEmpty()) {
            return casillasGuardadas.stream()
                    .map(c -> new CasillaDTO(c.getId(), c.getX(), c.getY(), c.getTipo().name()))
                    .collect(Collectors.toList());
        }

        List<CasillaDTO> mapaDTO = new ArrayList<>();
        Casilla.TipoTerreno[] tipos = Casilla.TipoTerreno.values();
        Random random = new Random();

        for (int i = 0; i < FILAS_FIJAS; i++) {
            for (int j = 0; j < COLUMNAS_FIJAS; j++) {
                Casilla.TipoTerreno tipo = tipos[random.nextInt(tipos.length)];
                Casilla casilla = new Casilla(i, j, tipo);
                casillaRepository.save(casilla);

                mapaDTO.add(new CasillaDTO(casilla.getId(), casilla.getX(), casilla.getY(), casilla.getTipo().name()));
            }
        }

        return mapaDTO;
    }

    /**
     * Borra el mapa actual y genera uno nuevo con terrenos aleatorios.
     * Este método puede usarse desde un endpoint si se desea regenerar el mapa.
     */
    public List<CasillaDTO> reiniciarMapa() {
        casillaRepository.deleteAll();
        return obtenerMapa(); // Genera automáticamente uno nuevo porque ya no habrá casillas
    }
}
