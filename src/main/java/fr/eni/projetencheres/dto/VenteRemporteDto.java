package fr.eni.projetencheres.dto;

import java.util.Objects;

public class VenteRemporteDto {

    private String nomArticle;
    private String description;
    private Integer montantEnchere;
    private Integer miseAPrix;
    private String image;
    private String rue;
    private String codePostal;
    private String ville;
    private String pseudo;
    private String telephone;

    public VenteRemporteDto() {
    }

    public VenteRemporteDto(String nomArticle, String description, Integer montantEnchere, Integer miseAPrix, String image,
                            String rue, String codePostal, String ville, String pseudo, String telephone) {
        this.nomArticle = nomArticle;
        this.description = description;
        this.montantEnchere = montantEnchere;
        this.miseAPrix = miseAPrix;
        this.image = image;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.pseudo = pseudo;
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("VenteRemporteDto{");
        sb.append("nomArticle='").append(nomArticle).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", montantEnchere=").append(montantEnchere);
        sb.append(", miseAPrix=").append(miseAPrix);
        sb.append(", image='").append(image).append('\'');
        sb.append(", rue='").append(rue).append('\'');
        sb.append(", codePostal='").append(codePostal).append('\'');
        sb.append(", ville='").append(ville).append('\'');
        sb.append(", pseudo='").append(pseudo).append('\'');
        sb.append(", telephone='").append(telephone).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof VenteRemporteDto that)) return false;
        return montantEnchere == that.montantEnchere && miseAPrix == that.miseAPrix && Objects.equals(nomArticle, that.nomArticle) && Objects.equals(description, that.description) && Objects.equals(image, that.image) && Objects.equals(rue, that.rue) && Objects.equals(codePostal, that.codePostal) && Objects.equals(ville, that.ville) && Objects.equals(pseudo, that.pseudo) && Objects.equals(telephone, that.telephone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomArticle, description, montantEnchere, miseAPrix, image, rue, codePostal, ville, pseudo, telephone);
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

    public Integer getMontantEnchere() {
        return montantEnchere;
    }

    public void setMontantEnchere(Integer montantEnchere) {
        this.montantEnchere = montantEnchere;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}

