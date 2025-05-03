package it.epicode.exam_epicode_be_s7.eventi;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoRequest {
    @NotBlank(message = "Il titolo non può essere vuoto")
    private String titolo;
    @NotBlank(message = "La descrizione non può essere vuota")
    private String descrizione;
    @NotBlank(message = "La data non può essere vuota")
    private String data;
    @NotBlank(message = "Il luogo non può essere vuoto")
    private String luogo;
    @NotBlank(message = "Il numero di posti disponibili non può essere vuoto")
    private int numeroPostiDisponibili;
}
