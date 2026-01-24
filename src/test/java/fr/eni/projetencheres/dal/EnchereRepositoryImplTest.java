package fr.eni.projetencheres.dal;

import fr.eni.projetencheres.bo.ArticleVendu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;

@SpringBootTest
public class EnchereRepositoryImplTest
{
    @Autowired
    private EnchereRepository enchereRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("test de récupération des informations de la vue des enchères")
    public void testrecuperationVueSql()
    {
        ArrayList<ArticleVendu> articles= new ArrayList<ArticleVendu>();
        articles = enchereRepository.findAllEncheres();
    }

    @Test
    @DisplayName("test de récupération des filtres enchères")
    public void testRecuperationFiltreEncheres()
    {
        int no_utilisateur = 2;
        ArrayList<ArticleVendu> articles= new ArrayList<ArticleVendu>();
        articles = enchereRepository.findEncheresOuvertes(2);
        articles = enchereRepository.findMesEncheres(2);
        articles = enchereRepository.findFiltreEncheresRemportees(2);
    }
}
