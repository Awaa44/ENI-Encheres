package fr.eni.projetencheres.bll;

import fr.eni.projetencheres.bo.*;
import fr.eni.projetencheres.dal.ArticleRepository;
import fr.eni.projetencheres.dto.CreerVenteDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepository;
    private RetraitService retraitService;

    public ArticleServiceImpl(ArticleRepository articleRepository, RetraitService retraitService) {
        this.articleRepository = articleRepository;
        this.retraitService = retraitService;
    }

    @Override
    public ArticleVendu getArticleVendu(int noArticle) {
        ArticleVendu articleVendu = articleRepository.selectArticle(noArticle);
        return articleVendu;
    }

    @Override
    public ArticleVendu insertArticle(CreerVenteDto venteDto, int idVendeur){
        //déclaration de la variable retrait
        Retrait retrait;

        //récupère l'adresse existante
        Retrait adresseExistante = retraitService.findRetraitById(venteDto.getIdRetraitExistant());

        //Vérifie si l'utilisateur a modifié l'adresse existante
        boolean adresseModifie =
                !adresseExistante.getRue().equals(venteDto.getRue()) ||
                        !adresseExistante.getCodePostal().equals(venteDto.getCodePostal()) ||
                        !adresseExistante.getVille().equals(venteDto.getVille());
        if (adresseModifie) {
            //créer une nouvelle adresse dans retrait
            retrait = new Retrait();
            retrait.setRue(venteDto.getRue());
            retrait.setCodePostal(venteDto.getCodePostal());
            retrait.setVille(venteDto.getVille());
            retrait = retraitService.createRetrait(retrait);

            //lier le retrait à l'utilisateur
            retraitService.lierRetraitUtilisateur(idVendeur, retrait.getIdRetrait());

        } else {
            //utiliser l'adresse existante
            retrait = adresseExistante;
        }

        //copier données du DTO vers le BO
        ArticleVendu articleVendu = new ArticleVendu();
        articleVendu.setNomArticle(venteDto.getNomArticle());
        articleVendu.setDescription(venteDto.getDescription());
        articleVendu.setMiseAPrix(venteDto.getMiseAPrix());
        articleVendu.setDateDebutEncheres(venteDto.getDateDebutEnchere());
        articleVendu.setDateFinEncheres(venteDto.getDateFinEnchere());

        //catégorie
        Categorie categorie = new Categorie();
        categorie.setNoCategorie(venteDto.getNoCategorie());
        articleVendu.setCategorie(categorie);

        //retrait
        articleVendu.setLieuRetrait(retrait);

        //récupération de l'id depuis le controller
        Utilisateur vendeur = new Utilisateur();
        vendeur.setNoUtilisateur(idVendeur);
        articleVendu.setVendeur(vendeur);

        //valeurs par défaut
        articleVendu.setEtatVente(0);
        articleVendu.setPrixVente(null);

        return articleRepository.insertArticle(articleVendu);
    }

    @Override
    public List<ArticleVendu> findAllArticleVenduByVendeur(Integer noUtilisateur) {
        return articleRepository.findAllArticleVenduByVendeur(noUtilisateur);
    }

    @Override
    public List<ArticleVendu> findAllArticleVendu() {
        return articleRepository.findAllArticleVendu();
    }

}
