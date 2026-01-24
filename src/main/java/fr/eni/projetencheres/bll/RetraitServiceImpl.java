package fr.eni.projetencheres.bll;

import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.bo.Retrait;
import fr.eni.projetencheres.dal.RetraitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RetraitServiceImpl implements RetraitService{

    private RetraitRepository retraitRepository;

    public RetraitServiceImpl(RetraitRepository retraitRepository) {
        this.retraitRepository = retraitRepository;
    }

    @Override
    public Retrait findRetraitById(int id) {
        return retraitRepository.findRetraitById(id);
    }

    @Override
    public Retrait createRetrait(Retrait retrait) {
        //vérification que les champs sont bien remplis
        if(retrait.getRue() == null || retrait.getRue().isBlank() ||
                retrait.getCodePostal() == null || retrait.getCodePostal().isBlank() ||
                retrait.getVille() ==null || retrait.getVille().isBlank())
        {
            throw new IllegalArgumentException("Adresse de retrait incomplète");
        }

        return retraitRepository.createRetrait(retrait);
    }

    @Override
    public List<Retrait> findRetraitsByUserId(int id) {
        return retraitRepository.findRetraitsByUserId(id);
    }

    @Transactional
    @Override
    public void lierRetraitUtilisateur(int noUtilisateur, int idRetrait) {
        retraitRepository.lierRetraitUtilisateur(noUtilisateur, idRetrait);
    }
}
