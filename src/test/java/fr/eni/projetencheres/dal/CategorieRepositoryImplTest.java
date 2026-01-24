package fr.eni.projetencheres.dal;

import fr.eni.projetencheres.bo.Categorie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CategorieRepositoryImplTest
{
    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("test de récupération des catégories") //Optionnel
    public void testRecuperationCategories()
    {
        ArrayList<Categorie> categories = new ArrayList<Categorie>();

        categories = categorieRepository.selectAllCategories();

    }
}
