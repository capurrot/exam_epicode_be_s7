package it.epicode.exam_epicode_be_s7.prenotazioni;

import it.epicode.exam_epicode_be_s7.auth.AppUser;
import it.epicode.exam_epicode_be_s7.eventi.Evento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prenotazioni")
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private AppUser utente;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;
}
