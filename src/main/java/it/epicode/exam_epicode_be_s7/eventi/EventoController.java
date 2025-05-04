package it.epicode.exam_epicode_be_s7.eventi;

import it.epicode.exam_epicode_be_s7.commons.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventi")
@RequiredArgsConstructor
public class EventoController {
    private final EventoService eventoService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<EventoResponse> getAllEventi() {
        return eventoService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ORGANIZER')")
    public CommonResponse createEvento(EventoRequest eventoRequest) {
        return eventoService.saveEvento(eventoRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public EventoResponse getEventoById(Long id) {
        return eventoService.findEventoById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ORGANIZER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvento(Long id) {
        eventoService.deleteEvento(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ORGANIZER')")
    public EventoResponse updateEvento(@PathVariable Long id, @RequestBody EventoRequest eventoRequest) {
        return eventoService.updateEvento(id, eventoRequest);
    }
}
