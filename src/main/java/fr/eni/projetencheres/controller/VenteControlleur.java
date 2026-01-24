package fr.eni.projetencheres.controller;

import fr.eni.projetencheres.bll.EncheresService;
import fr.eni.projetencheres.bll.RetraitService;
import fr.eni.projetencheres.bll.ArticleService;
import fr.eni.projetencheres.bll.UtilisateurService;
import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.bo.Retrait;
import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dto.CreerVenteDto;
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
public class VenteControlleur {
    final Logger logger = LoggerFactory.getLogger(VenteControlleur.class);
    private final ArticleService articleService;
    private final EncheresService encheresService;
    private final RetraitService retraitService;
    private final UtilisateurService utilisateurService;

    public VenteControlleur(ArticleService articleService, EncheresService encheresService, RetraitService retraitService, UtilisateurService utilisateurService) {
        this.articleService = articleService;
        this.encheresService = encheresService;
        this.retraitService = retraitService;
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/nouvelle_vente")
    public String newVente(Model model, @AuthenticationPrincipal CustomUserDetailService userDetailService) {
        if (!model.containsAttribute("creerVenteDto")) {
            model.addAttribute("creerVenteDto", new CreerVenteDto());
        }

        //charger adresse par défaut de l'utiisateur
        int id = userDetailService.getNoUtilisateur();
        //récupérer l'utilisateur avec son adresse par défaut
        Utilisateur utilisateur = utilisateurService.findUtilisateurById(id);

        //pré rempli l'adresse par défaut de l'utilisateur
        CreerVenteDto dto = new CreerVenteDto();
        dto.setRue(utilisateur.getRue());
        dto.setCodePostal(utilisateur.getCodePostal());
        dto.setVille(utilisateur.getVille());

        //charger la liste des retraits de l'utilisateur
        List<Retrait> retraits = retraitService.findRetraitsByUserId(id);
        //sélectionne dans la liste l'adresse par défaut de l'utilisateur
        for (Retrait retrait : retraits) {
            if (retrait.getRue().equals(utilisateur.getRue()) &&
                    retrait.getCodePostal().equals(utilisateur.getCodePostal()) &&
                    retrait.getVille().equals(utilisateur.getVille())) {
                dto.setIdRetraitExistant(retrait.getIdRetrait());
                break;
            }
        }

        model.addAttribute("creerVenteDto", dto);
        model.addAttribute("retraits", retraits);

        //charger la liste de catégories quand le service categorie est fait
        List<Categorie> categories = encheresService.selectAllCategories();
        model.addAttribute("categories", categories);

        return "view-nouvelleVente";
    }

    @PostMapping("/nouvelle_vente")
    public String newVente(@Valid @ModelAttribute("creerVenteDto") CreerVenteDto dto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Model model,
                           @AuthenticationPrincipal CustomUserDetailService userDetailService){

        int id = userDetailService.getNoUtilisateur();
        List<Categorie> categories = encheresService.selectAllCategories();
        List<Retrait> retraits  = retraitService.findRetraitsByUserId(id);

        //binding Result
        if(bindingResult.hasErrors()) {
            logger.warn("Erreur de création de vente");
            model.addAttribute("creerVenteDto", dto);
            model.addAttribute("categories", categories);
            model.addAttribute("retraits", retraits);
            return "view-nouvelleVente";
        }

        //création de la vente
        try {
            ArticleVendu article = articleService.insertArticle(dto, id);
            logger.info("Vente créée avec succès");
            redirectAttributes.addFlashAttribute("succesCreationVente",
                    "Votre vente a été créée avec succès");
            return "redirect:/detail_vente/" + article.getNoArticle();
            //return "redirect:/accueil";
        } catch (Exception e) {
            logger.error("La création de la vente a échoué");
            bindingResult.rejectValue(null, "erreur technique");
            model.addAttribute("creerVenteDto", dto);
            model.addAttribute("categories", categories);
            model.addAttribute("retraits", retraits);
            return "view-nouvelleVente";
        }
    }

    @GetMapping("/article/venteRemporte/{id}")
    public String venteRemportee(@PathVariable Integer id, Model model)
    {
        ArticleVendu article = encheresService.findVenteRemportee(id);
        model.addAttribute("venteRemportee", article);
        return "view-venteRemportee";
    }
}
