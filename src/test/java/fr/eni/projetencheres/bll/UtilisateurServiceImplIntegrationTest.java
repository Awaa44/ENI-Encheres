package fr.eni.projetencheres.bll;

import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dto.InscriptionDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UtilisateurServiceImplIntegrationTest {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    public static void setUp() {
        System.out.println("Before All");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("After All");
    }

    @BeforeEach
    public void BeforeEach() {
        System.out.println("BeforeEach");
    }
    @AfterEach
    public void AfterEach() {}

    @Test
    @DisplayName("Test pour inscrire un utilisateur cas nominal")
    public void createUser() {
        //AAA
        // Arrange
        String pseudoUnique = "Test" + System.currentTimeMillis();
        InscriptionDto inscriptionDto = new InscriptionDto();
        inscriptionDto.setPseudo(pseudoUnique);
        inscriptionDto.setNom("Test");
        inscriptionDto.setPrenom("PrénomTest");
        inscriptionDto.setEmail(pseudoUnique+"@test.com");
        inscriptionDto.setTelephone("0658524585");
        inscriptionDto.setRue("rue faraday");
        inscriptionDto.setCodePostal("44000");
        inscriptionDto.setVille("Nantes");
        inscriptionDto.setMotDePasse("Azerty2548*");
        inscriptionDto.setConfirmMotDePasse("Azerty2548*");

        //Act
        Utilisateur utilisateur = utilisateurService.createUser(inscriptionDto);

        //Assert
        assertNotNull(utilisateur);
        assertEquals(pseudoUnique, utilisateur.getPseudo());

        // Cleanup : supprimer cet utilisateur de la BDD
        jdbcTemplate.update("DELETE FROM utilisateurs WHERE pseudo = ?", pseudoUnique);

    }

    @Test
    @DisplayName(("Test pour supprimer un compte"))
    public void supprimerUtilisateurTest() {
        //AAA
        // Arrange
        String pseudoUnique = "Test" + System.currentTimeMillis();
        InscriptionDto inscriptionDto = new InscriptionDto();
        inscriptionDto.setPseudo(pseudoUnique);
        inscriptionDto.setNom("Test");
        inscriptionDto.setPrenom("PrénomTest");
        inscriptionDto.setEmail(pseudoUnique+"@test.com");
        inscriptionDto.setTelephone("0658524585");
        inscriptionDto.setRue("rue faraday");
        inscriptionDto.setCodePostal("44000");
        inscriptionDto.setVille("Nantes");
        inscriptionDto.setMotDePasse("Azerty2548*");
        inscriptionDto.setConfirmMotDePasse("Azerty2548*");

        Utilisateur utilisateur = utilisateurService.createUser(inscriptionDto);

        //Act
        utilisateurService.deleteUser(utilisateur.getNoUtilisateur());

        //Assert
        //Quand aucune ligne n’est trouvée → Spring lève EmptyResultDataAccessException
        assertThrows(EmptyResultDataAccessException.class, () ->
                utilisateurService.findUtilisateurById(utilisateur.getNoUtilisateur()));
    }
}
