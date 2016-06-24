package ch.cpnv.timbreuse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jasypt.util.text.BasicTextEncryptor;

import ch.cpnv.timbreuse.beans.Student;
import ch.cpnv.timbreuse.beans.Teacher;
import ch.cpnv.timbreuse.beans.User;
import static ch.cpnv.timbreuse.dao.DAOUtility.preparedRequestInitialisation;
import static ch.cpnv.timbreuse.dao.DAOUtility.closeObjects;


public class DAOImplUser implements DAOUser {

	private static final String SQL_SELECT_USER_CONNECTION = "SELECT * FROM users WHERE Username=?";
	private static final String SQL_SELECT_STUDENT_BY_EMAIL = "SELECT * FROM eleves WHERE Email=?";
	private static final String SQL_SET_NEW_PWD = "UPDATE users SET Password=? WHERE Username=?";
	private static final String SQL_SELECT_LIST_PASSWORD_BY_CLASS = "SELECT * FROM users WHERE Firstname=? AND Lastname=? AND CHAR_LENGTH(Password) < 8";
	private static final String SQL_LIST_TEACHERS = "SELECT * FROM profs";
	
	private DAOFactory daoFactory;

	public DAOImplUser(DAOFactory daoFactory) {
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
	public Student findStudent(String username, DAOStudent daoStudent) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Student student = new Student();
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_SELECT_STUDENT_BY_EMAIL, false, username+"@cpnv.ch");
			
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				student = daoStudent.find(username);
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(resultSet, preparedStatement, connection);
		}
		return student;
	}
	
	@Override
	public void delete(User user) throws DAOException {
	}

	private static User map(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getLong("id"));
		user.setUsername(resultSet.getString("Username"));
		user.setPassword(resultSet.getString("Password"));
		user.setLastname(resultSet.getString("Lastname"));
		user.setFirstname(resultSet.getString("Firstname"));
		user.setPermissionLevel(resultSet.getInt("PermissionLevel"));
        return user;
	}

	@Override
	public void setNewPassword(User user, String newPassword, boolean encrypt) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		BasicTextEncryptor cryptor = new BasicTextEncryptor();
		cryptor.setPassword("MonGrainDeSel");
		if(encrypt) newPassword = cryptor.encrypt(newPassword);
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_SET_NEW_PWD, false, newPassword,user.getUsername());
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
	
	public User getDefaultPassword(String firstname, String lastname) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_SELECT_LIST_PASSWORD_BY_CLASS, false, firstname, lastname);
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

	public ArrayList<Teacher> listTeachers(DAOTeacher daoTeacher) throws DAOException {
		ArrayList<Teacher> list = new ArrayList<Teacher>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_LIST_TEACHERS, false);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				String email = resultSet.getString("Email");
				list.add(daoTeacher.findTeacher(email.substring(0, email.indexOf("@"))));
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(resultSet, preparedStatement, connection);
		}
		return list;
	}
}
