package fr.eni.projetencheres.dal;

import fr.eni.projetencheres.bo.Retrait;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RetraitRepositoryImpl implements RetraitRepository{

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RetraitRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate.getJdbcTemplate();
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Retrait findRetraitById(int id) {
        Retrait retrait;

        String sql = "SELECT * FROM RETRAIT WHERE id_retrait = ?";

        retrait = jdbcTemplate.queryForObject(sql, new RetraitRowMapper(),id);

        return retrait;
    }

    @Override
    public Retrait createRetrait(Retrait retrait) {
        String sql = "INSERT INTO RETRAIT (rue, code_postal, ville) VALUES (:rue, :code_postal, :ville)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("rue", retrait.getRue());
        parameterSource.addValue("code_postal", retrait.getCodePostal());
        parameterSource.addValue("ville", retrait.getVille());

        namedParameterJdbcTemplate.update(sql, parameterSource, keyHolder, new String[] {"idRetrait"});
        retrait.setIdRetrait(keyHolder.getKey().intValue());

        return retrait;
    }

    @Override
    public List<Retrait> findRetraitsByUserId(int noUtilisateur) {
        String sql = "SELECT * FROM RETRAIT ret\n" +
                "INNER JOIN ADRESSES adr\n" +
                "ON ret.id_retrait = adr.id_retrait WHERE adr.no_utilisateur = ?";

        List<Retrait> retraits = jdbcTemplate.query(sql, new RetraitRowMapper(), noUtilisateur);

        return retraits;
    }

    @Override
    public void lierRetraitUtilisateur(int noUtilisateur, int idRetrait) {
        String sql = "INSERT INTO ADRESSES (id_retrait, no_utilisateur) values (:idRetrait, :noUtilisateur)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("idRetrait", idRetrait);
        parameterSource.addValue("noUtilisateur", noUtilisateur);

        //void donc juste un INSERT, aucun return
        namedParameterJdbcTemplate.update(sql, parameterSource);
    }


    //ROWMAPPER
    class RetraitRowMapper implements RowMapper<Retrait> {

        @Override
        public Retrait mapRow(ResultSet rs, int rowNum) throws SQLException {
            Retrait retrait = new Retrait();
            retrait.setIdRetrait(rs.getInt("id_retrait"));
            retrait.setRue(rs.getString("rue"));
            retrait.setCodePostal(rs.getString("code_postal"));
            retrait.setVille(rs.getString("ville"));
            return retrait;
        }
    }
}
