package ch.cpnv.timbreuse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jasypt.util.text.BasicTextEncryptor;

import ch.cpnv.timbreuse.beans.User;
import static ch.cpnv.timbreuse.dao.DAOUtility.preparedRequestInitialisation;
import static ch.cpnv.timbreuse.dao.DAOUtility.closeObjects;
import static ch.cpnv.timbreuse.dao.DAOUtility.upperWithoutAccent;

public class DAOImplUsername implements DAOUser {

	private static final String SQL_SELECT_USER_CONNECTION = "SELECT Username, Password, Lastname, PermissionLevel FROM users WHERE Username=?";
	private static final String SQL_SELECT_STUDENT_BY_EMAIL = "SELECT * FROM eleves WHERE Email=?";
	private static final String SQL_SET_NEW_PWD = "UPDATE users SET Password=? WHERE Username=?";
	private DAOFactory daoFactory;

	public DAOImplUsername(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void create(User user) throws DAOException {

	}

	@Override
	public User findUser(String username) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = new User();
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_SELECT_USER_CONNECTION, false, username);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				user.setUsername(resultSet.getString("Username"));
				user.setPassword(resultSet.getString("Password"));
				user.setLastname(resultSet.getString("Lastname"));
				user.setPermissionLevel(resultSet.getInt("PermissionLevel"));
			} 
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(resultSet, preparedStatement, connection);
		}
		return user;
	}

	@Override
	public User findStudent(String email) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = new User();
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_SELECT_STUDENT_BY_EMAIL, false, email+"@cpnv.ch");
			
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
	
	@Override
	public void delete(User user) throws DAOException {
	}

	private static User map(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getLong("id"));
		user.setClasse(resultSet.getString("Class"));
		user.setLastname(resultSet.getString("Lastname"));
		user.setFirstname(resultSet.getString("Firstname"));
		user.setTimeDiff(resultSet.getTime("TimeDiff"));
		user.setTodayTime(resultSet.getTime("TodayTime"));
		user.setStatus(resultSet.getString("Status"));
		user.setLastCheck(resultSet.getDate("LastCheck"));
		user.setStartDate(resultSet.getDate("StartDate"));
		user.setMonday(resultSet.getTime("Monday"));
		user.setTuesday(resultSet.getTime("Tuesday"));
		user.setWednesday(resultSet.getTime("Wednesday"));
		user.setThursday(resultSet.getTime("Thursday"));
		user.setFriday(resultSet.getTime("Friday"));
		user.setSaturday(resultSet.getTime("Saturday"));
		user.setSunday(resultSet.getTime("Sunday"));
		user.setEmail(resultSet.getString("Email"));
		user.setPermissionLevel(3);
        return user;
	}

	@Override
	public User find(String email) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setNewPassword(User user, String newPassword) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		BasicTextEncryptor cryptor = new BasicTextEncryptor();
		cryptor.setPassword("MonGrainDeSel");
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_SET_NEW_PWD, false, cryptor.encrypt(newPassword),user.getUsername());
			int statut = preparedStatement.executeUpdate();
			if(statut == 0) {
				throw new DAOException("Echec du changement de mot de passe.");
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(resultSet, preparedStatement, connection);
		}
	}
}
