package fr.eni.projetencheres.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class ModifierProfilDto {

    @NotBlank(message = "Le pseudo est obligatoire")
    @Size(min=3, max=25, message = "Le pseudo doit contenir entre 3 et 25 caractères")
    @Pattern(regexp = "^[^@]+$",
            message = "Le pseudo ne peut pas contenir le caractère @")
    private String pseudo;

    @NotBlank (message = "Le prénom est obligatoire")
    @Size(min=3, max=50, message = "Le prénom doit contenir entre 3 et 50 caractères")
    private String prenom;

    @NotBlank (message = "Le nom est obligatoire")
    @Size(min=3, max=50, message = "Le nom doit contenir entre 3 et 50 caractères")
    private String nom;

    @NotBlank (message = "L email est obligatoire")
    @Email(message = "Format d'email invalide")
    @Size(max = 100, message = "L email ne peut pas dépasser 100 caracteres")
    private String email;

    @NotBlank (message = "Le telephone est obligatoire")
    @Pattern(regexp = "^0[1-9][0-9]{8}$", message = "Le téléphone doit être au format suivant : ex: 0612345678)")
    private String telephone;

    @NotBlank (message = "La rue est obligatoire")
    @Size(max = 100, message = "La rue ne peut pas dépasser 100 caractères")
    private String rue;

    @NotBlank (message = "Le code postal est obligatoire")
    @Pattern(regexp = "^[0-9]{5}$", message = "Le code postal doit contenir 5 chiffres")
    private String codePostal;

    @NotBlank (message = "La ville est obligatoire")
    private String ville;

    @NotBlank (message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@*#$%^&+=!]).*$",
            message = "Le mot de passe doit contenir au moins 1 majuscule, 1 chiffre et 1 caractère spécial (@*#$%^&+=!)")
    private String motDePasse;

    /*@Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@*#$%^&+=!]).*$",
            message = "Le mot de passe doit contenir au moins 1 majuscule, 1 chiffre et 1 caractère spécial (@*#$%^&+=!)")*/
    private String confirmMotDePasse;

    /*@Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@*#$%^&+=!]).*$",
            message = "Le mot de passe doit contenir au moins 1 majuscule, 1 chiffre et 1 caractère spécial (@*#$%^&+=!)")*/
    private String newMotDePasse;

    public ModifierProfilDto() {
    }

    public ModifierProfilDto(String pseudo, String prenom, String nom, String email, String telephone, String rue,
                             String codePostal, String ville, String motDePasse, String confirmMotDePasse,
                             String newMotDePasse) {
        this.pseudo = pseudo;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.motDePasse = motDePasse;
        this.confirmMotDePasse = confirmMotDePasse;
        this.newMotDePasse = newMotDePasse;
    }


    @Override
    public String toString() {
        return "ModifierProfilDto{" +
                "pseudo='" + pseudo + '\'' +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", rue='" + rue + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ModifierProfilDto that)) return false;
        return Objects.equals(pseudo, that.pseudo) && Objects.equals(prenom, that.prenom) && Objects.equals(nom, that.nom) && Objects.equals(email, that.email) && Objects.equals(telephone, that.telephone) && Objects.equals(rue, that.rue) && Objects.equals(codePostal, that.codePostal) && Objects.equals(ville, that.ville) && Objects.equals(motDePasse, that.motDePasse) && Objects.equals(confirmMotDePasse, that.confirmMotDePasse) && Objects.equals(newMotDePasse, that.newMotDePasse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pseudo, prenom, nom, email, telephone, rue, codePostal, ville, motDePasse, confirmMotDePasse, newMotDePasse);
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getConfirmMotDePasse() {
        return confirmMotDePasse;
    }

    public void setConfirmMotDePasse(String confirmMotDePasse) {
        this.confirmMotDePasse = confirmMotDePasse;
    }

    public String getNewMotDePasse() {
        return newMotDePasse;
    }

    public void setNewMotDePasse(String newMotDePasse) {
        this.newMotDePasse = newMotDePasse;
    }

}
