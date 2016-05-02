package ch.cpnv.timbreuse.dao;

import static ch.cpnv.timbreuse.dao.DAOUtility.closeObjects;
import static ch.cpnv.timbreuse.dao.DAOUtility.preparedRequestInitialisation;
import static ch.cpnv.timbreuse.dao.DAOUtility.generateEmail;
import static ch.cpnv.timbreuse.dao.DAOUtility.generateUsername;
import static ch.cpnv.timbreuse.dao.DAOUtility.randomPassword;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ch.cpnv.timbreuse.beans.Teacher;
import ch.cpnv.timbreuse.beans.User;

public class DAOImplTeacher implements DAOTeacher {
	private static final String SQL_SELECT_PROF_BY_EMAIL = "SELECT * FROM profs WHERE Email=?";
	private static final String SQL_LIST_STUDENTS_BY_CLASS = "SELECT * FROM eleves WHERE Class=?";
	private static final String SQL_INSERT_TEACHER = "INSERT INTO profs(id,Firstname,Lastname,Class,Email) VALUES(default,?,?,?,?)";
	private static final String SQL_INSERT_USER = "INSERT INTO users(id,Username,Password,PermissionLevel,Firstname,Lastname) VALUES(default,?,?,2,?,?)";
	private static final String SQL_DELETE_TEACHER = "DELETE FROM profs WHERE Firstname=? AND Lastname=?";
	private static final String SQL_DELETE_USER = "DELETE FROM users WHERE Firstname=? AND Lastname=? AND PermissionLevel=2";
	
	private DAOFactory daoFactory;
	
	public DAOImplTeacher(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public void createTeacher(Teacher teacher) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		ResultSet autoGenValue = null;
		String firstname = teacher.getFirstname();
		String lastname = teacher.getLastname();
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_INSERT_TEACHER, true, firstname,lastname,teacher.getClasse(),generateEmail(firstname, lastname));
			preparedStatement2 = preparedRequestInitialisation(connection, SQL_INSERT_USER, true, generateUsername(firstname,lastname),randomPassword(),firstname,lastname);
			int statut = preparedStatement.executeUpdate();
			int statut2 = preparedStatement2.executeUpdate();
			if(statut == 0 || statut2 == 0) {
				throw new DAOException("Echec de la création de l'enseignant");
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(autoGenValue, preparedStatement, connection);
		}		
	}

	@Override
	public Teacher findTeacher(String email) throws DAOException {
		return findTeacher(SQL_SELECT_PROF_BY_EMAIL, email);
	}

	@Override
	public void deleteTeacher(Teacher teacher) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		ResultSet autoGenValue = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_DELETE_TEACHER, false, teacher.getFirstname(), teacher.getLastname());
			preparedStatement2 = preparedRequestInitialisation(connection, SQL_DELETE_USER, false, teacher.getFirstname(), teacher.getLastname());
			int statut = preparedStatement.executeUpdate();
			int statut2 = preparedStatement2.executeUpdate();
			if(statut == 0 || statut2 == 0) {
				throw new DAOException("Echec de la suppression.");
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(autoGenValue, preparedStatement, connection);
		}
	}

	@Override
	public Teacher findUser(String username) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Teacher findTeacher(String sql, String email) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Teacher teacher = null;
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, sql, false, email+"@cpnv.ch");
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				teacher = map(resultSet);
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(resultSet, preparedStatement, connection);
		}
		
		return teacher;
	}
		
	private static Teacher map(ResultSet resultSet) throws SQLException {
		Teacher teacher = new Teacher();
		teacher.setId(resultSet.getLong("id"));
		teacher.setFirstname(resultSet.getString("Firstname"));
		teacher.setLastname(resultSet.getString("Lastname"));
		teacher.setClasse(resultSet.getString("Class"));
		teacher.setEmail(resultSet.getString("Email"));
		return teacher;
	}

	@Override
	public ArrayList<User> listClass(String classe) throws DAOException {
		ArrayList<User> list = new ArrayList<User>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_LIST_STUDENTS_BY_CLASS, false, classe);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
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
				list.add(user);
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(resultSet, preparedStatement, connection);
		}
		return list;
	}
}
