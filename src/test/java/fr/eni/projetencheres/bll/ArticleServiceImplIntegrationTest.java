package fr.eni.projetencheres.bll;
import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Retrait;
import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dto.CreerVenteDto;
import fr.eni.projetencheres.dto.InscriptionDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional  // ✅ Démarre une transaction pour chaque test
@Rollback       // ✅ Annule la transaction après chaque test
public class ArticleServiceImplIntegrationTest {

    @Autowired
    private ArticleService articleService;

    @Autowired UtilisateurService utilisateurService;

    @Autowired EncheresService encheresService;

    //@Autowired RetraitService retraitService;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RetraitService retraitService;

    @Test
    @DisplayName("Test création d'un nouvel article mis en vente avec retrait existant")
    public void addArticle(){
        //Arrange
        InscriptionDto inscriptionDto = new InscriptionDto();
        inscriptionDto.setPseudo("testvendeur");
        inscriptionDto.setNom("Test");
        inscriptionDto.setPrenom("Vendeur");
        inscriptionDto.setEmail("testvendeur@test.com");
        inscriptionDto.setTelephone("0612345678");
        inscriptionDto.setMotDePasse("Test123!");
        inscriptionDto.setConfirmMotDePasse("Test123!");
        inscriptionDto.setRue("15 Rue Victor Hugo");
        inscriptionDto.setCodePostal("44000");
        inscriptionDto.setVille("Nantes");

        Utilisateur vendeur = utilisateurService.createUser(inscriptionDto);
        int vendeurId = vendeur.getNoUtilisateur();

        CreerVenteDto creerVenteDto = new CreerVenteDto();
        creerVenteDto.setNomArticle("canapé");
        creerVenteDto.setDescription("Canapé 3 places");
        creerVenteDto.setDateDebutEnchere(LocalDateTime.of(2025, 12, 28, 2, 25));
        creerVenteDto.setDateFinEnchere(LocalDateTime.of(2025, 12, 31, 6,35));
        creerVenteDto.setMiseAPrix(100);
        creerVenteDto.setNoCategorie(1);
        creerVenteDto.setRue(vendeur.getRue());
        creerVenteDto.setCodePostal(vendeur.getCodePostal());
        creerVenteDto.setVille(vendeur.getVille());
        creerVenteDto.setIdRetraitExistant(vendeur.getIdRetrait());


        //Act
        ArticleVendu article = articleService.insertArticle(creerVenteDto, vendeurId);

        //Assert
        assertNotNull(article);
        assertNotNull(article.getNoArticle());
        assertEquals("canapé", article.getNomArticle());
        assertEquals(100, article.getMiseAPrix());
        assertNotNull(vendeur.getIdRetrait(), "Le vendeur doit avoir un retrait associé");

    }


    @Test
    @DisplayName("Test création d'un nouvel article mis en vente avec nouveau retrait")
    public void addArticleNewRetrait(){
        //Arrange
        InscriptionDto inscriptionDto = new InscriptionDto();
        inscriptionDto.setPseudo("testvendeur");
        inscriptionDto.setNom("Test");
        inscriptionDto.setPrenom("Vendeur");
        inscriptionDto.setEmail("testvendeur@test.com");
        inscriptionDto.setTelephone("0612345678");
        inscriptionDto.setMotDePasse("Test123!");
        inscriptionDto.setConfirmMotDePasse("Test123!");
        inscriptionDto.setRue("15 Rue Victor Hugo");
        inscriptionDto.setCodePostal("44000");
        inscriptionDto.setVille("Nantes");

        Utilisateur vendeur = utilisateurService.createUser(inscriptionDto);
        int vendeurId = vendeur.getNoUtilisateur();

        //récupérer ldi par défaut
        List<Retrait> retraitsAvant = retraitService.findRetraitsByUserId(vendeurId);
        assertEquals(1, retraitsAvant.size(), "Le vendeur doit avoir 1 retrait après inscription");

        int idRetraitParDefaut = retraitsAvant.get(0).getIdRetrait();

        //création vente avec nouvelle adresse
        CreerVenteDto creerVenteDto = new CreerVenteDto();
        creerVenteDto.setNomArticle("canapé");
        creerVenteDto.setDescription("Canapé 3 places");
        creerVenteDto.setDateDebutEnchere(LocalDateTime.of(2025, 12, 28, 6,30));
        creerVenteDto.setDateFinEnchere(LocalDateTime.of(2025, 12, 31, 5,25));
        creerVenteDto.setMiseAPrix(100);
        creerVenteDto.setNoCategorie(1);
        //Retrait avant changement adresse
        creerVenteDto.setIdRetraitExistant(idRetraitParDefaut);

        //nouvelle adresse
        creerVenteDto.setRue("nouvelle rue");
        creerVenteDto.setCodePostal("44600");
        creerVenteDto.setVille("nouvelle ville");

        //Act
        ArticleVendu article = articleService.insertArticle(creerVenteDto, vendeurId);

        //Assert
        assertNotNull(article);
        assertNotNull(article.getNoArticle());
        assertEquals("canapé", article.getNomArticle());
        assertEquals(100, article.getMiseAPrix());

        //vérifier qu'un nouveau retrait est créé
        List<Retrait> retraitsApres= retraitService.findRetraitsByUserId(vendeurId);
        assertEquals(2, retraitsApres.size(), "L'utilisateur a 2 retraits");

        //vérifier que l'article est lié au nouveau retrait
        assertNotNull(article.getLieuRetrait(), "L'article doit avoir un id retrait");
        assertNotEquals(idRetraitParDefaut, article.getLieuRetrait().getIdRetrait(),
                "L'id retrait doit être différent de l'id par défaut");

        //véfifier que le nouveau retrait à la bonne adresse
        assertEquals("nouvelle rue", article.getLieuRetrait().getRue());
        assertEquals("44600", article.getLieuRetrait().getCodePostal());
        assertEquals("nouvelle ville", article.getLieuRetrait().getVille());

    }
}