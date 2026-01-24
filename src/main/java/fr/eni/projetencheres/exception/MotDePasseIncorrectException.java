package fr.eni.projetencheres.exception;

public class MotDePasseIncorrectException extends RuntimeException {
    public MotDePasseIncorrectException(String message) {
        super(message);
    }

    public MotDePasseIncorrectException(String message, Throwable cause) {
        super(message, cause);
    }

    public MotDePasseIncorrectException() {
        super("Le mot de passe actuel est incorrect");
    }
}

