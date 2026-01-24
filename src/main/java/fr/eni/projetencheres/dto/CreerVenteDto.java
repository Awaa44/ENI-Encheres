package fr.eni.projetencheres.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class CreerVenteDto {

    @NotBlank(message = "Le nom de l'article est obligatoire")
    private String nomArticle;
    @NotBlank(message = "La description de l'article est obligatoire")
    private String description;
    @NotNull(message = "Le prix de départ est obligatoire")
    @Min(value = 1)
    private Integer miseAPrix;
    @NotNull(message="La date est obligatoire")
    private LocalDateTime dateDebutEnchere;
    @NotNull(message="La date est obligatoire")
    private LocalDateTime dateFinEnchere;

    @NotNull(message="Veuillez sélectionner une catégorie")
    @Min(value = 1)
    private Integer noCategorie;
    private String image;

    //option B nouvelle adresse
    @NotBlank (message = "La rue est obligatoire")
    private String rue;
    @NotBlank(message = "Le code postale est obligatoire")
    private String codePostal;
    @NotBlank(message = "La ville est obligatoire")
    private String ville;

    //option A choix d'une adresse existante
    @NotNull(message = "Veuillez sélectionner une adresse de retrait")
    @Min(value = 1, message = "veuillez sélectionner une adresse valide")
    private Integer idRetraitExistant;

    public CreerVenteDto() {
    }

    public CreerVenteDto(String nomArticle, String description, Integer noCategorie, String image, Integer miseAPrix,
                         LocalDateTime dateDebutEnchere, LocalDateTime dateFinEnchere, String rue, String codePostal, String ville, Integer idRetraitExistant) {
        this.nomArticle = nomArticle;
        this.description = description;
        this.noCategorie = noCategorie;
        this.image = image;
        this.miseAPrix = miseAPrix;
        this.dateDebutEnchere = dateDebutEnchere;
        this.dateFinEnchere = dateFinEnchere;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.idRetraitExistant = idRetraitExistant;
    }

    @Override
    public String toString() {
        return "CreerVenteDto{" +
                "nomArticle='" + nomArticle + '\'' +
                ", description='" + description + '\'' +
                ", noCategorie=" + noCategorie +
                ", image='" + image + '\'' +
                ", miseAPrix=" + miseAPrix +
                ", dateDebutEnchere=" + dateDebutEnchere +
                ", dateFinEnchere=" + dateFinEnchere +
                ", rue='" + rue + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CreerVenteDto that)) return false;
        return noCategorie == that.noCategorie && Objects.equals(nomArticle, that.nomArticle) && Objects.equals(description, that.description) && Objects.equals(image, that.image) && Objects.equals(miseAPrix, that.miseAPrix) && Objects.equals(dateDebutEnchere, that.dateDebutEnchere) && Objects.equals(dateFinEnchere, that.dateFinEnchere) && Objects.equals(rue, that.rue) && Objects.equals(codePostal, that.codePostal) && Objects.equals(ville, that.ville);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomArticle, description, noCategorie, image, miseAPrix, dateDebutEnchere, dateFinEnchere, rue, codePostal, ville);
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Integer getNoCategorie() {
        return noCategorie;
    }

    public void setNoCategorie(Integer noCategorie) {
        this.noCategorie = noCategorie;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getMiseAPrix() {
        return miseAPrix;
    }

    public void setMiseAPrix(Integer miseAPrix) {
        this.miseAPrix = miseAPrix;
    }

    public LocalDateTime getDateDebutEnchere() {
        return dateDebutEnchere;
    }

    public void setDateDebutEnchere(LocalDateTime dateDebutEnchere) {
        this.dateDebutEnchere = dateDebutEnchere;
    }

    public LocalDateTime getDateFinEnchere() {
        return dateFinEnchere;
    }

    public void setDateFinEnchere(LocalDateTime dateFinEnchere) {
        this.dateFinEnchere = dateFinEnchere;
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

    public Integer getIdRetraitExistant() {
        return idRetraitExistant;
    }

    public void setIdRetraitExistant(Integer idRetraitExistant) {
        this.idRetraitExistant = idRetraitExistant;
    }
}
