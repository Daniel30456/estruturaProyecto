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
    private static final int TOTAL = FILAS_FIJAS * COLUMNAS_FIJAS;

    @Autowired
    private CasillaRepository casillaRepository;

    public List<CasillaDTO> obtenerMapa() {
        List<Casilla> guardadas = casillaRepository.findAll();
        if (guardadas.size() != TOTAL) {
            // si no están las 144 casillas, borramos y generamos de nuevo
            casillaRepository.deleteAll();
            guardadas = generarYGuardarMapa();
        }
        return guardadas.stream()
                .map(c -> new CasillaDTO(c.getId(), c.getX(), c.getY(), c.getTipo().name()))
                .collect(Collectors.toList());
    }

    private List<Casilla> generarYGuardarMapa() {
        List<Casilla> nuevaLista = new ArrayList<>();
        Casilla.TipoTerreno[] tipos = Casilla.TipoTerreno.values();
        Random rnd = new Random();

        for (int i = 0; i < FILAS_FIJAS; i++) {
            for (int j = 0; j < COLUMNAS_FIJAS; j++) {
                Casilla.TipoTerreno tipo = tipos[rnd.nextInt(tipos.length)];
                Casilla casilla = new Casilla(i, j, tipo);
                nuevaLista.add(casilla);
            }
        }
        // guardamos todas de golpe (más eficiente)
        return casillaRepository.saveAll(nuevaLista);
    }

    public List<CasillaDTO> reiniciarMapa() {
        casillaRepository.deleteAll();
        return obtenerMapa();
    }
}