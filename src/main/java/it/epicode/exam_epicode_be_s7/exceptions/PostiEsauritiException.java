package it.epicode.exam_epicode_be_s7.exceptions;

public class PostiEsauritiException extends AppException {
  public PostiEsauritiException() {
    super("Posti non disponibili per questo evento");
  }
}
