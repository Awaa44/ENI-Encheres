package fr.eni.projetencheres.bo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Enchere {
    private Integer noEnchere;
    private LocalDateTime dateEnchere;
    private Integer montantEnchere;

    private Utilisateur utilisateur;
    private ArticleVendu articleVendu;

    public Enchere(){}

    public Enchere(Integer idEnchere, LocalDateTime dateEnchere, Integer montantEnchere){
        this.noEnchere = idEnchere;
        this.dateEnchere=dateEnchere;
        this.montantEnchere=montantEnchere;
    }

    public Enchere(LocalDateTime dateEnchere, Integer montantEnchere, Integer idEnchere, Utilisateur utilisateur,
                   ArticleVendu articleVendu){
        this(idEnchere, dateEnchere, montantEnchere);
        this.utilisateur=utilisateur;
        this.articleVendu=articleVendu;
    }

    public LocalDateTime getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(LocalDateTime dateEnchere) {
        this.dateEnchere = dateEnchere;
    }

    public Integer getMontantEnchere() {
        return montantEnchere;
    }

    public void setMontantEnchere(Integer montantEnchere) {
        this.montantEnchere = montantEnchere;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public ArticleVendu getArticleVendu() {
        return articleVendu;
    }

    public void setArticleVendu(ArticleVendu articleVendu) {
        this.articleVendu = articleVendu;
    }

    public Integer getNoEnchere() {
        return noEnchere;
    }

    public void setNoEnchere(Integer noEnchere) {
        this.noEnchere = noEnchere;
    }
}