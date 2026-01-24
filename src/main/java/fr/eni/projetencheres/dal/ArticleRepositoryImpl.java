package fr.eni.projetencheres.dal;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.bo.Retrait;
import fr.eni.projetencheres.bo.Utilisateur;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ArticleRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = namedParameterJdbcTemplate.getJdbcTemplate();
    }

    public ArticleVendu insertArticle(ArticleVendu articleVendu) {
        String sql = "INSERT INTO ARTICLES_VENDUS(id_retrait, nom_article, description, date_debut_encheres, " +
                "date_fin_encheres, prix_initial, prix_vente, etatVente, no_utilisateur, no_categorie) " +
                "VALUES (:idRetrait, :nomArticle, :description, :dateDebutEncheres, :dateFinEncheres, :prixInitial," +
                ":prixVente, :etatVente, :noUtilisateur, :noCategorie)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("idRetrait", articleVendu.getLieuRetrait().getIdRetrait());
        parameter.addValue("nomArticle", articleVendu.getNomArticle());
        parameter.addValue("description", articleVendu.getDescription());
        parameter.addValue("dateDebutEncheres", articleVendu.getDateDebutEncheres());
        parameter.addValue("dateFinEncheres", articleVendu.getDateFinEncheres());
        parameter.addValue("prixInitial", articleVendu.getMiseAPrix());
        parameter.addValue("prixVente", articleVendu.getPrixVente());
        parameter.addValue("etatVente", articleVendu.getEtatVente());
        parameter.addValue("noUtilisateur", articleVendu.getVendeur().getNoUtilisateur());
        parameter.addValue("noCategorie", articleVendu.getCategorie().getNoCategorie());
        namedParameterJdbcTemplate.update(sql, parameter, keyHolder, new String[] {"no_article"});

        articleVendu.setNoArticle(keyHolder.getKey().intValue());

        return articleVendu;
    }

    @Override
    public ArticleVendu selectArticle(int noArticle) {
        String sql = "SELECT\n" +
                "art.no_article,\n" +
                "art.nom_article,\n" +
                "art.description,\n" +
                "art.date_debut_encheres,\n" +
                "art.date_fin_encheres,\n" +
                "art.prix_initial,\n" +
                "art.prix_vente,\n" +
                "art.etatVente,\n" +
                "cat.no_categorie,\n" +
                "cat.libelle,\n" +
                "util.pseudo,\n" +
                "util.no_utilisateur,\n" +
                "ret.id_retrait,\n" +
                "ret.rue,\n" +
                "ret.code_postal,\n" +
                "ret.ville\n" +
                "FROM ARTICLES_VENDUS art\n" +
                "INNER JOIN CATEGORIES cat ON art.no_categorie = cat.no_categorie\n" +
                "INNER JOIN UTILISATEURS util ON art.no_utilisateur = util.no_utilisateur\n" +
                "INNER JOIN RETRAIT ret ON art.id_retrait = ret.id_retrait WHERE art.no_article = ?";

        ArticleVendu articleVendu = jdbcTemplate.queryForObject(sql, new ArticlesVendusRowMapper(), noArticle);

        return articleVendu;
    }

    @Override
    public List<ArticleVendu> findAllArticleVenduByVendeur(Integer noUtilisateur) {
        String sql = "SELECT\n" +
                "art.no_article,\n" +
                "art.nom_article,\n" +
                "art.description,\n" +
                "art.date_debut_encheres,\n" +
                "art.date_fin_encheres,\n" +
                "art.prix_initial,\n" +
                "art.prix_vente,\n" +
                "art.etatVente,\n" +
                "cat.no_categorie,\n" +
                "cat.libelle,\n" +
                "util.pseudo,\n" +
                "util.no_utilisateur,\n" +
                "ret.id_retrait,\n" +
                "ret.rue,\n" +
                "ret.code_postal,\n" +
                "ret.ville\n" +
                "FROM ARTICLES_VENDUS art\n" +
                "INNER JOIN CATEGORIES cat ON art.no_categorie = cat.no_categorie\n" +
                "INNER JOIN UTILISATEURS util ON art.no_utilisateur = util.no_utilisateur\n" +
                "INNER JOIN RETRAIT ret ON art.id_retrait = ret.id_retrait";

        List<ArticleVendu> articlesByVendeur = jdbcTemplate.query(sql, new ArticlesVendusRowMapper(), noUtilisateur);
        return articlesByVendeur;
    }

    @Override
    public List<ArticleVendu> findAllArticleVendu() {
        String sql = "SELECT\n" +
                "art.no_article,\n" +
                "art.nom_article,\n" +
                "art.description,\n" +
                "art.date_debut_encheres,\n" +
                "art.date_fin_encheres,\n" +
                "art.prix_initial,\n" +
                "art.prix_vente,\n" +
                "art.etatVente,\n" +
                "cat.no_categorie,\n" +
                "cat.libelle,\n" +
                "util.pseudo,\n" +
                "util.no_utilisateur,\n" +
                "ret.id_retrait,\n" +
                "ret.rue,\n" +
                "ret.code_postal,\n" +
                "ret.ville\n" +
                "FROM ARTICLES_VENDUS art\n" +
                "INNER JOIN CATEGORIES cat ON art.no_categorie = cat.no_categorie\n" +
                "INNER JOIN UTILISATEURS util ON art.no_utilisateur = util.no_utilisateur\n" +
                "INNER JOIN RETRAIT ret ON art.id_retrait = ret.id_retrait";

        List<ArticleVendu> articles = jdbcTemplate.query(sql, new ArticlesVendusRowMapper());
        return articles;
    }

    class ArticlesVendusRowMapper implements RowMapper<ArticleVendu>{

        @Override
        public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException {
            //ARTICLES
            ArticleVendu articleVendu = new ArticleVendu();
            articleVendu.setNoArticle(rs.getInt("no_article"));
            articleVendu.setNomArticle(rs.getString("nom_article"));
            articleVendu.setDescription(rs.getString("description"));
            articleVendu.setDateDebutEncheres(rs.getTimestamp("date_debut_encheres").toLocalDateTime());
            articleVendu.setDateFinEncheres(rs.getTimestamp("date_fin_encheres").toLocalDateTime());
            articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
            articleVendu.setPrixVente(rs.getInt("prix_vente"));
            articleVendu.setEtatVente(rs.getInt("etatVente"));

            //CATEGORIE
            Categorie categorie = new Categorie();
            categorie.setNoCategorie(rs.getInt("no_categorie"));
            categorie.setLibelle(rs.getString("libelle"));
            articleVendu.setCategorie(categorie);

            //RETRAIT
            Retrait retrait = new Retrait();
            retrait.setIdRetrait(rs.getInt("id_retrait"));
            retrait.setRue(rs.getString("rue"));
            retrait.setCodePostal(rs.getString("code_postal"));
            retrait.setVille(rs.getString("ville"));
            articleVendu.setLieuRetrait(retrait);

            //UTILISATEUR
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
            utilisateur.setPseudo(rs.getString("pseudo"));
            articleVendu.setVendeur(utilisateur);

            return articleVendu;
        }
    }
}
