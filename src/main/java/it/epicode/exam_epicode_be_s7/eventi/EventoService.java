package it.epicode.exam_epicode_be_s7.eventi;

import it.epicode.exam_epicode_be_s7.auth.AppUser;
import it.epicode.exam_epicode_be_s7.commons.CommonResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
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
        AppUser currentUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        evento.setOrganizzatore(currentUser);
        eventoRepository.save(evento);
        return new CommonResponse(evento.getId());
    }

    public void deleteEvento(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));

        AppUser currentUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!evento.getOrganizzatore().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Non puoi eliminare eventi che non hai creato.");
        }

        eventoRepository.delete(evento);
    }

    public EventoResponse updateEvento(Long id, EventoRequest request) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));

        AppUser currentUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!evento.getOrganizzatore().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Non puoi modificare eventi che non hai creato.");
        }

        BeanUtils.copyProperties(request, evento, "id", "organizzatore", "prenotazioni");
        eventoRepository.save(evento);

        return fromEntityToResponse(evento);
    }


    public EventoResponse fromEntityToResponse(Evento evento) {
        EventoResponse response = new EventoResponse();
        BeanUtils.copyProperties(evento, response);

        response.setOrganizzatoreId(evento.getOrganizzatore().getId());
        response.setOrganizzatoreUsername(evento.getOrganizzatore().getUsername());

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
