package fr.eni.projetencheres.bll;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.dto.CreerVenteDto;

import java.util.List;

public interface ArticleService {

   //Trouver un article par id
   ArticleVendu getArticleVendu(int noArticle);

   //Ajouter un article
   ArticleVendu insertArticle(CreerVenteDto venteDto, int idVendeur);

   //Lister les articles
   List<ArticleVendu> findAllArticleVenduByVendeur(Integer noUtilisateur);
   //Lister tous les articles
   List<ArticleVendu> findAllArticleVendu();

}
