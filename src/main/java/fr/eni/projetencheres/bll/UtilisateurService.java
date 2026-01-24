package fr.eni.projetencheres.bll;

import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.dto.InscriptionDto;
import fr.eni.projetencheres.dto.ModifierProfilDto;

public interface UtilisateurService {


    Utilisateur findUtilisateurById(int id);
    Utilisateur findUtilisateurByUsername(String username);
    Utilisateur createUser(InscriptionDto utilisateurDto);
    void deleteUser(int noUtilisateur);
    int updateUser(int userId, ModifierProfilDto modifierProfilDto);


    //méthode pour vérifier si un pseudo existe
    boolean existsUtilisateurByUsername(String username);
    //méthode pour vérifier si un email existe
    boolean existsUtilisateurByEmail(String email);
}
