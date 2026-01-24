package fr.eni.projetencheres.bo;

import fr.eni.projetencheres.enumeration.EncheresAffichage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleVendu {
    private int noArticle;
    private String nomArticle;
    private String description;
    private LocalDateTime dateDebutEncheres;
    private LocalDateTime dateFinEncheres;
    private Integer miseAPrix;
    private Integer prixVente;
    private int etatVente;

    private EncheresAffichage encheresAffichage;

    private Utilisateur vendeur;
    //private Retrait retrait;
    private Categorie categorie;
    private List<Enchere> encheres;
    private Enchere enchere;

    private Retrait lieuRetrait;

    public ArticleVendu(){
        this.encheres=new ArrayList<>();
    }

    public ArticleVendu(int noArticle, String nomArticle, String description,
                        LocalDateTime dateDebutEncheres, LocalDateTime dateFinEncheres, Integer miseAPrix, Integer prixVente, int etatVente){
        this();
        this.noArticle=noArticle;
        this.nomArticle=nomArticle;
        this.description=description;
        this.dateDebutEncheres=dateDebutEncheres;
        this.dateFinEncheres=dateFinEncheres;
        this.miseAPrix=miseAPrix;
        this.prixVente=prixVente;
        this.etatVente=etatVente;
    }

    public ArticleVendu(int noArticle, String nomArticle, String description, LocalDateTime dateDebutEncheres,
                        LocalDateTime dateFinEncheres, Integer miseAPrix, Integer prixVente, int etatVente, Utilisateur vendeur,
                        Categorie categorie){
        this(noArticle, nomArticle, description, dateDebutEncheres, dateFinEncheres, miseAPrix, prixVente, etatVente);
        this.vendeur=vendeur;
        this.categorie=categorie;
    }

    public int getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(int noArticle) {
        this.noArticle = noArticle;
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

    public LocalDateTime getDateDebutEncheres() {
        return dateDebutEncheres;
    }

    public void setDateDebutEncheres(LocalDateTime dateDebutEncheres) {
        this.dateDebutEncheres = dateDebutEncheres;
    }

    public LocalDateTime getDateFinEncheres() {
        return dateFinEncheres;
    }

    public void setDateFinEncheres(LocalDateTime dateFinEncheres) {
        this.dateFinEncheres = dateFinEncheres;
    }

    public Integer getMiseAPrix() {
        return miseAPrix;
    }

    public void setMiseAPrix(Integer miseAPrix) {
        this.miseAPrix = miseAPrix;
    }

    public Integer getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(Integer prixVente) {
        this.prixVente = prixVente;
    }

    public int getEtatVente() {
        return etatVente;
    }

    public void setEtatVente(int etatVente) {
        this.etatVente = etatVente;
    }

    public Utilisateur getVendeur() {
        return vendeur;
    }

    public void setVendeur(Utilisateur vendeur) {
        this.vendeur = vendeur;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<Enchere> getEncheres() {
        return encheres;
    }

    public void setEncheres(List<Enchere> encheres) {
        this.encheres = encheres;
    }

    public Retrait getLieuRetrait() {
        return lieuRetrait;
    }

    public void setLieuRetrait(Retrait lieuRetrait) {
        this.lieuRetrait = lieuRetrait;
    }

    public EncheresAffichage getEncheresAffichage() {
        return encheresAffichage;
    }

    public void setEncheresAffichage(EncheresAffichage encheresAffichage) {
        this.encheresAffichage = encheresAffichage;
    }

    public Enchere getEnchere() {
        return enchere;
    }

    public void setEnchere(Enchere enchere) {
        this.enchere = enchere;
    }
}