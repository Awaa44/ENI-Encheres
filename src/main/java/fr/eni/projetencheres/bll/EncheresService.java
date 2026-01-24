package fr.eni.projetencheres.bll;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.bo.Enchere;
import fr.eni.projetencheres.dto.DetailVenteDto;

import java.util.ArrayList;
import java.util.List;

public interface EncheresService {
    ArrayList<ArticleVendu> findAllEncheres();
    ArrayList<ArticleVendu> findEncheresOuvertes(int no_utilisateur);
    ArrayList<Categorie> selectAllCategories();
    ArrayList<ArticleVendu> findMesEncheres(int no_utilisateur);
    ArrayList<ArticleVendu> findFiltreEncheresRemportees(int no_utilisateur);
    ArrayList<ArticleVendu> findEncheresByCategorie(int no_categorie);
    ArrayList<ArticleVendu> findEncheresBySearch(String recherche);
    ArticleVendu findVenteRemportee(int idArticle);
    ArticleVendu findEnchereRemportee(int idArticle);

    //ajouter une enchère
    Enchere addEnchere (DetailVenteDto venteDto, int noUtilisateur, int noArticle);

    //trouver enchère par id
    Enchere findEnchereById(int noEnchere);

    //afficher toutes les enchères sur un article
    List<Enchere> findEnchereByArticle(int noArticle);

    //trouver la meilleure enchère
    Enchere findBetterEnchere(DetailVenteDto venteDto, int noArticle);


}
