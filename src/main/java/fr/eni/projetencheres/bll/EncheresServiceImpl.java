package fr.eni.projetencheres.bll;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.bo.Enchere;
import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dal.CategorieRepository;
import fr.eni.projetencheres.dal.CategorieRepositoryImpl;
import fr.eni.projetencheres.dal.EnchereRepository;
import fr.eni.projetencheres.dto.DetailVenteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EncheresServiceImpl implements EncheresService {

    @Autowired
    EnchereRepository enchereRepository;
    @Autowired
    CategorieRepository categorieRepository;

    @Override
    public ArrayList<ArticleVendu> findAllEncheres()
    {
        ArrayList<ArticleVendu> articles = new ArrayList<>();
        articles.addAll(enchereRepository.findAllEncheres());
        return articles;
    }

    @Override
    public ArrayList<ArticleVendu> findEncheresOuvertes(int no_utilisateur)
    {
        ArrayList<ArticleVendu> articles = new ArrayList<>();
        articles.addAll(enchereRepository.findEncheresOuvertes(no_utilisateur));
        return articles;
    }

    @Override
    public ArrayList<ArticleVendu> findMesEncheres(int no_utilisateur)
    {
        ArrayList<ArticleVendu> articles = new ArrayList<>();
        articles.addAll(enchereRepository.findMesEncheres(no_utilisateur));
        return articles;
    }

    @Override
    public ArrayList<ArticleVendu> findFiltreEncheresRemportees(int no_utilisateur)
    {
        ArrayList<ArticleVendu> articles = new ArrayList<>();
        articles.addAll(enchereRepository.findFiltreEncheresRemportees(no_utilisateur));
        return articles;
    }

    @Override
    public ArrayList<Categorie> selectAllCategories()
    {
        return categorieRepository.selectAllCategories();
    }

    @Override
   public ArrayList<ArticleVendu> findEncheresByCategorie(int no_categorie)
   {
       return enchereRepository.findEncheresByCategorie(no_categorie);
   }

    @Override
    public ArrayList<ArticleVendu> findEncheresBySearch(String recherche)
    {
        return enchereRepository.findEncheresBySearch(recherche);
    }

    @Override
    public ArticleVendu findVenteRemportee(int idArticle)
    {
        return enchereRepository.findVenteRemportee(idArticle);
    }

    @Override
    public ArticleVendu findEnchereRemportee(int idArticle)
    {
        return enchereRepository.findEnchereRemportee(idArticle);
    }

    @Transactional
    @Override
    public Enchere addEnchere(DetailVenteDto venteDto, int noUtilisateur, int noArticle) {

        //Logique métier pour faire une enchère
//        montant_enchere > mise à prix de ArticleVendu
//        montant enchere >= credit utilisateur
//        enchère possible :
//        - à partir de la date de début enchère
//        - jusqu'à la date de fin enchere

        //copier données du DTO vers le BO
        Enchere enchere = new Enchere();
        enchere.setMontantEnchere(venteDto.getPropositionEnchere());
        //récupération de la date de soumission de l'enchère
        enchere.setDateEnchere(LocalDateTime.now());

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNoUtilisateur(noUtilisateur);
        enchere.setUtilisateur(utilisateur);

        ArticleVendu article = new ArticleVendu();
        article.setNoArticle(noArticle);
        enchere.setArticleVendu(article);


//        Pour la logique service de Article Service
//        Prix vente = meilleure offre au moment de la date de fin enchère

        return enchereRepository.addEnchere(enchere);
    }

    @Override
    public Enchere findEnchereById(int noEnchere) {
        return enchereRepository.findEnchereById(noEnchere);
    }

    @Override
    public List<Enchere> findEnchereByArticle(int noArticle) {
        return enchereRepository.findEnchereByArticle(noArticle);
    }

    @Override
    public Enchere findBetterEnchere(DetailVenteDto venteDto, int noArticle) {

        //récupère l'enchère si elle existe déjà
        List<Enchere> montantEncheresActuels = findEnchereByArticle(noArticle);

        Enchere meilleureEnchere = null;

        if(montantEncheresActuels != null && !montantEncheresActuels.isEmpty()){

            int montantMax = 0;
            for (Enchere ench : montantEncheresActuels){
                if (ench.getMontantEnchere() > montantMax){
                    //sauvegarder le montant max
                    montantMax = ench.getMontantEnchere();
                    //sauvegarde l'enchère complète avec la date, l'acheteur, et l'article
                    meilleureEnchere = ench;
                }
            }

            venteDto.setMeilleureEnchere(montantMax);
        }
        return meilleureEnchere;
    }
}
