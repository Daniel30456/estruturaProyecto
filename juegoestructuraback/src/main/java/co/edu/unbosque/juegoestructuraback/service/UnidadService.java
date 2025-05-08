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
                .filter(c -> c.getX() == dto.getX() && c.getY() == dto.getY()).findFirst();

        if (optCasilla.isEmpty()) {
            throw new RuntimeException("No existe la casilla (" + dto.getX() + "," + dto.getY() + ")");
        }

        Casilla casilla = optCasilla.get();
        Unidad unidad = new Unidad(dto.getTipo(), dto.getVida(), dto.getAtaque(), dto.getDefensa(), dto.getAlcance(),
                dto.getMovimiento(), dto.getJugador(), casilla);
        unidadRepository.save(unidad);

        dto.setId(unidad.getId());
        return dto;
    }

    public List<UnidadDTO> listarUnidades() {
        List<UnidadDTO> resultado = new ArrayList<>();

        for (Unidad u : unidadRepository.findAll()) {
            resultado.add(new UnidadDTO(u.getId(), u.getTipo(), u.getVida(), u.getAtaque(), u.getDefensa(),
                    u.getAlcance(), u.getMovimiento(), u.getJugador(), u.getCasilla().getX(), u.getCasilla().getY()));
        }

        return resultado;
    }

    public String moverUnidad(int unidadId, int nuevaX, int nuevaY) {
        Unidad unidad = unidadRepository.findById((long) unidadId) // Convierte unidadId a Long
                .orElseThrow(() -> new RuntimeException("Unidad no encontrada"));

        Casilla nuevaCasilla = casillaRepository.findByXAndY(nuevaX, nuevaY)
                .orElseThrow(() -> new RuntimeException("Casilla no válida"));

        // Verificar si ya hay otra unidad en la nueva casilla
        Optional<Unidad> ocupante = unidadRepository.findByCasilla(nuevaCasilla);
        if (ocupante.isPresent()) {
            return "La casilla está ocupada por otra unidad.";
        }

        // Calcular distancia de Manhattan (simple)
        int distancia = Math.abs(unidad.getCasilla().getX() - nuevaX) + Math.abs(unidad.getCasilla().getY() - nuevaY);
        if (distancia > unidad.getMovimiento()) {
            return "Movimiento fuera de alcance.";
        }

        // Mover unidad
        unidad.setCasilla(nuevaCasilla);
        unidadRepository.save(unidad);

        return "Unidad movida exitosamente a (" + nuevaX + ", " + nuevaY + ")";
    }

    // Método para realizar un ataque entre dos unidades
    public String atacar(Long idAtacante, Long idDefensor) {
        // Buscar las unidades en el repositorio
        Unidad atacante = unidadRepository.findById(idAtacante)
                .orElseThrow(() -> new RuntimeException("Unidad atacante no encontrada"));
        Unidad defensor = unidadRepository.findById(idDefensor)
                .orElseThrow(() -> new RuntimeException("Unidad defensora no encontrada"));

        // Verificar si las unidades son enemigas
        if (!sonEnemigas(atacante, defensor)) {
            return "Las unidades no son enemigas y no pueden combatir.";
        }

        // Calcular el daño
        int daño = calcularDaño(atacante, defensor);
        
        // Restar el daño a la vida de la unidad defensora
        defensor.setVida(defensor.getVida() - daño);

        // Guardar la unidad defensora después del ataque
        unidadRepository.save(defensor);

        // Verificar si la unidad defensora ha sido derrotada
        if (defensor.getVida() <= 0) {
            return "La unidad " + defensor.getId() + " ha sido derrotada.";
        }

        return "El ataque fue exitoso. La unidad " + defensor.getId() + " tiene " + defensor.getVida() + " puntos de vida restantes.";
    }

    // Método para verificar si las unidades son enemigas
    private boolean sonEnemigas(Unidad unidad1, Unidad unidad2) {
        return !unidad1.getJugador().equals(unidad2.getJugador()); // Suponiendo que las unidades de diferentes jugadores son enemigas
    }

    // Método para calcular el daño basado en el ataque y defensa
    private int calcularDaño(Unidad atacante, Unidad defensor) {
        int daño = atacante.getAtaque() - defensor.getDefensa();
        return Math.max(daño, 0); // El daño no puede ser negativo
    }
}
