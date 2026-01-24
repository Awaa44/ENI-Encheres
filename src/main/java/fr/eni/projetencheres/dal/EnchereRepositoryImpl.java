package fr.eni.projetencheres.dal;

import fr.eni.projetencheres.bo.ArticleVendu;
import fr.eni.projetencheres.bo.Categorie;
import fr.eni.projetencheres.bo.Enchere;
import fr.eni.projetencheres.bo.Utilisateur;
import fr.eni.projetencheres.enumeration.EncheresAffichage;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EnchereRepositoryImpl implements EnchereRepository {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public EnchereRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate)
    {
        this.jdbcTemplate = namedParameterJdbcTemplate.getJdbcTemplate();
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public ArrayList<ArticleVendu> findAllEncheres()
    {
        String sql = "SELECT * FROM View_Liste_Enchere WHERE date_fin_encheres >= GETDATE();";

        ArrayList<ArticleVendu> articles= new ArrayList<ArticleVendu>();

        articles = jdbcTemplate.query(sql, new Mapper.ViewEnchereRowMapper());

        return articles;
    }

    @Override
    public ArrayList<ArticleVendu> findEncheresOuvertes(int no_utilisateur)
    {
        String sql =
                "SELECT v.*\n" +
                        "FROM View_Liste_Enchere v\n" +
                        "WHERE GETDATE() BETWEEN v.date_debut_encheres AND v.date_fin_encheres";

        ArrayList<ArticleVendu> articles =
                (ArrayList<ArticleVendu>) jdbcTemplate.query(
                        sql,
                        new Mapper.ViewEnchereOuvertesRowMapper()
                );

        for (ArticleVendu article : articles) {
            article.setEncheresAffichage(EncheresAffichage.EncheresOuvertes);
        }

        return articles;
    }

    @Override
    public ArrayList<ArticleVendu> findMesEncheres(int no_utilisateur)
    {
        String sql =
                "SELECT DISTINCT v.* " +
                        "FROM View_Liste_Enchere v " +
                        "JOIN ENCHERES e ON e.no_article = v.no_article " +
                        "WHERE e.no_utilisateur = ?";

        ArrayList<ArticleVendu> articles =
                (ArrayList<ArticleVendu>) jdbcTemplate.query(
                        sql,
                        new Mapper.ViewEnchereOuvertesRowMapper(),
                        no_utilisateur
                );

        for (ArticleVendu article : articles) {
            article.setEncheresAffichage(EncheresAffichage.MesEncheres);
        }

        return articles;
    }

    @Override
    public ArrayList<ArticleVendu> findFiltreEncheresRemportees(int no_utilisateur)
    {
        String sql =
                "SELECT DISTINCT v.*\n" +
                        "FROM View_Liste_Enchere v\n" +
                        "JOIN ENCHERES e ON e.no_article = v.no_article\n" +
                        "WHERE e.no_utilisateur = ?\n" +
                        "AND v.etatVente = 1\n" +
                        "AND e.montant_enchere = (\n" +
                        "    SELECT MAX(e2.montant_enchere)\n" +
                        "    FROM ENCHERES e2\n" +
                        "    WHERE e2.no_article = v.no_article\n" +
                        ")\n";

        ArrayList<ArticleVendu> articles =
                (ArrayList<ArticleVendu>) jdbcTemplate.query(
                        sql,
                        new Mapper.ViewEnchereOuvertesRowMapper(),
                        no_utilisateur
                );

        for (ArticleVendu article : articles) {
            article.setEncheresAffichage(EncheresAffichage.EncheresRemportees);
        }

        return articles;
    }

    @Override
    public ArrayList<ArticleVendu> findEncheresByCategorie(int no_categorie)
    {
        String sql = "SELECT \n" +
                "art.no_article, \n" +
                "art.nom_article, \n" +
                "art.prix_initial, \n" +
                "art.date_fin_encheres, \n" +
                "ench.date_enchere, \n" +
                "pseudo = (SELECT pseudo FROM UTILISATEURS util where art.no_utilisateur = util.no_utilisateur) \n" +
                "FROM ARTICLES_VENDUS art \n" +
                "INNER JOIN ENCHERES ench ON art.no_article = ench.no_article \n" +
                "WHERE art.no_categorie = ?";

        ArrayList<ArticleVendu> articles = new ArrayList<ArticleVendu>();

        articles = jdbcTemplate.query(sql, new Mapper.ViewEnchereOuvertesRowMapper(),no_categorie);

        for (ArticleVendu articleVendu : articles) {
            articleVendu.setEncheresAffichage(EncheresAffichage.EncheresRemportees);
        }

        return articles;
    }

    @Override
    public ArrayList<ArticleVendu> findEncheresBySearch(String recherche) {
        String sql = "SELECT " +
                "art.no_article, " +
                "art.nom_article, " +
                "art.prix_initial, " +
                "art.date_fin_encheres, " +
                "ench.date_enchere, " +
                "util.pseudo " +
                "FROM ARTICLES_VENDUS art " +
                "INNER JOIN ENCHERES ench ON art.no_article = ench.no_article " +
                "INNER JOIN UTILISATEURS util ON art.no_utilisateur = util.no_utilisateur " +
                "WHERE art.nom_article LIKE ?";

        ArrayList<ArticleVendu> articles = (ArrayList<ArticleVendu>) jdbcTemplate.query(
                sql,
                new Mapper.ViewEnchereOuvertesRowMapper(),
                "%" + recherche + "%"
        );

        for (ArticleVendu articleVendu : articles) {
            articleVendu.setEncheresAffichage(EncheresAffichage.EncheresRemportees);
        }

        return articles;
    }

    public ArticleVendu findVenteRemportee(int idArticle)
    {
        ArticleVendu enchereRemportee = new ArticleVendu();

        String sql = "SELECT [no_article]\n" +
                "      ,vlist.[id_retrait]\n" +
                "      ,[nom_article]\n" +
                "      ,[description]\n" +
                "      ,[prix_initial]\n" +
                "      ,[prix_vente]\n" +
                "      ,vlist.[no_utilisateur]\n" +
                "      ,[pseudo]\n" +
                "\t  ,(SELECT telephone FROM UTILISATEURS where no_utilisateur = vlist.no_utilisateur) as telephone \n" +
                "\t  ,ret.rue + ' ' + ret.code_postal + ' ' + ret.ville as adresseRetrait\n" +
                "\t  FROM [ENCHERES].[dbo].[View_Liste_Enchere] vlist\n" +
                "\t  INNER JOIN RETRAIT ret on vlist.id_retrait = ret.id_retrait\n" +
                "\t  where vlist.no_article = ?\n" +
                "\n";


        enchereRemportee = jdbcTemplate.query(sql, new Mapper.VenteRemporteeRowMapper(),idArticle).getFirst();

        return enchereRemportee;
    }

    public ArticleVendu findEnchereRemportee(int idArticle)
    {
        ArticleVendu enchereRemportee = new ArticleVendu();

        String sql = "SELECT TOP 1\n" +
                "    art.no_article,\n" +
                "    art.nom_article,\n" +
                "    art.description,\n" +
                "    art.prix_initial,\n" +
                "    art.prix_vente,\n" +
                "    gagnant.no_utilisateur AS id_gagnant,\n" +
                "    gagnant.pseudo AS pseudo_gagnant,\n" +
                "    gagnant.telephone AS telephone_gagnant,\n" +
                "    ench.montant_enchere,\n" +
                "    ench.date_enchere,\n" +
                "    vendeur.no_utilisateur AS id_vendeur,\n" +
                "    vendeur.pseudo AS pseudo_vendeur,\n" +
                "    ret.rue + ' ' + ret.code_postal + ' ' + ret.ville AS adresseRetrait\n" +
                "FROM ARTICLES_VENDUS art\n" +
                "INNER JOIN ENCHERES ench\n" +
                "    ON ench.no_article = art.no_article\n" +
                "INNER JOIN UTILISATEURS gagnant\n" +
                "    ON gagnant.no_utilisateur = ench.no_utilisateur\n" +
                "INNER JOIN UTILISATEURS vendeur\n" +
                "    ON vendeur.no_utilisateur = art.no_utilisateur\n" +
                "INNER JOIN RETRAIT ret\n" +
                "    ON ret.id_retrait = art.id_retrait\n" +
                "WHERE art.no_article = ?\n" +
                "ORDER BY ench.montant_enchere DESC;\n";


        enchereRemportee = jdbcTemplate.query(sql, new Mapper.EnchereRemporteeRowMapper(),idArticle).getFirst();

        return enchereRemportee;
    }

    @Override
    public Enchere addEnchere(Enchere enchere) {
        String sql = "INSERT INTO ENCHERES (date_enchere, montant_enchere, no_article, no_utilisateur) \n" +
                "VALUES (:dateEnchere, :montantEnchere, :noArticle, :noUtilisateur)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("dateEnchere", enchere.getDateEnchere());
        parameterSource.addValue("montantEnchere", enchere.getMontantEnchere());
        parameterSource.addValue("noArticle", enchere.getArticleVendu().getNoArticle());
        parameterSource.addValue("noUtilisateur", enchere.getUtilisateur().getNoUtilisateur());
        namedParameterJdbcTemplate.update(sql, parameterSource, keyHolder, new String[] {"no_enchere"});
        enchere.setNoEnchere(keyHolder.getKey().intValue());

        return enchere;
    }

    @Override
    public Enchere findEnchereById(int noEnchere) {
        String sql = "SELECT no_enchere,\n" +
                "       date_enchere,\n" +
                "       montant_enchere,\n" +
                "       art.no_article,\n" +
                "       art.nom_article,\n" +
                "       art.prix_initial,\n" +
                "       util.no_utilisateur,\n" +
                "       util.pseudo AS Vendeur\n" +
                "  FROM ENCHERES ench\n" +
                "  INNER JOIN ARTICLES_VENDUS art\n" +
                "  ON art.no_article = ench.no_article\n" +
                "  INNER JOIN UTILISATEURS util\n" +
                "  ON  util.no_utilisateur = ench.no_utilisateur";

        return jdbcTemplate.queryForObject(sql, new EnchereRowMapper(), noEnchere);

    }

    @Override
    public List<Enchere> findEnchereByArticle(int noArticle) {
        String sql = "SELECT no_enchere,\n" +
                "       date_enchere,\n" +
                "       montant_enchere,\n" +
                "       art.no_article,\n" +
                "       art.nom_article,\n" +
                "       art.prix_initial,\n" +
                "       util.no_utilisateur,\n" +
                "       util.pseudo AS Vendeur\n" +
                "  FROM ENCHERES ench\n" +
                "  INNER JOIN ARTICLES_VENDUS art\n" +
                "  ON art.no_article = ench.no_article\n" +
                "  INNER JOIN UTILISATEURS util\n" +
                "  ON  util.no_utilisateur = ench.no_utilisateur";

        return jdbcTemplate.query(sql, new EnchereRowMapper(), noArticle);
    }


    //ROWMAPPER
    class EnchereRowMapper implements RowMapper<Enchere>{

        @Override
        public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {

            //ENCHERES
            Enchere enchere = new Enchere();
            enchere.setMontantEnchere(rs.getInt("no_enchere"));
            enchere.setDateEnchere(rs.getTimestamp("date_enchere").toLocalDateTime());

            //UTILISATEUR
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
            enchere.setUtilisateur(utilisateur);

            //ARTICLE
            ArticleVendu articleVendu = new ArticleVendu();
            articleVendu.setNoArticle(rs.getInt("no_article"));
            enchere.setArticleVendu(articleVendu);

            return enchere;
        }
    }
}
