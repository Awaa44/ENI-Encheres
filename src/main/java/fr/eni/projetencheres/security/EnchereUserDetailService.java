package fr.eni.projetencheres.security;

import fr.eni.projetencheres.bll.UtilisateurService;
import fr.eni.projetencheres.bo.Utilisateur;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EnchereUserDetailService implements UserDetailsService {

    UtilisateurService utilisateurService;

    public EnchereUserDetailService(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Utilisateur utilisateur = utilisateurService.findUtilisateurByUsername(username);

        if (utilisateur == null) {
            throw new UsernameNotFoundException("Utilisateur ou mot de passe n'existe pas");
        }

        //détermination des roles
        String role = utilisateur.isAdministrateur() ? "ADMIN" : "USER";

        return new CustomUserDetailService(
                utilisateur.getNoUtilisateur(),
                utilisateur.getPseudo(),
                utilisateur.getMotDePasse(),
                role
        );
            // Utilisateur trouvé en base
        /*return User.builder()
                    .username(utilisateur.getPseudo())
                    .password(utilisateur.getMotDePasse()) // mot de passe hashé de la base
                    .roles(utilisateur.isAdministrateur() ? "ADMIN" : "USER")
                    .build();*/
    }
}
