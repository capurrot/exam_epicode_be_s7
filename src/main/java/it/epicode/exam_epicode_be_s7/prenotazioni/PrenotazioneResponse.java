package it.epicode.exam_epicode_be_s7.prenotazioni;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PrenotazioneResponse {
    private Long prenotazioneId;

    private Long eventoId;
    private String titoloEvento;
    private String descrizioneEvento;
    private LocalDate dataEvento;
    private String luogoEvento;
}

