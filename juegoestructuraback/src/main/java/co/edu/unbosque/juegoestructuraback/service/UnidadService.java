package co.edu.unbosque.juegoestructuraback.service;

import co.edu.unbosque.juegoestructuraback.dto.UnidadDTO;
import co.edu.unbosque.juegoestructuraback.model.Casilla;
import co.edu.unbosque.juegoestructuraback.model.Unidad;
import co.edu.unbosque.juegoestructuraback.repository.CasillaRepository;
import co.edu.unbosque.juegoestructuraback.repository.UnidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UnidadService {

    @Autowired
    private UnidadRepository unidadRepository;

    @Autowired
    private CasillaRepository casillaRepository;

    public UnidadDTO crearUnidad(UnidadDTO dto) {
        Optional<Casilla> optCasilla = casillaRepository.findAll().stream()
            .filter(c -> c.getX() == dto.getX() && c.getY() == dto.getY())
            .findFirst();

        if (optCasilla.isEmpty()) {
            throw new RuntimeException("No existe la casilla (" + dto.getX() + "," + dto.getY() + ")");
        }

        Casilla casilla = optCasilla.get();
        Unidad unidad = new Unidad(dto.getTipo(), dto.getVida(), dto.getAtaque(), dto.getDefensa(),
                dto.getAlcance(), dto.getMovimiento(), dto.getJugador(), casilla);
        unidadRepository.save(unidad);

        dto.setId(unidad.getId());
        return dto;
    }

    public List<UnidadDTO> listarUnidades() {
        List<UnidadDTO> resultado = new ArrayList<>();

        for (Unidad u : unidadRepository.findAll()) {
            resultado.add(new UnidadDTO(
                u.getId(),
                u.getTipo(),
                u.getVida(),
                u.getAtaque(),
                u.getDefensa(),
                u.getAlcance(),
                u.getMovimiento(),
                u.getJugador(),
                u.getCasilla().getX(),
                u.getCasilla().getY()
            ));
        }

        return resultado;
    }
}
