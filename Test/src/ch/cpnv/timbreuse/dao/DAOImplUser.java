package ch.cpnv.timbreuse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ch.cpnv.timbreuse.beans.User;
import static ch.cpnv.timbreuse.dao.DAOUtility.preparedRequestInitialisation;
import static ch.cpnv.timbreuse.dao.DAOUtility.closeObjects;


public class DAOImplUser implements DAOUser {
	
	private static final String SQL_SELECT_BY_LASTNAME = "SELECT id, Class, Lastname, Firstname, Email, TimeDiff FROM eleves WHERE Lastname = ?";
	
	private DAOFactory daoFactory;
	
	public DAOImplUser(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public User find(String Lastname) throws DAOException {
		return find(SQL_SELECT_BY_LASTNAME, Lastname);
	}
	
	@Override
	public void create(User user) throws DAOException {
		
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
		user.setId( resultSet.getLong("id"));
		user.setEmail( resultSet.getString("Email"));
		user.setName( resultSet.getString("Lastname"));
        return user;
	}
}