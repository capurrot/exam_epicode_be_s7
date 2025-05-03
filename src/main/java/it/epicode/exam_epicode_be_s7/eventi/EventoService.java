package it.epicode.exam_epicode_be_s7.eventi;

import it.epicode.exam_epicode_be_s7.commons.CommonResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class EventoService {
    private final EventoRepository eventoRepository;

    public CommonResponse saveEvento(@Valid EventoRequest request) {
        Evento evento =  new Evento();
        BeanUtils.copyProperties(request, evento);
        eventoRepository.save(evento);
        return new CommonResponse(evento.getId());
    }

    public void deleteEvento(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new EntityNotFoundException("Evento non trovato");
        }
        eventoRepository.deleteById(id);
    }

    public EventoResponse fromEntityToResponse(Evento evento) {
        EventoResponse response = new EventoResponse();
        BeanUtils.copyProperties(evento, response);
        return response;
    }

    public List<EventoResponse> findAll() {
        return eventoRepository.findAll()
                .stream()
                .map(this::fromEntityToResponse)
                .toList();
    }

    public EventoResponse findEventoById(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));

        return fromEntityToResponse(evento);
    }

}
