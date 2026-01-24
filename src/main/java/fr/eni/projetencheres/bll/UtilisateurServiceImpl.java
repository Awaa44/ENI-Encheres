package fr.eni.projetencheres.bll;

import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dal.UtilisateurRepository;
import fr.eni.projetencheres.dto.InscriptionDto;
import fr.eni.projetencheres.dto.ModifierProfilDto;
import fr.eni.projetencheres.exception.EmailDejaUtiliseException;
import fr.eni.projetencheres.exception.MotDePasseIncorrectException;
import fr.eni.projetencheres.exception.PseudoDejaUtiliseException;
import fr.eni.projetencheres.exception.UtilisateurNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    //implémentation du logger
    private static final Logger logger = LoggerFactory.getLogger(UtilisateurServiceImpl.class);

    //implementation du password encoder
    private final PasswordEncoder passwordEncoder;
    //implementation du repository
    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurServiceImpl(PasswordEncoder passwordEncoder, UtilisateurRepository utilisateurRepository) {
        this.passwordEncoder = passwordEncoder;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public Utilisateur findUtilisateurById(int id) {
        try {
            return  utilisateurRepository.findUtilisateurById(id);
        } catch (UtilisateurNotFound ex) {
            logger.warn("Utilisateur non trouvé avec l'id : {}", id);
            throw ex;
        }
    }

    @Override
    public Utilisateur findUtilisateurByUsername(String username) {
        try {
            return utilisateurRepository.findUtilisateurByUsername(username);
        } catch (UtilisateurNotFound ex) {
            logger.warn("Pseudo utilisateur non trouvé : {}", username);
            throw ex;
        }
    }

    @Override
    @Transactional
    public Utilisateur createUser(InscriptionDto utilisateurDto) {

        //vérification du pseudo et email
        if (utilisateurRepository.existsUtilisateurByUsername(utilisateurDto.getPseudo())) {
            throw new PseudoDejaUtiliseException("Ce pseudo est déjà utilisé");
        }

        if (utilisateurRepository.existsUtilisateurByEmail(utilisateurDto.getEmail())) {
            throw new EmailDejaUtiliseException("Cet email est déjà utilisé");
        }

        //copie des données du DTO vers le BO
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPseudo(utilisateurDto.getPseudo());
        utilisateur.setNom(utilisateurDto.getNom());
        utilisateur.setPrenom(utilisateurDto.getPrenom());
        utilisateur.setEmail(utilisateurDto.getEmail());
        utilisateur.setTelephone(utilisateurDto.getTelephone());
        utilisateur.setRue(utilisateurDto.getRue());
        utilisateur.setCodePostal(utilisateurDto.getCodePostal());
        utilisateur.setVille(utilisateurDto.getVille());

        //mot de passe hashé
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateurDto.getMotDePasse()));

        //valeur par défaut pour crédit et admin
        utilisateur.setCredit(100);
        utilisateur.setAdministrateur(false);

        utilisateur = utilisateurRepository.createUser(utilisateur);

        return utilisateur;
    }

    @Override
    public boolean existsUtilisateurByUsername(String username) {
        return utilisateurRepository.existsUtilisateurByUsername(username);
    }

    @Override
    public boolean existsUtilisateurByEmail(String email) {
        return utilisateurRepository.existsUtilisateurByEmail(email);
    }


    @Override
    public int updateUser(int userId, ModifierProfilDto modifierProfilDto) {

        Utilisateur utilisateurActuel = utilisateurRepository.findUtilisateurById(userId);

        //vérification mot de passe
        if (!passwordEncoder.matches(modifierProfilDto.getMotDePasse(), utilisateurActuel.getMotDePasse())){
            throw new MotDePasseIncorrectException("Le mot de passe est incorrect");
        }

        //vérification pseudo et email existent dans la base
        if (!modifierProfilDto.getPseudo().equals(utilisateurActuel.getPseudo())) {
            if (utilisateurRepository.existsUtilisateurByUsername(modifierProfilDto.getPseudo())) {
                throw new PseudoDejaUtiliseException("Ce pseudo est déjà utilisé");
            }
        }
        if (!modifierProfilDto.getEmail().equals(utilisateurActuel.getEmail())) {
            if (utilisateurRepository.existsUtilisateurByEmail(modifierProfilDto.getEmail())) {
                throw new EmailDejaUtiliseException("Cet email est déjà utilisé");
            }
        }

        //copie des données du DTO vers le bo
        utilisateurActuel.setPseudo(modifierProfilDto.getPseudo());
        utilisateurActuel.setNom(modifierProfilDto.getNom());
        utilisateurActuel.setPrenom(modifierProfilDto.getPrenom());
        utilisateurActuel.setEmail(modifierProfilDto.getEmail());
        utilisateurActuel.setTelephone(modifierProfilDto.getTelephone());
        utilisateurActuel.setRue(modifierProfilDto.getRue());
        utilisateurActuel.setCodePostal(modifierProfilDto.getCodePostal());
        utilisateurActuel.setVille(modifierProfilDto.getVille());

        //mot de passe hashé si nouveau mot de passe entré
        if (modifierProfilDto.getNewMotDePasse() != null && !modifierProfilDto.getNewMotDePasse().isEmpty()) {
            utilisateurActuel.setMotDePasse(passwordEncoder.encode(modifierProfilDto.getNewMotDePasse()));
            logger.info("Mot de passe mis à jour pour l'utilisateur ID : {}", userId);
        }

        return utilisateurRepository.updateUser(utilisateurActuel);
    }


    @Override
    @Transactional
    public void deleteUser(int noUtilisateur) {

        utilisateurRepository.findUtilisateurById(noUtilisateur);

        utilisateurRepository.deleteUser(noUtilisateur);
    }
}
