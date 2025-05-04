package it.epicode.exam_epicode_be_s7.eventi;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoRequest {

    @NotBlank(message = "Il titolo non può essere vuoto")
    private String titolo;

    @NotBlank(message = "La descrizione non può essere vuota")
    private String descrizione;

    @NotNull(message = "La data è obbligatoria")
    @Future(message = "La data deve essere futura")
    private LocalDate data;

    @NotBlank(message = "Il luogo non può essere vuoto")
    private String luogo;

    @Min(value = 0, message = "Il numero di posti disponibili non può essere negativo")
    private int numeroPostiDisponibili;
}

