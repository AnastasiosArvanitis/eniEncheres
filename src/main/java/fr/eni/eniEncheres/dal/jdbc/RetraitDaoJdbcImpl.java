package fr.eni.eniEncheres.dal.jdbc;

import fr.eni.eniEncheres.bo.Retrait;
import fr.eni.eniEncheres.dal.DalException;
import fr.eni.eniEncheres.dal.dao.RetraitDao;
import fr.eni.eniEncheres.dal.jdbcTools.JdbcConnection;
import fr.eni.eniEncheres.tools.EnchereLogger;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RetraitDaoJdbcImpl implements RetraitDao {

    private Logger logger = EnchereLogger.getLogger("RetraitDaoJdbcImpl");

    @Override
    public Retrait selectRetraitById(int retraitId) throws SQLException, DalException {
        Retrait retrait = null;
        final String SELECT_BY_ID = "select * from RETRAITS where id = ?";

        try {
            Connection connection = JdbcConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, retraitId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                retrait = retraitBuilder(resultSet);
            }
        } catch(SQLException e){
            logger.severe("Error method selectRetraitById " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);
        }

        return retrait;
    }

    @Override
    public Retrait insertRetrait(Retrait ajoutRetrait) throws SQLException, DalException {
        Retrait retraitCree = null;
        final String SQL_INSERT = "insert into RETRAITS (rue, codePostal, ville) values (?,?,?)";
        int idAjout = 0;

        try {
            Connection connection = JdbcConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, ajoutRetrait.getRue());
            preparedStatement.setString(2, ajoutRetrait.getCodePostal());
            preparedStatement.setString(3, ajoutRetrait.getVille());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                idAjout = resultSet.getInt(1);
                retraitCree = selectRetraitById(idAjout);
            }
        } catch(SQLException e){
            logger.severe("Error method insertRetrait " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);
        }
        return retraitCree;
    }

    public Retrait updateRetrait(Retrait modifRetrait) throws  DalException {
        Retrait retraitCree = null;
        final String SQL_UPDATE = "UPDATE RETRAITS SET rue = ? , codePostal = ? , ville = ? WHERE id = ?";

        try {
            Connection connection = JdbcConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, modifRetrait.getRue());
            preparedStatement.setString(2, modifRetrait.getCodePostal());
            preparedStatement.setString(3, modifRetrait.getVille());
            preparedStatement.setInt(4,modifRetrait.getId());
            preparedStatement.executeUpdate();

            retraitCree = selectRetraitById(modifRetrait.getId());

        } catch(SQLException e){
            logger.severe("Error method UpdateRetrait " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);
        }
        return retraitCree;
    }

    /**
     *
     * @param deleteRetrait
     * Récupère un boolean si l'execution du delete est ok
     * @return
     * @throws DalException
     */
    public boolean deleteRetrait(Retrait deleteRetrait) throws  DalException {
        boolean effacerRetrait = false;
        final String SQL_DELETE = "DELETE RETRAITS WHERE ID = ?";

        try {
            Connection connection = JdbcConnection.connect();
            PreparedStatement  stmt= connection.prepareStatement(SQL_DELETE);
            stmt.setInt(1,deleteRetrait.getId());
            effacerRetrait = !( stmt.executeUpdate()==0);

        } catch(SQLException e){
            logger.severe("Error method DeleteRetrait " + e.getMessage() + "\n");
            throw new DalException(e.getMessage(), e);
        }
        return effacerRetrait;
    }

    public boolean validerRetrait(int numeroRetrait) throws DalException, SQLException {
        boolean retraitValider = false;
        System.out.println("entre dans la dao");
        final String SQL_UPDATE_RETRAIT = "UPDATE RETRAITS SET retrait =1 WHERE id =?  and retrait <>1";
        System.out.println(SQL_UPDATE_RETRAIT);

        try(Connection connection = JdbcConnection.connect()){
            PreparedStatement stmt = connection.prepareStatement(SQL_UPDATE_RETRAIT);
            stmt.setInt(1,numeroRetrait);
            retraitValider = (!(stmt.executeUpdate() == 0));
        }return  retraitValider;
    }


    private Retrait retraitBuilder(ResultSet rs) throws SQLException {
        Retrait retrait = new Retrait();
        retrait.setId(rs.getInt("id"));
        retrait.setRue(rs.getString("rue"));
        retrait.setCodePostal(rs.getString("codePostal"));
        retrait.setVille(rs.getString("ville"));
        retrait.setRetrait(rs.getBoolean("retrait"));

        return retrait;
    }

}
