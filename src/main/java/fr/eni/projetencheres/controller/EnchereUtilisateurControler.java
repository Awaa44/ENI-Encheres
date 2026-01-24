package fr.eni.projetencheres.controller;

import fr.eni.projetencheres.bll.EncheresService;
import fr.eni.projetencheres.bll.UtilisateurService;
import fr.eni.projetencheres.bll.ArticleService;
import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dto.InscriptionDto;
import fr.eni.projetencheres.dto.ListeEnchereDto;
import fr.eni.projetencheres.dto.ModifierProfilDto;
import fr.eni.projetencheres.exception.EmailDejaUtiliseException;
import fr.eni.projetencheres.exception.PseudoDejaUtiliseException;
import fr.eni.projetencheres.security.CustomUserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class EnchereUtilisateurControler {

    private final Logger logger = LoggerFactory.getLogger(EnchereUtilisateurControler.class);
    private final ArticleService articleService;
    private UtilisateurService utilisateurService;
    private EncheresService encheresService;

    public EnchereUtilisateurControler(UtilisateurService utilisateurService, EncheresService encheresService, ArticleService articleService) {
        this.utilisateurService = utilisateurService;
        this.encheresService = encheresService;
        this.articleService = articleService;
    }
    @GetMapping("/")
    public String root() {
        return "redirect:/accueil";
    }

    @PostMapping("/filtre")
    public String filtreEnchere(
            ListeEnchereDto listeEnchereDto,
            @RequestParam(name = "categorie", required = false) Integer categorieId,
            @RequestParam(name = "recherche", required = false) String recherche,
            @AuthenticationPrincipal CustomUserDetailService userDetailService,
            HttpSession session
    ) {
        Set<ArticleVendu> elemsFiltre = new HashSet<>(encheresService.findAllEncheres());

        // 👉 Nettoyage ancien filtre
        session.removeAttribute("typeFiltreAchat");

        if (recherche != null && !recherche.isBlank()) {
            elemsFiltre = elemsFiltre.stream()
                    .filter(a -> a.getNomArticle().toLowerCase()
                            .contains(recherche.toLowerCase()))
                    .collect(Collectors.toSet());
        }

        if (categorieId != null && categorieId > 0) {
            elemsFiltre = elemsFiltre.stream()
                    .filter(a -> a.getCategorie().getNoCategorie() == (categorieId))
                    .collect(Collectors.toSet());
        }

        if (listeEnchereDto.getFiltresAchat() != null && userDetailService != null)
        {
            Set<ArticleVendu> etatEncheres = new HashSet<>();

            if (listeEnchereDto.getFiltresAchat().contains("mesEncheres")) {
                etatEncheres.addAll(
                        encheresService.findMesEncheres(userDetailService.getNoUtilisateur())
                );
                session.setAttribute("typeFiltreAchat", "mesEncheres");
            }
            if (listeEnchereDto.getFiltresAchat().contains("encheresRemportees")) {
                etatEncheres.addAll(
                        encheresService.findFiltreEncheresRemportees(userDetailService.getNoUtilisateur())
                );
                session.setAttribute("typeFiltreAchat", "encheresRemportees");
            }
            if (listeEnchereDto.getFiltresAchat().contains("enchereOuverte")) {
                etatEncheres.addAll(
                        encheresService.findEncheresOuvertes(userDetailService.getNoUtilisateur())
                );
                session.setAttribute("typeFiltreAchat", "enchereOuverte");
            }

            // Remplacer elemsFiltre par les résultats filtrés
            elemsFiltre = etatEncheres;
        }else {
            // 🔹 Aucun filtre achat → on supprime l’info
            session.removeAttribute("typeFiltreAchat");
        }
        session.setAttribute("articlesFiltre", new ArrayList<>(elemsFiltre));
        session.setAttribute("filtreApplique", true);

        return "redirect:/accueil";
    }

    @GetMapping("accueil")
    public String accueil(Model model, HttpSession session) {

        List<ArticleVendu> sessionFiltres =
                (List<ArticleVendu>) session.getAttribute("articlesFiltre");

        Boolean filtreApplique =
                (Boolean) session.getAttribute("filtreApplique");

        ListeEnchereDto encheresDto = new ListeEnchereDto();

        // ✅ Arrivée initiale (aucun filtre appliqué)
        if (filtreApplique == null) {
            encheresDto.setArticles(encheresService.findAllEncheres());
            // 🔹 Aucun filtre achat → on supprime l’info
            session.removeAttribute("typeFiltreAchat");
        }
        // 🚫 Filtre appliqué mais aucun résultat
        else if (sessionFiltres == null || sessionFiltres.isEmpty()) {
            encheresDto.setArticles(Collections.emptyList());
        }
        // ✅ Filtre avec résultats
        else {
            encheresDto.setArticles(sessionFiltres);
        }

        // nettoyage
        session.removeAttribute("articlesFiltre");
        session.removeAttribute("filtreApplique");

        encheresDto.setCategories(encheresService.selectAllCategories());
        model.addAttribute("encheresDto", encheresDto);

        return "accueil";
    }

    @GetMapping("/inscription")
    public String inscriptionUtilisateur(Model model) {
        if (!model.containsAttribute("inscriptionDto")) {
            model.addAttribute("inscriptionDto", new InscriptionDto());
        }
        return "view-inscription";
    }

    @PostMapping("/inscription")
    public String inscriptionUtilisateur(@Valid @ModelAttribute("inscriptionDto") InscriptionDto inscriptionDto,
                                         BindingResult bindingResult, RedirectAttributes redirectAttributes) {


        //validation du mot de passe avec mot de passe confirmation
        if (!inscriptionDto.getMotDePasse().equals(inscriptionDto.getConfirmMotDePasse())) {
            //indique une erreur sans supprimer les données du formulaire d'inscription
            bindingResult.rejectValue("confirmMotDePasse", null,
                    "Les mots de passe ne correspondent pas");
            return "view-inscription";
        }

        //validation @Bean validation
        if (bindingResult.hasErrors()) {
            logger.warn("Erreur d'inscription de l'utilisateur : {}", inscriptionDto.getPseudo());
            return "view-inscription";
        }

        //inscription de l'utilisateur
        try {
            utilisateurService.createUser(inscriptionDto);
            logger.info("Utilisateur créé avec succès : {}", inscriptionDto.getPseudo());
            //message pour l'utilisateur dans le HTML
            redirectAttributes.addFlashAttribute("succes", "Inscription réalisée avec succès," +
                    " vous pouvez vous connecter");
            return "redirect:/login";

        } catch (PseudoDejaUtiliseException ex) {
            logger.warn("Pseudo déjà utilisé : {}", inscriptionDto.getPseudo());
            bindingResult.rejectValue("pseudo", null, ex.getMessage());
            return "view-inscription";

        } catch (EmailDejaUtiliseException ex) {
            logger.warn("Email déjà utilisé : {}", inscriptionDto.getEmail());
            bindingResult.rejectValue("email", null, ex.getMessage());
            return "view-inscription";

        } catch (Exception ex) {
            logger.error("Erreur technique lors de l'inscription  : {}", inscriptionDto.getPseudo(), ex);
            //en cas d'erreur envoyer un message à l'utilisateur
            bindingResult.reject(null, "Erreur technique lors de l'inscription. Veuillez réessayer");
            return "view-inscription";
        }
    }

    @GetMapping("/login")
    public String connectionParticipant(@RequestParam(value = "error", required = false) String error,
                                        @RequestParam(value = "logout", required = false) String logout,
                                        Model model) {
        //afficher message erreur si authentification échouée
        if (error !=null) {
            model.addAttribute("errorMesage", "Identifiant ou mot de passe incorrect");
        }
        if (logout != null){
            model.addAttribute("logoutMessage", "Vous avez été déconnecté avec succès");
        }
        return "view-login";
    }

    @GetMapping("/profil")
    public String afficherProfil(@AuthenticationPrincipal CustomUserDetailService userDetailService, Model model) {

        int id = userDetailService.getNoUtilisateur();
        Utilisateur idUtilisateur = utilisateurService.findUtilisateurById(id);

        //affichage vue
        model.addAttribute("utilisateur", idUtilisateur);

        return "view-profil";
    }

    @GetMapping("/modifierProfil")
    public String afficherModifierProfil(@AuthenticationPrincipal CustomUserDetailService userDetailService, Model model) {

        int id = userDetailService.getNoUtilisateur();
        Utilisateur utilisateur = utilisateurService.findUtilisateurById(id);

        //pré-remplissage des champs de la page modifier profil
        ModifierProfilDto modifierProfilDto = new ModifierProfilDto();
        modifierProfilDto.setPseudo(utilisateur.getPseudo());
        modifierProfilDto.setNom(utilisateur.getNom());
        modifierProfilDto.setPrenom(utilisateur.getPrenom());
        modifierProfilDto.setEmail(utilisateur.getEmail());
        modifierProfilDto.setTelephone(utilisateur.getTelephone());
        modifierProfilDto.setRue(utilisateur.getRue());
        modifierProfilDto.setCodePostal(utilisateur.getCodePostal());
        modifierProfilDto.setVille(utilisateur.getVille());

        model.addAttribute("modifierProfilDto", modifierProfilDto);
        model.addAttribute("utilisateur", utilisateur);

        return "view-modifierProfil";
    }

    @PostMapping("/modifierProfil")
    public String modifierProfil(@Valid @ModelAttribute("modifierProfilDto") ModifierProfilDto modifierProfilDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal CustomUserDetailService userDetailService, Model model) {

        int id = userDetailService.getNoUtilisateur();

        //validation mot de passe seulement si newMotDePasse renseigné
        if (modifierProfilDto.getNewMotDePasse() != null && !modifierProfilDto.getNewMotDePasse().isEmpty()) {
            //vérifier que nouveau mot de passe est différent de ancien mot de passe
            if (modifierProfilDto.getMotDePasse().equals(modifierProfilDto.getNewMotDePasse())) {
                bindingResult.rejectValue("newMotDePasse", null, "L'ancien et le nouveau mot de " +
                        "passe ne doivent pas être identiques");
            }
            //vérifier longueur nouveau mot de passe
            if (modifierProfilDto.getNewMotDePasse().length() < 8){
                bindingResult.rejectValue("newMotDePasse", null, "Le mot de passe doit " +
                        "faire 8 caractères minimum");
            }
            //vérifier qu'il contient 1 Maj, 1 chiffre, 1 caractère spécial
            if (!modifierProfilDto.getNewMotDePasse().matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@*#$%^&+=!]).*$")) {
                bindingResult.rejectValue("newMotDePasse", null, "Le mot de passe doit " +
                        "contenir 1 majuscule, 1 chiffre et 1 caractère spécial");
            }

            //vérifier que la confirmation est identique au nouveau mot de passe
            if(modifierProfilDto.getConfirmMotDePasse() == null && modifierProfilDto.getConfirmMotDePasse().isEmpty()) {
                bindingResult.rejectValue("confirmMotDePasse", null,"La confirmation " +
                        "du mot de passe doit être renseignée");
            } else if (!modifierProfilDto.getNewMotDePasse().equals(modifierProfilDto.getConfirmMotDePasse())) {
                bindingResult.rejectValue("confirmMotDePasse", null, "Le nouveau  mot de passe " +
                        "et la confirmation doivent être identiques");
            }
        }
        //validation des @Bean contraintes en affichant tous les types d'erreurs
        if (bindingResult.hasErrors()) {
            System.out.println("ERREURS DE VALIDATION DÉTECTÉES :");

            bindingResult.getAllErrors().forEach(error -> {
                if (error instanceof org.springframework.validation.FieldError) {
                    org.springframework.validation.FieldError fieldError = (org.springframework.validation.FieldError) error;
                    System.out.println("Champ : " + fieldError.getField());
                    System.out.println("Valeur rejetée : " + fieldError.getRejectedValue());
                    System.out.println("Message : " + fieldError.getDefaultMessage());
                } else {
                    System.out.println("Erreur globale : " + error.getDefaultMessage());
                }
                System.out.println("----------------------------------------");
            });
            System.out.println("========================================");

            Utilisateur utilisateur = utilisateurService.findUtilisateurById(id);
            model.addAttribute("utilisateur", utilisateur);
            model.addAttribute("modifierProfilDto", modifierProfilDto);
            logger.warn("Erreur modification de l'utilisateur : {}", modifierProfilDto.getPseudo());
            return "view-modifierProfil";
        }

        try {
            utilisateurService.updateUser(id, modifierProfilDto);
            logger.info("Profil modifié avec succès : {}", modifierProfilDto.getPseudo());
            redirectAttributes.addFlashAttribute("succes", "Votre profil a été mis à jour");
            return "redirect:/profil";

        } catch (PseudoDejaUtiliseException ex) {
            logger.warn("Le pseudo existe déjà : {}", modifierProfilDto.getPseudo());
            bindingResult.rejectValue("pseudo", null, ex.getMessage());
            Utilisateur utilisateur = utilisateurService.findUtilisateurById(id);
            model.addAttribute("utilisateur", utilisateur);
            model.addAttribute("modifierProfilDto", modifierProfilDto);
            return "view-modifierProfil";

        } catch (EmailDejaUtiliseException ex) {
            logger.warn("L'email existe déjà : {}", modifierProfilDto.getEmail());
            bindingResult.rejectValue("email", null, ex.getMessage());
            Utilisateur utilisateur = utilisateurService.findUtilisateurById(id);
            model.addAttribute("utilisateur", utilisateur);
            model.addAttribute("modifierProfilDto", modifierProfilDto);
            return "view-modifierProfil";

        } catch (Exception ex) {
            logger.error("Erreur technique lors de l'inscription  : {}", modifierProfilDto.getPseudo(), ex);
            bindingResult.reject(null, "Erreur technique lors de la modification du profil. " +
                    "Veuillez réessayer");
            Utilisateur utilisateur = utilisateurService.findUtilisateurById(id);
            model.addAttribute("utilisateur", utilisateur);
            model.addAttribute("modifierProfilDto", modifierProfilDto);
            return "view-modifierProfil";
        }
    }

    @PostMapping("/supprimerCompte")
        public String supprimerCompte(@AuthenticationPrincipal CustomUserDetailService userDetailService,
                                      HttpServletRequest request,
                                      RedirectAttributes redirectAttributes) {

        int idUser = userDetailService.getNoUtilisateur();



        try {
            utilisateurService.deleteUser(idUser);
            logger.info("Le compte a été supprimé correctement");
            //invalider la session
            request.getSession().invalidate();
            //effacer le contexte de sécurité
            SecurityContextHolder.clearContext();
            redirectAttributes.addFlashAttribute("succesSuppressionCompte",
                    "Le compte a été supprimé correctement");
            return("redirect:/inscription");

        } catch (Exception ex) {
            logger.error("Erreur lors de la suppression du compte", ex);
            throw new RuntimeException("Erreur lors de la suppression", ex);

        }

    }

    @GetMapping("/enchereRemportee/{id}")
    public String enchereRemportee(@PathVariable Integer id, Model model)
    {
        ArticleVendu article = encheresService.findEnchereRemportee(id);
        model.addAttribute("enchereRemportee", article);
        return "view-enchereRemportee";
    }

    @GetMapping("/profilVendeur/{id}")
    public String profilVendeur(@PathVariable Integer id, Model model)
    {
        Utilisateur profilVendeur = utilisateurService.findUtilisateurById(id);
        model.addAttribute("profilVendeur", profilVendeur);
        return "view-profilVendeur";
    }

}
