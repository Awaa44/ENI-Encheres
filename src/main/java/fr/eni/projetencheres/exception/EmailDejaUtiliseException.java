package fr.eni.projetencheres.exception;

public class EmailDejaUtiliseException extends RuntimeException {
    public EmailDejaUtiliseException(String message) {
        super(message);
    }

    public EmailDejaUtiliseException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailDejaUtiliseException() {
        super("Cet email est déjà utilisé");
    }
}
