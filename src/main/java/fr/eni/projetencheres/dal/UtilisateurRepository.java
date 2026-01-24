package fr.eni.projetencheres.dal;

import fr.eni.projetencheres.bo.Utilisateur;

public interface UtilisateurRepository {
    Utilisateur findUtilisateurById(int id);
    Utilisateur findUtilisateurByUsername(String username);
    Utilisateur createUser(Utilisateur utilisateur);
    void deleteUser(int noUtilisateur);
    int updateUser(Utilisateur utilisateur);

    //méthode pour vérifier si un pseudo existe
    boolean existsUtilisateurByUsername(String username);
    //méthode pour vérifier si un email existe
    boolean existsUtilisateurByEmail(String email);

}
