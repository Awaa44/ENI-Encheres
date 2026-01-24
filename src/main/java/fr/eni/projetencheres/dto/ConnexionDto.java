package fr.eni.projetencheres.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class ConnexionDto {

    @NotBlank
    @Size(min = 1, max = 25)
    private String pseudo;

    @NotBlank
    @Size(min = 8, max = 30)
    private String motDePasse;

    public ConnexionDto() {
    }

    public ConnexionDto(String pseudo, String motDePasse) {
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ConnexionDto{");
        sb.append("pseudo='").append(pseudo).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ConnexionDto that)) return false;
        return Objects.equals(pseudo, that.pseudo) && Objects.equals(motDePasse, that.motDePasse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pseudo, motDePasse);
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
