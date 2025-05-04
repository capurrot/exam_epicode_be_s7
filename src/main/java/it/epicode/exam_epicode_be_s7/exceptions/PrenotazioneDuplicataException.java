package it.epicode.exam_epicode_be_s7.exceptions;

public class PrenotazioneDuplicataException extends AppException {
    public PrenotazioneDuplicataException() {
        super("Hai già prenotato questo evento");
    }
}
