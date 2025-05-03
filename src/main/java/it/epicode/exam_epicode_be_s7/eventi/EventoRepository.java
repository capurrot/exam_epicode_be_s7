package it.epicode.exam_epicode_be_s7.eventi;


import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}