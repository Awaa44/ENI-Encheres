package fr.eni.projetencheres.exception;

public class UtilisateurNotFound extends RuntimeException {

    //message personnalisé
    public UtilisateurNotFound(String message) {
        super(message);
    }

    //message prédéfini
    public UtilisateurNotFound() {
        super("Utilisateur introuvable");
    }

    //constructeur avec message et cause
    public UtilisateurNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
