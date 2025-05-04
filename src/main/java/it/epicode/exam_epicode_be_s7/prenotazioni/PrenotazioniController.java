package it.epicode.exam_epicode_be_s7.prenotazioni;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prenotazioni")
@RequiredArgsConstructor
public class PrenotazioniController {

    private final PrenotazioneService prenotazioneService;

    @PostMapping("/{eventoId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    public void prenota(@PathVariable Long eventoId) {
        prenotazioneService.prenotaEvento(eventoId);
    }

    @GetMapping("/mie")
    @PreAuthorize("hasRole('USER')")
    public List<Prenotazione> leMiePrenotazioni() {
        return prenotazioneService.leMiePrenotazioni();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('USER')")
    public void annulla(@PathVariable Long id) {
        prenotazioneService.annullaPrenotazione(id);
    }
}

