package it.epicode.exam_epicode_be_s7.prenotazioni;

import it.epicode.exam_epicode_be_s7.auth.AppUser;
import it.epicode.exam_epicode_be_s7.eventi.Evento;
import it.epicode.exam_epicode_be_s7.eventi.EventoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final EventoRepository eventoRepository;

    public PrenotazioneResponse prenotaEvento(Long eventoId) {
        AppUser currentUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));

        if (evento.getNumeroPostiDisponibili() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Posti non disponibili per questo evento");
        }

        boolean alreadyBooked = prenotazioneRepository.existsByEventoAndUtente(evento, currentUser);
        if (alreadyBooked) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hai già prenotato questo evento");
        }

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setEvento(evento);
        prenotazione.setUtente(currentUser);
        prenotazioneRepository.save(prenotazione);

        evento.setNumeroPostiDisponibili(evento.getNumeroPostiDisponibili() - 1);
        eventoRepository.save(evento);

        PrenotazioneResponse response = new PrenotazioneResponse();
        response.setPrenotazioneId(prenotazione.getId());
        response.setEventoId(evento.getId());
        response.setTitoloEvento(evento.getTitolo());
        response.setDescrizioneEvento(evento.getDescrizione());
        response.setDataEvento(evento.getData());
        response.setLuogoEvento(evento.getLuogo());

        return response;
    }

    public List<PrenotazioneResponse> leMiePrenotazioni() {
        AppUser currentUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return prenotazioneRepository.findByUtente(currentUser)
                .stream()
                .map(prenotazione -> {
                    Evento evento = prenotazione.getEvento();
                    PrenotazioneResponse dto = new PrenotazioneResponse();
                    dto.setPrenotazioneId(prenotazione.getId());
                    dto.setEventoId(evento.getId());
                    dto.setTitoloEvento(evento.getTitolo());
                    dto.setDescrizioneEvento(evento.getDescrizione());
                    dto.setDataEvento(evento.getData());
                    dto.setLuogoEvento(evento.getLuogo());
                    return dto;
                })
                .toList();
    }

    public void annullaPrenotazione(Long prenotazioneId) {
        AppUser currentUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Prenotazione prenotazione = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new EntityNotFoundException("Prenotazione non trovata"));

        if (!prenotazione.getUtente().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Puoi annullare solo le tue prenotazioni");
        }

        Evento evento = prenotazione.getEvento();
        evento.setNumeroPostiDisponibili(evento.getNumeroPostiDisponibili() + 1);
        eventoRepository.save(evento);

        prenotazioneRepository.delete(prenotazione);
    }
}


