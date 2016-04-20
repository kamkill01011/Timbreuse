package ch.cpnv.timbreuse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ch.cpnv.timbreuse.beans.User;
import static ch.cpnv.timbreuse.dao.DAOUtility.preparedRequestInitialisation;
import static ch.cpnv.timbreuse.dao.DAOUtility.closeObjects;

public class DAOImplUser implements DAOUser {
	
	private static final String SQL_SELECT_BY_LASTNAME = "SELECT id, Class, Lastname, Firstname, Email, TimeDiff FROM eleves WHERE Lastname =?";
	private static final String SQL_STUDENT_INSERT = "INSERT INTO eleves(id,Class,Lastname,Firstname,TimeDiff,TodayTime,Status,LastCheck,StartDate,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday,Email,Password) VALUES (default,?,?,?,'0','0',default,null,null,'0','0','0','0','0','0','0',?,null)";
	private static final String SQL_STUDENT_DELETE = "DELETE FROM eleves WHERE Firstname=? AND Lastname=?";
	private DAOFactory daoFactory;
	
	public DAOImplUser(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public User find(String lastname) throws DAOException {
		return find(SQL_SELECT_BY_LASTNAME, lastname);
	}
	
	@Override
	public void create(User user) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet autoGenValue = null;
		
		try {
			connection = daoFactory.getConnection();
			
			preparedStatement = preparedRequestInitialisation(connection, SQL_STUDENT_INSERT, true, user.getClasse(),user.getLastname(),user.getFirstname(),user.getEmail());
			int statut = preparedStatement.executeUpdate();
			if(statut == 0) {
				throw new DAOException("Echec de la création de l'utilisateur, aucune ligne ajoutée dans la table.");
			}
			/*autoGenValue = preparedStatement.getGeneratedKeys();
			if(autoGenValue.next()) {
				user.setId(autoGenValue.getLong(1));
			} else {
			throw new DAOException("Echec de la création de l'utilisateur en base, aucun ID auto-généré retourné.");
			}
			*/
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(autoGenValue, preparedStatement, connection);
		}
	}
	
	@Override
	public void delete(User user) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet autoGenValue = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_STUDENT_DELETE, false, user.getFirstname(), user.getLastname());
			int statut = preparedStatement.executeUpdate();
			if(statut == 0) {
				throw new DAOException("Echec de la suppression.");
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(autoGenValue, preparedStatement, connection);
		}
	}
	
	private User find(String sql, Object... objects) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, sql, false, objects);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				user = map(resultSet);
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(resultSet, preparedStatement, connection);
		}
		return user;
	}
	
	private static User map(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getLong("id"));
		user.setEmail(resultSet.getString("Email"));
		user.setLastname(resultSet.getString("Lastname"));
		user.setFirstname(resultSet.getString("Firstname"));
		user.setTimeDiff(resultSet.getTime("timeDiff"));
        return user;
	}

	
}