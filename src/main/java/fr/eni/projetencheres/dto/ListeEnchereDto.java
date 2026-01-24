package fr.eni.projetencheres.dto;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.bo.Enchere;

import java.util.List;
import java.util.Objects;

public class ListeEnchereDto {

    List<Categorie> categories;
    List<ArticleVendu> articles;
    List<Enchere> encheres;
    private String pseudo;

    private List<String> filtresAchat;
    private List<String> filtresVente;

    public ListeEnchereDto() {
    }

    public ListeEnchereDto(List<Categorie> categories, List<ArticleVendu> articles,
                           List<Enchere> encheres, String pseudo) {
        this.categories = categories;
        this.articles = articles;
        this.encheres = encheres;
        this.pseudo = pseudo;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ListeEnchereDto{");
        sb.append("categories=").append(categories);
        sb.append(", articles=").append(articles);
        sb.append(", encheres=").append(encheres);
        sb.append(", pseudo='").append(pseudo).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ListeEnchereDto that)) return false;
        return Objects.equals(categories, that.categories) && Objects.equals(articles, that.articles) && Objects.equals(encheres, that.encheres) && Objects.equals(pseudo, that.pseudo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categories, articles, encheres, pseudo);
    }

    public List<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }

    public List<ArticleVendu> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleVendu> articles) {
        this.articles = articles;
    }

    public List<Enchere> getEncheres() {
        return encheres;
    }

    public void setEncheres(List<Enchere> encheres) {
        this.encheres = encheres;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public List<String> getFiltresAchat() {
        return filtresAchat;
    }

    public void setFiltresAchat(List<String> filtresAchat) {
        this.filtresAchat = filtresAchat;
    }

    public List<String> getFiltresVente() {
        return filtresVente;
    }

    public void setFiltresVente(List<String> filtresVente) {
        this.filtresVente = filtresVente;
    }
}
