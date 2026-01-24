package fr.eni.projetencheres.dal;

import fr.eni.projetencheres.bo.Retrait;

import java.util.List;

public interface RetraitRepository {

    //afficher un retrait par id
    Retrait findRetraitById(int id);

    //ajouter un retrait
    Retrait createRetrait(Retrait retrait);

    //afficher tous les retraits d'un utilisateur
    List<Retrait> findRetraitsByUserId(int noUtilisateur);

    //lier  les retraits à un utilisateur
    void lierRetraitUtilisateur(int noUtilisateur, int idRetrait);


}
