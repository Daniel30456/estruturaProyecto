package co.edu.unbosque.juegoestructuraback.service;

import co.edu.unbosque.juegoestructuraback.model.Jugador;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoVerificacion(Jugador jugador) throws MessagingException {
        String asunto = "Verifica tu cuenta en el juego";
        String mensaje = "Hola " + jugador.getNombre() + ", haz clic en el siguiente enlace para verificar tu cuenta:\n\n" +
                "http://localhost:8082/jugadores/verificar?token=" + jugador.getTokenVerificacion();

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(jugador.getEmail());
        helper.setSubject(asunto);
        helper.setText(mensaje, false);

        mailSender.send(mimeMessage);
    }
}
