package co.edu.unbosque.juegoestructuraback.service;

import co.edu.unbosque.juegoestructuraback.dto.JugadorDTO;
import co.edu.unbosque.juegoestructuraback.model.Jugador;
import co.edu.unbosque.juegoestructuraback.repository.JugadorRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class JugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private EmailService emailService;

    public void registrarJugador(JugadorDTO dto) throws MessagingException {
        Jugador jugador = new Jugador();
        jugador.setNombre(dto.getNombre());
        jugador.setEmail(dto.getEmail());
        jugador.setContraseña(dto.getContraseña()); 

        jugador.setVerificado(false);
        jugador.setTokenVerificacion(UUID.randomUUID().toString());

        jugadorRepository.save(jugador);
        System.out.println("Enviando correo a: " + jugador.getEmail());
        emailService.enviarCorreoVerificacion(jugador);
    }

    public Jugador login(String email, String contraseña) {
        Optional<Jugador> jugadorOpt = jugadorRepository.findByEmail(email);
        
        if (jugadorOpt.isPresent()) {
            Jugador jugador = jugadorOpt.get();

            if (jugador.getContraseña().equals(contraseña)) {
                if (jugador.isVerificado()) {
                    return jugador;
                } else {
                    throw new IllegalStateException("Cuenta no verificada.");
                }
            } else {
                throw new IllegalArgumentException("Contraseña incorrecta.");
            }
        } else {
            throw new IllegalArgumentException("Correo no registrado.");
        }
    }

    
    public boolean verificarCuenta(String token) {
        Optional<Jugador> jugadorOptional = jugadorRepository.findByTokenVerificacion(token);
        if (jugadorOptional.isPresent()) {
            Jugador jugador = jugadorOptional.get();
            jugador.setVerificado(true);
            jugador.setTokenVerificacion(null);
            jugadorRepository.save(jugador);
            return true;
        }
        return false;
    }
}
