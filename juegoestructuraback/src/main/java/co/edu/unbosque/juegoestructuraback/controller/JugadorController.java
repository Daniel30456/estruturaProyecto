package co.edu.unbosque.juegoestructuraback.controller;

import co.edu.unbosque.juegoestructuraback.dto.JugadorDTO;
import co.edu.unbosque.juegoestructuraback.model.Jugador;
import co.edu.unbosque.juegoestructuraback.service.JugadorService;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jugadores")
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;

    @PostMapping("/registro")
    public ResponseEntity<String> registrar(@RequestBody JugadorDTO dto) {
        try {
            jugadorService.registrarJugador(dto);
            return new ResponseEntity<>("Registro exitoso. Revisa tu correo para verificar la cuenta.", HttpStatus.CREATED);
        } catch (MessagingException e) {
            return new ResponseEntity<>("Error al enviar el correo de verificación.", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Error en el registro.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody JugadorDTO dto) {
        try {
            Jugador jugador = jugadorService.login(dto.getEmail(), dto.getContraseña());
            return new ResponseEntity<>("Inicio de sesión exitoso. Bienvenido " + jugador.getNombre(), HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error en el inicio de sesión.", HttpStatus.BAD_REQUEST);
        }
    }

    
    @GetMapping("/verificar")
    public ResponseEntity<String> verificar(@RequestParam String token) {
        boolean verificado = jugadorService.verificarCuenta(token);
        if (verificado) {
            return new ResponseEntity<>("Cuenta verificada con éxito.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Token inválido o expirado.", HttpStatus.NOT_FOUND);
        }
    }
}
