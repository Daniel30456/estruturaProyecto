package co.edu.unbosque.juegoestructuraback.service;

import co.edu.unbosque.juegoestructuraback.dto.CasillaDTO;
import co.edu.unbosque.juegoestructuraback.model.Casilla;
import co.edu.unbosque.juegoestructuraback.model.Casilla.TipoTerreno;
import co.edu.unbosque.juegoestructuraback.repository.CasillaRepository;
import co.edu.unbosque.juegoestructuraback.repository.UnidadRepository;  // << importar
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;        // << importar

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CasillaService {

    private final PredefinedMapProvider mapProvider;
    private final CasillaRepository casillaRepo;
    private final UnidadRepository unidadRepo;                       // << inyectar

    public CasillaService(PredefinedMapProvider mapProvider,
                         CasillaRepository casillaRepo,
                         UnidadRepository unidadRepo) {
        this.mapProvider = mapProvider;
        this.casillaRepo = casillaRepo;
        this.unidadRepo = unidadRepo;                              // << asignar
    }

    /**
     * Limpia la BBDD (unidades primero, luego casillas),
     * persiste un mapa escogido de los JSON y devuelve el
     * nuevo mapa en forma de DTOs.
     */
    @Transactional
    public List<CasillaDTO> reiniciarMapa() {
        // 1) elimina todas las unidades para no violar la FK
        unidadRepo.deleteAllInBatch();

        // 2) elimina todas las casillas
        casillaRepo.deleteAllInBatch();

        // 3) genera el template en memoria
        int[][] template = mapProvider.getRandomTemplate();
        List<Casilla> entidades = new ArrayList<>(template.length * template[0].length);
        for (int x = 0; x < template.length; x++) {
            for (int y = 0; y < template[x].length; y++) {
                TipoTerreno tipo = TipoTerreno.values()[template[x][y]];
                entidades.add(new Casilla(x, y, tipo));
            }
        }

        // 4) guarda todo en batch
        casillaRepo.saveAll(entidades);

        // 5) convierte a DTOs sin necesidad de volver a consultar BD
        return entidades.stream()
                .map(c -> new CasillaDTO(
                    c.getCasillaId(),
                    c.getX(),
                    c.getY(),
                    c.getTipo().name(),
                    c.getPropietario()
                ))
                .collect(Collectors.toList());
        }

    /**
     * Obtiene el mapa actual o lo reinicia si está vacío.
     */
    public List<CasillaDTO> obtenerMapa() {
        List<Casilla> entidades = casillaRepo.findAll();
        if (entidades.isEmpty()) {
            return reiniciarMapa();
        }
        return entidades.stream()
                .map(c -> new CasillaDTO(
                    c.getCasillaId(),
                    c.getX(),
                    c.getY(),
                    c.getTipo().name(),
                    c.getPropietario()
                ))
                .collect(Collectors.toList());
    }
}
