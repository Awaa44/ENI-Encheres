package fr.eni.projetencheres.dal;

import fr.eni.projetencheres.bo.*;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Mapper
{
    static class UtilisateurRowMapper implements RowMapper<Utilisateur>
    {
        @Override
        public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            Utilisateur  utilisateur = new Utilisateur();
            utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
            utilisateur.setIdRetrait(rs.getInt("id_retrait"));
            utilisateur.setPseudo(rs.getString("pseudo"));
            utilisateur.setNom(rs.getString("nom"));
            utilisateur.setPrenom(rs.getString("prenom"));
            utilisateur.setEmail(rs.getString("email"));
            utilisateur.setTelephone(rs.getString("telephone"));
            utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
            utilisateur.setCredit(rs.getInt("credit"));
            utilisateur.setAdministrateur(rs.getBoolean("administrateur"));

            Retrait retrait = new Retrait();
            retrait.setIdRetrait(rs.getInt("id_retrait"));
            retrait.setRue(rs.getString("rue"));
            retrait.setVille(rs.getString("ville"));
            retrait.setCodePostal(rs.getString("code_postal"));

            utilisateur.setRetrait(retrait);

            utilisateur.setRue(retrait.getRue());
            utilisateur.setCodePostal(retrait.getCodePostal());
            utilisateur.setVille(retrait.getVille());


            return utilisateur;
        }
    }

    static class UtilisateurLoginRowMapper implements RowMapper<Utilisateur>
    {
        @Override
        public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException
        {

            Utilisateur  utilisateur = new Utilisateur();
            utilisateur.setPseudo(rs.getString("pseudo"));
            utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
            utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));

            return utilisateur;
        }
    }

    static class ViewEnchereRowMapper implements ResultSetExtractor<ArrayList<ArticleVendu>>
    {
        @Override
        public ArrayList<ArticleVendu> extractData(ResultSet rs) throws SQLException
        {
            ArrayList<ArticleVendu>  articles = new ArrayList<ArticleVendu>();

            while (rs.next())
            {
                ArticleVendu article = new ArticleVendu();
                Retrait retrait = new Retrait();
                Utilisateur utilisateur = new Utilisateur();
                Categorie categorie = new Categorie();

                article.setNoArticle(rs.getInt("no_article"));
                retrait.setIdRetrait(rs.getInt("id_retrait"));
                article.setNomArticle(rs.getString("nom_article"));
                article.setDescription(rs.getString("description"));
                article.setDateDebutEncheres(rs.getTimestamp("date_debut_encheres").toLocalDateTime());
                article.setDateFinEncheres(rs.getTimestamp("date_fin_encheres").toLocalDateTime());
                article.setMiseAPrix(rs.getInt("prix_initial"));
                article.setPrixVente(rs.getInt("prix_vente"));
                article.setEtatVente(rs.getInt("etatVente"));
                utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
                categorie.setNoCategorie(rs.getInt("no_categorie"));
                categorie.setLibelle(rs.getString("libelleCategorie"));
                utilisateur.setPseudo(rs.getString("pseudo"));

                article.setCategorie(categorie);
                article.setLieuRetrait(retrait);
                article.setVendeur(utilisateur);

                articles.add(article);
            }
            return articles;
        }
    }

    static class CategorieRowMapper implements ResultSetExtractor<ArrayList<Categorie>>
    {
        @Override
        public ArrayList<Categorie> extractData(ResultSet rs) throws SQLException
        {
            ArrayList<Categorie> categories = new ArrayList<>();


            while (rs.next())
            {
                Categorie categorie = new Categorie();
                categorie.setLibelle(rs.getString("libelle"));
                categorie.setNoCategorie(rs.getInt("no_categorie"));
                categories.add(categorie);
            }

            return categories;
        }
    }

    static class ViewEnchereOuvertesRowMapper implements ResultSetExtractor<ArrayList<ArticleVendu>>
    {
        @Override
        public ArrayList<ArticleVendu> extractData(ResultSet rs) throws SQLException
        {
            ArrayList<ArticleVendu>  articles = new ArrayList<ArticleVendu>();

            while (rs.next())
            {
                ArticleVendu  article = new ArticleVendu();
                Utilisateur  utilisateur = new Utilisateur();
                Categorie  categorie = new Categorie();

                article.setNoArticle(rs.getInt("no_article"));
                article.setNomArticle(rs.getString("nom_article"));
                article.setMiseAPrix(rs.getInt("prix_initial"));
                categorie.setLibelle(rs.getString("libelleCategorie"));
                article.setDateFinEncheres(rs.getTimestamp("date_fin_encheres").toLocalDateTime());
                article.setDateFinEncheres(rs.getTimestamp("date_fin_encheres").toLocalDateTime());
                utilisateur.setPseudo(rs.getString("pseudo"));
                article.setVendeur(utilisateur);
                article.setCategorie(categorie);
                articles.add(article);
            }
            return articles;
        }
    }

    static class VenteRemporteeRowMapper implements RowMapper<ArticleVendu>
    {
        @Override
        public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            Retrait retrait = new Retrait();
            Utilisateur utilisateur = new Utilisateur();

            ArticleVendu  enchereRemportee = new ArticleVendu();
            enchereRemportee.setNoArticle(rs.getInt("no_article"));
            retrait.setIdRetrait(rs.getInt("id_retrait"));
            enchereRemportee.setNomArticle(rs.getString("nom_article"));
            enchereRemportee.setDescription(rs.getString("description"));
            enchereRemportee.setMiseAPrix(rs.getInt("prix_initial"));
            enchereRemportee.setPrixVente(rs.getInt("prix_vente"));
            utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
            utilisateur.setPseudo(rs.getString("pseudo"));
            utilisateur.setTelephone(rs.getString("telephone"));
            retrait.setAdresseConcat(rs.getString("adresseRetrait"));

            enchereRemportee.setVendeur(utilisateur);
            enchereRemportee.setLieuRetrait(retrait);

            return enchereRemportee;
        }
    }

    static class EnchereRemporteeRowMapper implements RowMapper<ArticleVendu>
    {
        @Override
        public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            Retrait retrait = new Retrait();
            Utilisateur utilisateur = new Utilisateur();
            Enchere enchere = new Enchere();

            ArticleVendu  enchereRemportee = new ArticleVendu();
            enchereRemportee.setNoArticle(rs.getInt("no_article"));
            enchereRemportee.setNomArticle(rs.getString("nom_article"));
            enchereRemportee.setDescription(rs.getString("description"));
            enchereRemportee.setMiseAPrix(rs.getInt("prix_initial"));
            enchereRemportee.setPrixVente(rs.getInt("prix_vente"));
            utilisateur.setNoUtilisateurGagnant(rs.getInt("id_gagnant"));
            utilisateur.setPseudoGagnant(rs.getString("pseudo_gagnant"));
            utilisateur.setTelephone(rs.getString("telephone_gagnant"));
            enchere.setMontantEnchere(rs.getInt("montant_enchere"));
            enchereRemportee.setDateFinEncheres(rs.getTimestamp("date_enchere").toLocalDateTime());
            utilisateur.setNoUtilisateur(rs.getInt("id_vendeur"));
            utilisateur.setPseudo(rs.getString("pseudo_vendeur"));
            retrait.setAdresseConcat(rs.getString("adresseRetrait"));

            enchereRemportee.setVendeur(utilisateur);
            enchereRemportee.setLieuRetrait(retrait);
            enchereRemportee.setEnchere(enchere);

            return enchereRemportee;
        }
    }
}
