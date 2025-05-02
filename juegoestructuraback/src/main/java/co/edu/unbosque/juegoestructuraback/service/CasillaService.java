package co.edu.unbosque.juegoestructuraback.service;

import co.edu.unbosque.juegoestructuraback.dto.CasillaDTO;
import co.edu.unbosque.juegoestructuraback.model.Casilla;
import co.edu.unbosque.juegoestructuraback.repository.CasillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CasillaService {

    @Autowired
    private CasillaRepository casillaRepository;

    private static final String[] TIPOS = {"llano", "agua", "bosque", "ciudad"};

    public List<CasillaDTO> generarMapa(int filas, int columnas) {
        List<CasillaDTO> mapaDTO = new ArrayList<>();

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                String tipo = TIPOS[new Random().nextInt(TIPOS.length)];
                Casilla casilla = new Casilla(i, j, tipo);
                casillaRepository.save(casilla); 

                mapaDTO.add(new CasillaDTO(i, j, tipo));
            }
        }

        return mapaDTO;
    }

    public List<CasillaDTO> obtenerMapaGuardado() {
        return casillaRepository.findAll().stream()
                .map(c -> new CasillaDTO(c.getX(), c.getY(), c.getTipo()))
                .toList();
    }
}
