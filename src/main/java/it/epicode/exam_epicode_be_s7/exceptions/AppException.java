package it.epicode.exam_epicode_be_s7.exceptions;

public abstract class AppException extends RuntimeException {
    public AppException(String message) {
        super(message);
    }
}
