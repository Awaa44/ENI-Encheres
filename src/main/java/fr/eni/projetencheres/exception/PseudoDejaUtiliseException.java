package fr.eni.projetencheres.exception;

public class PseudoDejaUtiliseException extends RuntimeException {
    public PseudoDejaUtiliseException(String message) {
        super(message);
    }

    public PseudoDejaUtiliseException(String message, Throwable cause) {
        super(message, cause);
    }

    public PseudoDejaUtiliseException() {
        super("Ce pseudo est déjà utilisé");
    }
}
