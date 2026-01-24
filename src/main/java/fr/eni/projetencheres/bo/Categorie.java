package fr.eni.projetencheres.bo;

import java.util.ArrayList;
import java.util.List;

public class Categorie {
    private int noCategorie;
    private String libelle;

    private List<ArticleVendu> articlesVendus;

    public Categorie(){
        this.articlesVendus=new ArrayList<>();
    }

    public Categorie(int noCategorie, String libelle){
        this();
        this.noCategorie=noCategorie;
        this.libelle=libelle;
    }

    public int getNoCategorie() {
        return noCategorie;
    }

    public void setNoCategorie(int noCategorie) {
        this.noCategorie = noCategorie;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<ArticleVendu> getArticlesVendus() {
        return articlesVendus;
    }

    public void setArticlesVendus(List<ArticleVendu> articlesVendus) {
        this.articlesVendus = articlesVendus;
    }
}