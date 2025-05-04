package it.epicode.exam_epicode_be_s7.runners;

import com.github.javafaker.Faker;
import it.epicode.exam_epicode_be_s7.auth.AppUser;
import it.epicode.exam_epicode_be_s7.auth.AppUserService;
import it.epicode.exam_epicode_be_s7.eventi.Evento;
import it.epicode.exam_epicode_be_s7.eventi.EventoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Component
public class EventoRunner implements CommandLineRunner {

    private final EventoRepository eventoRepository;
    private final AppUserService appUserService;
    private final Faker faker = new Faker();
    private final Random random = new Random();

    public EventoRunner(EventoRepository eventoRepository, AppUserService appUserService) {
        this.eventoRepository = eventoRepository;
        this.appUserService = appUserService;
    }

    @Override
    public void run(String... args) {
        if (eventoRepository.count() == 0) {
            Optional<AppUser> organizerOpt = appUserService.findByUsername("organizer");

            if (organizerOpt.isPresent()) {
                AppUser organizzatore = organizerOpt.get();

                for (int i = 0; i < 10; i++) {
                    Evento evento = new Evento();
                    evento.setTitolo(faker.book().title());
                    evento.setDescrizione(faker.lorem().sentence(10));
                    Date futureDate = faker.date().future(60, java.util.concurrent.TimeUnit.DAYS);
                    evento.setData(futureDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    evento.setLuogo(faker.country().capital());
                    evento.setNumeroPostiDisponibili(random.nextInt(11));
                    evento.setOrganizzatore(organizzatore);
                    eventoRepository.save(evento);
                }
            } else {
                System.err.println("Organizzatore non trovato. EventoRunner non ha potuto creare eventi.");
            }
        }
    }
}



