package fr.eni.projetencheres.controller;

import fr.eni.projetencheres.bll.EncheresService;
import fr.eni.projetencheres.bll.UtilisateurService;
import fr.eni.projetencheres.bll.ArticleService;
import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dto.DetailVenteDto;
import fr.eni.projetencheres.security.CustomUserDetailService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class DetailVenteControlleur {

    private final Logger logger = LoggerFactory.getLogger(DetailVenteControlleur.class);
    private final EncheresService encheresService;
    private ArticleService articleService;
    private UtilisateurService utilisateurService;

    //constructeur
    public DetailVenteControlleur(ArticleService articleService, UtilisateurService utilisateurService, EncheresService encheresService) {
        this.articleService = articleService;
        this.utilisateurService = utilisateurService;
        this.encheresService = encheresService;
    }

    @GetMapping("/all-articles-vendus")
        public String afficherAllArticlesVendus(Model model){

            List<ArticleVendu> articles = articleService.findAllArticleVendu();
            model.addAttribute("articles", articles);

            return "view-allArticlesVendus";
        }


    @GetMapping("/detail_vente/{id}")
    public String detailVente(@PathVariable("id") Integer idArticle,
                              @AuthenticationPrincipal CustomUserDetailService userDetailService,
                              Model model){

        ArticleVendu articleDetail = articleService.getArticleVendu(idArticle);
        model.addAttribute("articleDetail", articleDetail);

        //ajouter l'object detailVenteDto
        model.addAttribute("detailVenteDto", new DetailVenteDto());

        return "view-detailVente";
    }

//    @PostMapping("/detail_vente/{id}")
//    public String detailVente(@Valid @ModelAttribute("detailVenteDto") DetailVenteDto detailVenteDto,
//                              @PathVariable("id") Integer idArticle,
//                              BindingResult bindingResult,
//                              RedirectAttributes redirectAttributes,
//                              @AuthenticationPrincipal CustomUserDetailService userDetailService,
//                              Model model){
//
//        //récupération de l'utilisateur
//        int idUser = userDetailService.getNoUtilisateur();
//        Utilisateur utilisateur = utilisateurService.findUtilisateurById(idUser);
//        model.addAttribute("utilisateur", utilisateur);
//
//        //Récupération de l'article vendu
//        ArticleVendu articleDetail = articleService.getArticleVendu(idArticle);
//        model.addAttribute("articleDetail", articleDetail);
//
//        //vérifier que le montant de l'enchère est supérieur à la mise à prix ou à la meilleure offre ou
//        // au crédit utilisateur
//        if (detailVenteDto.getPropositionEnchere() <= utilisateur.getCredit() ||
//                detailVenteDto.getPropositionEnchere() <= articleDetail.getMiseAPrix() ||
//        detailVenteDto.getPropositionEnchere() <= detailVenteDto.getMeilleureEnchere()){
//            bindingResult.rejectValue("propositionEnchere", null,
//                    "Le montant indiqué est insuffisant");
//            return "view-detailVente";
//        }
//
//        //créer l'enchère
//        try {
//            encheresService.addEnchere(detailVenteDto, idUser, idArticle);
//            logger.info("Enchère soumise avec succès");
//            redirectAttributes.addFlashAttribute("succesPropositionEnchere",
//                    "Votre proposition d'enchère a été soumise avec succès");
//            return "redirect:/detail_vente/" + articleDetail.getNoArticle();
//        } catch (Exception ex){
//            logger.error("Une erreur technique est survenue lors de la soumission");
//            bindingResult.rejectValue(null, "erreur technique");
//            model.addAttribute("detailVenteDto", detailVenteDto);
//            return "view-detailVente";
//        }
//
//    }
}
