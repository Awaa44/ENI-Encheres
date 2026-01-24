package fr.eni.projetencheres.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class DetailVenteDto {

    private String nomArticle;
    private String description;
    private String libelleCat;
    private Integer meilleureEnchere;
    private Integer miseAPrix;
    private String image;
    private LocalDateTime dateFinEnchere;
    private String rue;
    private String codePostal;
    private String ville;
    private String pseudo;
    private Integer propositionEnchere;

    public DetailVenteDto() {
    }

    public DetailVenteDto(String nomArticle, String description, String libelleCat, Integer meilleureEnchere,
                          Integer miseAPrix, String image, LocalDateTime dateFinEnchere, String rue, String codePostal,
                          String ville, String pseudo, Integer propositionEnchere) {
        this.nomArticle = nomArticle;
        this.description = description;
        this.libelleCat = libelleCat;
        this.meilleureEnchere = meilleureEnchere;
        this.miseAPrix = miseAPrix;
        this.image = image;
        this.dateFinEnchere = dateFinEnchere;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.pseudo = pseudo;
        this.propositionEnchere = propositionEnchere;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DetailVenteDto{");
        sb.append("nomArticle='").append(nomArticle).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", libelleCat='").append(libelleCat).append('\'');
        sb.append(", meilleureEnchere=").append(meilleureEnchere);
        sb.append(", miseAPrix=").append(miseAPrix);
        sb.append(", image='").append(image).append('\'');
        sb.append(", dateFinEnchere=").append(dateFinEnchere);
        sb.append(", rue='").append(rue).append('\'');
        sb.append(", codePostal='").append(codePostal).append('\'');
        sb.append(", ville='").append(ville).append('\'');
        sb.append(", pseudo='").append(pseudo).append('\'');
        sb.append(", propositionEnchere=").append(propositionEnchere);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DetailVenteDto that)) return false;
        return meilleureEnchere == that.meilleureEnchere && miseAPrix == that.miseAPrix && propositionEnchere == that.propositionEnchere && Objects.equals(nomArticle, that.nomArticle) && Objects.equals(description, that.description) && Objects.equals(libelleCat, that.libelleCat) && Objects.equals(image, that.image) && Objects.equals(dateFinEnchere, that.dateFinEnchere) && Objects.equals(rue, that.rue) && Objects.equals(codePostal, that.codePostal) && Objects.equals(ville, that.ville) && Objects.equals(pseudo, that.pseudo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomArticle, description, libelleCat, meilleureEnchere, miseAPrix, image, dateFinEnchere, rue, codePostal, ville, pseudo, propositionEnchere);
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

    public String getLibelleCat() {
        return libelleCat;
    }

    public void setLibelleCat(String libelleCat) {
        this.libelleCat = libelleCat;
    }

    public Integer getMeilleureEnchere() {
        return meilleureEnchere;
    }

    public void setMeilleureEnchere(Integer meilleureEnchere) {
        this.meilleureEnchere = meilleureEnchere;
    }

    public Integer getMiseAPrix() {
        return miseAPrix;
    }

    public void setMiseAPrix(Integer miseAPrix) {
        this.miseAPrix = miseAPrix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Integer getPropositionEnchere() {
        return propositionEnchere;
    }

    public void setPropositionEnchere(Integer propositionEnchere) {
        this.propositionEnchere = propositionEnchere;
    }
}
