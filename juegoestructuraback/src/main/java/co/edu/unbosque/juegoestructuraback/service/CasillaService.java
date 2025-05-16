package co.edu.unbosque.juegoestructuraback.service;

import co.edu.unbosque.juegoestructuraback.dto.CasillaDTO;
import co.edu.unbosque.juegoestructuraback.model.Casilla;
import co.edu.unbosque.juegoestructuraback.repository.CasillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CasillaService {

    private static final int FILAS = 12;
    private static final int COLUMNAS = 12;
    private static final int TOTAL = FILAS * COLUMNAS;

    @Autowired
    private CasillaRepository casillaRepository;

  
    public List<CasillaDTO> obtenerMapa() {
        List<Casilla> casillas = casillaRepository.findAll();
        if (casillas.size() != TOTAL) {
            casillaRepository.deleteAll();
            casillas = generarMapa();
        }
        return casillas.stream()
                .map(c -> new CasillaDTO(c.getCasillaId(), c.getX(), c.getY(), c.getTipo().name()))
                .collect(Collectors.toList());
    }

   
    public List<CasillaDTO> reiniciarMapa() {
        casillaRepository.deleteAll();
        List<Casilla> mapa = generarMapa();
        return mapa.stream()
                .map(c -> new CasillaDTO(c.getCasillaId(), c.getX(), c.getY(), c.getTipo().name()))
                .collect(Collectors.toList());
    }

    
    private List<Casilla> generarMapa() {
        List<Casilla> lista = new ArrayList<>();
        Casilla.TipoTerreno[] tipos = Casilla.TipoTerreno.values();
        Random rnd = new Random();
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                Casilla.TipoTerreno tipo = tipos[rnd.nextInt(tipos.length)];
                Casilla casilla = new Casilla(i, j, tipo);
                lista.add(casilla);
            }
        }
        return casillaRepository.saveAll(lista);
    }
}