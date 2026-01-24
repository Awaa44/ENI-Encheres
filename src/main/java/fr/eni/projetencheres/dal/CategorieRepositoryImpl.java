package fr.eni.projetencheres.dal;

import fr.eni.projetencheres.bo.Categorie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CategorieRepositoryImpl implements CategorieRepository {

    private JdbcTemplate jdbcTemplate;

    //injection d'un jdbcTemplate
    public CategorieRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ArrayList<Categorie> selectAllCategories()
    {
        ArrayList<Categorie> categories = new ArrayList<Categorie>();

        String sql = "SELECT * FROM CATEGORIES;";
        categories = jdbcTemplate.query(sql, new Mapper.CategorieRowMapper());

        return categories;
    }

}
