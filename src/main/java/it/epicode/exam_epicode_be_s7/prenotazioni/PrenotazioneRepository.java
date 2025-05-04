package it.epicode.exam_epicode_be_s7.prenotazioni;

import it.epicode.exam_epicode_be_s7.auth.AppUser;
import it.epicode.exam_epicode_be_s7.eventi.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    boolean existsByEventoAndUtente(Evento evento, AppUser utente);

    List<Prenotazione> findByUtente(AppUser utente);
}
