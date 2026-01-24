package fr.eni.projetencheres.bll;

import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dal.UtilisateurRepository;
import fr.eni.projetencheres.dto.InscriptionDto;
import fr.eni.projetencheres.exception.EmailDejaUtiliseException;
import fr.eni.projetencheres.exception.PseudoDejaUtiliseException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//TEST UNITAIRE
@ExtendWith(MockitoExtension.class)
public class UtilisateurServiceImplTest {

    @Mock
    UtilisateurRepository utilisateurRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @InjectMocks
    UtilisateurServiceImpl utilisateurServiceImpl;



    @Test
    @DisplayName("Test pour inscrire un utilisateur cas nominal")
    public void createUser() {
        //AAA
        //Arrange
        InscriptionDto inscriptionDto = new InscriptionDto();
        inscriptionDto.setPseudo("Test25");
        inscriptionDto.setNom("Test");
        inscriptionDto.setPrenom("PrénomTest");
        inscriptionDto.setEmail("email@test.com");
        inscriptionDto.setTelephone("0658524585");
        inscriptionDto.setRue("rue faraday");
        inscriptionDto.setCodePostal("44000");
        inscriptionDto.setVille("Nantes");
        inscriptionDto.setMotDePasse("Azerty2548*");
        inscriptionDto.setConfirmMotDePasse("Azerty2548*");

        //indique que le pseudo n'existe pas en base
        when(utilisateurRepository.existsUtilisateurByUsername("Test25"))
                .thenReturn(false);

        //indique que l'email n'existe pas en base
        when(utilisateurRepository.existsUtilisateurByEmail("email@test.com"))
                .thenReturn(false);

        //hash le mot de passe
        when(passwordEncoder.encode("Azerty2548*"))
                .thenReturn("Azerty2548*Hash");

        //simulation du renvoi de la BDD en indiquant noUtilisateur et Id retrait
        when(utilisateurRepository.createUser(any(Utilisateur.class)))
                .thenAnswer(invocation -> {
                    Utilisateur u = invocation.getArgument(0);
                    u.setNoUtilisateur(1);
                    u.setIdRetrait(10);
                    return u;
                });

        //Act
        Utilisateur utilisateur = utilisateurServiceImpl.createUser(inscriptionDto);

        //Assert
        assertNotNull(utilisateur);
        assertEquals("Test25", utilisateur.getPseudo());
        assertEquals("Azerty2548*Hash", utilisateur.getMotDePasse());

        verify(passwordEncoder).encode("Azerty2548*");
        verify(utilisateurRepository).createUser(any(Utilisateur.class));

    }

    @Test
    @DisplayName("Création utilisateur - pseudo déjà existant")
    void createUser_pseudoDejaExistant() {

        // Arrange
        InscriptionDto inscriptionDto = new InscriptionDto();
        inscriptionDto.setPseudo("Test25");
        inscriptionDto.setNom("Test");
        inscriptionDto.setPrenom("PrénomTest");
        inscriptionDto.setEmail("email@test.com");
        inscriptionDto.setTelephone("0658524585");
        inscriptionDto.setRue("rue faraday");
        inscriptionDto.setCodePostal("44000");
        inscriptionDto.setVille("Nantes");
        inscriptionDto.setMotDePasse("Azerty2548*");
        inscriptionDto.setConfirmMotDePasse("Azerty2548*");

        // indique que le pseudo existe en base
        when(utilisateurRepository.existsUtilisateurByUsername("Test25"))
                .thenReturn(true);

        // Act + Assert
        PseudoDejaUtiliseException exception =
                assertThrows(PseudoDejaUtiliseException.class,
                        () -> utilisateurServiceImpl.createUser(inscriptionDto));

        assertEquals("Ce pseudo est déjà utilisé", exception.getMessage());

        // Vérifications comportementales
        verify(utilisateurRepository).existsUtilisateurByUsername("Test25");
        verify(utilisateurRepository, never()).existsUtilisateurByEmail(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(utilisateurRepository, never()).createUser(any());
    }

    @Test
    @DisplayName("Création utilisateur - email déjà existant")
    void createUser_emailDejaExistant() {

        // Arrange
        InscriptionDto inscriptionDto = new InscriptionDto();
        inscriptionDto.setPseudo("Test25");
        inscriptionDto.setNom("Test");
        inscriptionDto.setPrenom("PrénomTest");
        inscriptionDto.setEmail("email@test.com");
        inscriptionDto.setTelephone("0658524585");
        inscriptionDto.setRue("rue faraday");
        inscriptionDto.setCodePostal("44000");
        inscriptionDto.setVille("Nantes");
        inscriptionDto.setMotDePasse("Azerty2548*");
        inscriptionDto.setConfirmMotDePasse("Azerty2548*");

        // indique que le pseudo n'existe pas en base
        when(utilisateurRepository.existsUtilisateurByUsername("Test25"))
                .thenReturn(false);

        //indique que l'email existe en base
        when(utilisateurRepository.existsUtilisateurByEmail("email@test.com"))
                .thenReturn(true);

        // Act + Assert
        EmailDejaUtiliseException exception =
                assertThrows(EmailDejaUtiliseException.class,
                        () -> utilisateurServiceImpl.createUser(inscriptionDto));

        assertEquals("Cet email est déjà utilisé", exception.getMessage());

        // Vérifications comportementales
        verify(utilisateurRepository).existsUtilisateurByUsername("Test25");
        verify(utilisateurRepository).existsUtilisateurByEmail("email@test.com");
        verify(passwordEncoder, never()).encode(anyString());
        verify(utilisateurRepository, never()).createUser(any());
    }

}
