package co.edu.unbosque.juegoestructuraback.service;

import co.edu.unbosque.juegoestructuraback.dto.CasillaDTO;
import co.edu.unbosque.juegoestructuraback.model.Casilla;
import co.edu.unbosque.juegoestructuraback.model.Casilla.TipoTerreno;
import co.edu.unbosque.juegoestructuraback.repository.CasillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CasillaService {

    private final PredefinedMapProvider mapProvider;
    private final CasillaRepository casillaRepo;

    public CasillaService(PredefinedMapProvider mapProvider,
                         CasillaRepository casillaRepo) {
        this.mapProvider = mapProvider;
        this.casillaRepo = casillaRepo;
    }

    /**
     * Limpia la BBDD, persiste un mapa escogido de los JSON
     * y devuelve el nuevo mapa en forma de DTOs.
     */
    public List<CasillaDTO> reiniciarMapa() {
        int[][] template = mapProvider.getRandomTemplate();
        casillaRepo.deleteAll();

        for (int x = 0; x < template.length; x++) {
            for (int y = 0; y < template[x].length; y++) {
                TipoTerreno tipo = TipoTerreno.values()[template[x][y]];
                Casilla entidad = new Casilla(x, y, tipo);
                casillaRepo.save(entidad);
            }
        }
        return obtenerMapa();
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
                c.getTipo().name()
            ))
            .collect(Collectors.toList());
    }
}
