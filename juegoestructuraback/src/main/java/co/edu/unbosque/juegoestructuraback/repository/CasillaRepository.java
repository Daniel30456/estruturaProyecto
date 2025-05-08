package co.edu.unbosque.juegoestructuraback.repository;

import co.edu.unbosque.juegoestructuraback.model.Casilla;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CasillaRepository extends JpaRepository<Casilla, Integer> {
    Optional<Casilla> findByXAndY(int x, int y);
}
