package fr.eni.projetencheres.dal;

import fr.eni.projetencheres.bo.Utilisateur;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UtilisateurRepositoryImpl implements UtilisateurRepository {
    private JdbcTemplate jdbcTemplate;

    //injection d'un jdbcTemplate
    public UtilisateurRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Utilisateur findUtilisateurById(int id)
    {
        Utilisateur utilisateur;

        String sql = "SELECT\n" +
                "       util.no_utilisateur,\n" +
                "       util.pseudo,\n" +
                "       util.nom,\n" +
                "       util.prenom,\n" +
                "       util.email,\n" +
                "       util.telephone,\n" +
                "       util.mot_de_passe,\n" +
                "       util.credit,\n" +
                "       util.administrateur,\n" +
                "       ret.id_retrait,\n" +
                "       ret.code_postal,\n" +
                "       ret.rue,\n" +
                "       ret.ville\n" +
                "FROM UTILISATEURS util\n" +
                "LEFT JOIN (\n" +
                "SELECT TOP 1 no_utilisateur, id_retrait\n" +
                "FROM ADRESSES adr\n" +
                "WHERE no_utilisateur = ?\n" +
                "ORDER BY id_retrait ASC\n" +
                "       ) adr ON adr.no_utilisateur = util.no_utilisateur\n" +
                "LEFT JOIN RETRAIT ret\n" +
                "       ON ret.id_retrait = adr.id_retrait\n" +
                "\t   WHERE util.no_utilisateur = ?;";

        utilisateur = jdbcTemplate.queryForObject(sql, new Mapper.UtilisateurRowMapper(), id, id);
        return utilisateur;
    }

    @Override
    public Utilisateur findUtilisateurByUsername(String username)
    {
        String sql = username.contains("@") ?
                "select pseudo,mot_de_passe, no_utilisateur from UTILISATEURS where email = ?"
                : "select pseudo,mot_de_passe, no_utilisateur from UTILISATEURS where pseudo = ?";
        Utilisateur utilisateur;

        utilisateur = jdbcTemplate.queryForObject(sql, new Mapper.UtilisateurLoginRowMapper(), username);

        return utilisateur;
    }

    @Override
    public void deleteUser(int noUtilisateur)
    {
        String sql = "{CALL sp_Delete_User(?)}";

        try {
            jdbcTemplate.execute((ConnectionCallback<Integer>) connection -> {
                try (CallableStatement cs = connection.prepareCall(sql)) {
                    cs.setInt(1, noUtilisateur);
                    cs.execute();
                }
                return null;
            });
        } catch (DataAccessException e) {
            throw new RuntimeException(
                    "Erreur lors de la suppression de l'utilisateur", e
            );
        }
    }

    @Override
    public Utilisateur createUser(Utilisateur utilisateur)
    {

        String sql = "{CALL sp_Create_User(?,?,?,?,?,?,?,?,?,?,?)}";

        return jdbcTemplate.execute((ConnectionCallback<Utilisateur>) connection -> {

            try (CallableStatement cs = connection.prepareCall(sql)) {

                // Paramètres utilisateur
                cs.setString(1, utilisateur.getPseudo());
                cs.setString(2, utilisateur.getNom());
                cs.setString(3, utilisateur.getPrenom());
                cs.setString(4, utilisateur.getEmail());
                cs.setString(5, utilisateur.getTelephone());
                cs.setString(6, utilisateur.getMotDePasse());
                cs.setInt(7, utilisateur.getCredit());
                cs.setBoolean(8, utilisateur.isAdministrateur());

                // Paramètres retrait
                cs.setString(9, utilisateur.getRue());
                cs.setString(10, utilisateur.getCodePostal());
                cs.setString(11, utilisateur.getVille());

                // Exécution
                try (ResultSet rs = cs.executeQuery()) {
                    int userId = -1;
                    int idRetrait = -1;

                    if (rs.next()) {
                        userId = rs.getInt("userId");
                    }

                    // Passer au deuxième ResultSet
                    if (cs.getMoreResults()) {
                        try (ResultSet rs2 = cs.getResultSet()) {
                            if (rs2.next()) {
                                idRetrait = rs2.getInt("id_retrait");
                            }
                        }
                    }

                    if (userId != -1 && idRetrait != -1) {
                        utilisateur.setNoUtilisateur(userId);
                        utilisateur.setIdRetrait(idRetrait);
                        return utilisateur;
                    } else {
                        throw new SQLException("La procédure n'a pas retourné les IDs attendus");
                    }
                }

            }
            catch (SQLException e)
            {
                throw new RuntimeException(
                        "Erreur lors de la création de l'utilisateur", e
                );
            }
        });
    }

    @Override
    public int updateUser(Utilisateur utilisateur) {

        String sql = "{CALL sp_Update_User(?,?,?,?,?,?,?,?,?,?,?)}";

        return jdbcTemplate.execute((ConnectionCallback<Integer>) connection -> {

            try (CallableStatement cs = connection.prepareCall(sql)) {

                // Paramètres utilisateur
                cs.setInt(1, utilisateur.getNoUtilisateur());
                cs.setString(2, utilisateur.getPseudo());
                cs.setString(3, utilisateur.getNom());
                cs.setString(4, utilisateur.getPrenom());
                cs.setString(5, utilisateur.getEmail());
                cs.setString(6, utilisateur.getTelephone());
                cs.setString(7, utilisateur.getMotDePasse());
                // Paramètres retrait
                cs.setInt(8, utilisateur.getIdRetrait());
                cs.setString(9, utilisateur.getRue());
                cs.setString(10, utilisateur.getCodePostal());
                cs.setString(11, utilisateur.getVille());

                // Exécution de la procédure stockée
                cs.executeUpdate();

                // Exécution
                return utilisateur.getNoUtilisateur();

            } catch (SQLException e)
            {
                throw new RuntimeException(
                        "Erreur lors de la création de l'utilisateur", e
                );
            }
        });
    }

    //méthode pour vérifier si un pseudo existe
    @Override
    public boolean existsUtilisateurByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM UTILISATEURS WHERE pseudo = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count!=null && count>0;
    }

    //méthode pour vérifier si un email existe
    @Override
    public boolean existsUtilisateurByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM UTILISATEURS WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count!=null && count>0;
    }

}

