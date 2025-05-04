package it.epicode.exam_epicode_be_s7.prenotazioni;

import it.epicode.exam_epicode_be_s7.auth.AppUser;
import it.epicode.exam_epicode_be_s7.eventi.Evento;
import it.epicode.exam_epicode_be_s7.eventi.EventoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final EventoRepository eventoRepository;

    public void prenotaEvento(Long eventoId) {
        AppUser currentUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));

        if (evento.getNumeroPostiDisponibili() <= 0) {
            throw new IllegalStateException("Nessun posto disponibile per questo evento");
        }

        // Verifica se già prenotato
        boolean alreadyBooked = prenotazioneRepository.existsByEventoAndUtente(evento, currentUser);
        if (alreadyBooked) {
            throw new IllegalStateException("Hai già prenotato questo evento");
        }

        // Salva la prenotazione
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setEvento(evento);
        prenotazione.setUtente(currentUser);
        prenotazioneRepository.save(prenotazione);

        // Decrementa i posti
        evento.setNumeroPostiDisponibili(evento.getNumeroPostiDisponibili() - 1);
        eventoRepository.save(evento);
    }

    public List<Prenotazione> leMiePrenotazioni() {
        AppUser currentUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return prenotazioneRepository.findByUtente(currentUser);
    }

    public void annullaPrenotazione(Long prenotazioneId) {
        AppUser currentUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Prenotazione prenotazione = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new EntityNotFoundException("Prenotazione non trovata"));

        if (!prenotazione.getUtente().getId().equals(currentUser.getId())) {
            throw new SecurityException("Puoi annullare solo le tue prenotazioni");
        }

        Evento evento = prenotazione.getEvento();
        evento.setNumeroPostiDisponibili(evento.getNumeroPostiDisponibili() + 1);
        eventoRepository.save(evento);

        prenotazioneRepository.delete(prenotazione);
    }
}

