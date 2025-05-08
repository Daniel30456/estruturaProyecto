package co.edu.unbosque.juegoestructuraback.repository;

import co.edu.unbosque.juegoestructuraback.model.Unidad;
import co.edu.unbosque.juegoestructuraback.model.Casilla;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnidadRepository extends JpaRepository<Unidad, Long> {
    Optional<Unidad> findByCasilla(Casilla casilla);
}
