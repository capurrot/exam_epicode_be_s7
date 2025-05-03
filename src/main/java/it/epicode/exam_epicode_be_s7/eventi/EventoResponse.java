package it.epicode.exam_epicode_be_s7.eventi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoResponse {
    private Long id;
    private String titolo;
    private String descrizione;
    private String data;
    private String luogo;
    private int numeroPostiDisponibili;
}
