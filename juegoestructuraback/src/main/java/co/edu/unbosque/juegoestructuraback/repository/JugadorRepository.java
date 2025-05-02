package co.edu.unbosque.juegoestructuraback.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.juegoestructuraback.model.Jugador;

public interface JugadorRepository extends JpaRepository<Jugador, Integer> {
    Optional<Jugador> findByEmail(String email);
    Optional<Jugador> findByTokenVerificacion(String tokenVerificacion);
}
