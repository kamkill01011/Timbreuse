package ch.cpnv.timbreuse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ch.cpnv.timbreuse.beans.User;
import static ch.cpnv.timbreuse.dao.DAOUtility.preparedRequestInitialisation;
import static ch.cpnv.timbreuse.dao.DAOUtility.closeObjects;

public class DAOImplUsername implements DAOUser {

	private static final String SQL_SELECT_USER_CONNECTION = "SELECT Username, Password, Lastname FROM users WHERE Username=?";
	private DAOFactory daoFactory;

	public DAOImplUsername(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void create(User user) throws DAOException {

	}

	@Override
	public User find(String username) throws DAOException {
		return find(SQL_SELECT_USER_CONNECTION, username);
	}

	@Override
	public void delete(User user) throws DAOException {
	}

	private User find(String sql, Object...objects) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_SELECT_USER_CONNECTION, false, objects);
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
		user.setUsername(resultSet.getString("Username"));
		user.setPassword(resultSet.getString("Password"));
		user.setLastname(resultSet.getString("Lastname"));
		return user;
	}
}
