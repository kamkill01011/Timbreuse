package ch.cpnv.timbreuse.dao;

import static ch.cpnv.timbreuse.dao.DAOUtility.closeObjects;
import static ch.cpnv.timbreuse.dao.DAOUtility.preparedRequestInitialisation;
import static ch.cpnv.timbreuse.dao.DAOUtility.generateUsername;
import static ch.cpnv.timbreuse.dao.DAOUtility.randomPassword;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ch.cpnv.timbreuse.beans.Student;
import ch.cpnv.timbreuse.beans.User;

public class DAOImplAdmin implements DAOUser {
	private static final String SQL_INSERT_ADMIN = "INSERT INTO users(id,Username,Password,PermissionLevel,Firstname,Lastname) VALUES(default,?,?,1,?,?)";
	private static final String SQL_DELETE_ADMIN = "DELETE FROM users WHERE Firstname=? AND Lastname=? AND PermissionLevel=1";
	private DAOFactory daoFactory;
	
	public DAOImplAdmin(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public void create(User user) throws DAOException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet autoGenValue = null;
		String firstname = user.getFirstname();
		String lastname = user.getLastname();
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_INSERT_ADMIN, true, generateUsername(firstname, lastname),randomPassword(),firstname,lastname);
			int statut = preparedStatement.executeUpdate();
			if(statut == 0) {
				throw new DAOException("Echec de la création de l'admin.");
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(autoGenValue, preparedStatement, connection);
		}
	}
	
	@Override
	public Student findStudent(String username, DAOStudent daoStudent) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public User findUser(String username) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void delete(User user) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet autoGenValue = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_DELETE_ADMIN, false, user.getFirstname(), user.getLastname());
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
	
	@Override
	public void setNewPassword(User user, String newPassord) throws DAOException {
		// TODO Auto-generated method stub	
	}
}
