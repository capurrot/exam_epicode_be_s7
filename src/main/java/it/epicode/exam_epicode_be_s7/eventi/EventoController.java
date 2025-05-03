package it.epicode.exam_epicode_be_s7.eventi;

import it.epicode.exam_epicode_be_s7.commons.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventi")
@RequiredArgsConstructor
public class EventoController {
    private final EventoService eventoService;

    @GetMapping
    public List<EventoResponse> getAllEventi() {
        return eventoService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse createEvento(EventoRequest eventoRequest) {
        return eventoService.saveEvento(eventoRequest);
    }

    @GetMapping("/{id}")
    public EventoResponse getEventoById(Long id) {
        return eventoService.findEventoById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvento(Long id) {
        eventoService.deleteEvento(id);
    }
}
