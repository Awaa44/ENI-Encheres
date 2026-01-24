package fr.eni.projetencheres.dal;

import fr.eni.projetencheres.bo.ArticleVendu;

import java.sql.SQLException;
import java.util.List;

public interface ArticleRepository {


    ArticleVendu insertArticle(ArticleVendu articleVendu);

    //Trouver un article par id
    ArticleVendu selectArticle(int noArticle);

    //Lister les articles par vendeur
    List<ArticleVendu> findAllArticleVenduByVendeur(Integer noUtilisateur);

    //Lister tous les articles
    List<ArticleVendu> findAllArticleVendu();
}
