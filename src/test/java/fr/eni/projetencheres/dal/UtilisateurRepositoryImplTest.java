package fr.eni.projetencheres.dal;
import fr.eni.projetencheres.bo.Utilisateur;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class UtilisateurRepositoryImplTest
{
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("test byUsername") //Optionnel
    public void testbyUsername()
    {
        utilisateurRepository.findUtilisateurByUsername("jdoe");

    }

    @Test
    @DisplayName("test des procèdures stockées") //Optionnel
    public void testProceduresCRUD()
    {
        //AAA
        //Arrange : Préparation du test
        Utilisateur utilisateurInsert = new Utilisateur();
        utilisateurInsert.setPseudo("Test");
        utilisateurInsert.setNom("nom");
        utilisateurInsert.setPrenom("prenom");
        utilisateurInsert.setEmail("test@Test");
        utilisateurInsert.setTelephone("0240110453");
        utilisateurInsert.setMotDePasse("123456789");
        utilisateurInsert.setCredit(123);
        utilisateurInsert.setAdministrateur(false);
        utilisateurInsert.setRue("rue de test");
        utilisateurInsert.setCodePostal("code postal");
        utilisateurInsert.setVille("ville");

        utilisateurInsert = utilisateurRepository.createUser(utilisateurInsert);

        Utilisateur utilisateurUpdate = new Utilisateur();
        utilisateurUpdate.setNoUtilisateur(utilisateurInsert.getNoUtilisateur());
        utilisateurUpdate.setPseudo("TestModif");
        utilisateurUpdate.setNom("nomModif");
        utilisateurUpdate.setPrenom("prenomModif");
        utilisateurUpdate.setEmail("test@TestModif");
        utilisateurUpdate.setTelephone("0240110453Modif");
        utilisateurUpdate.setMotDePasse("123456789Modif");
        utilisateurUpdate.setIdRetrait(utilisateurInsert.getIdRetrait());
        utilisateurUpdate.setRue("rue de testModif");
        utilisateurUpdate.setCodePostal("code postalModif");
        utilisateurUpdate.setVille("villeModif");

        utilisateurRepository.updateUser(utilisateurUpdate);
        utilisateurRepository.deleteUser(utilisateurInsert.getNoUtilisateur());
    }
}
