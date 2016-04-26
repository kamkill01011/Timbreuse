package ch.cpnv.timbreuse.dao;

import static ch.cpnv.timbreuse.dao.DAOUtility.closeObjects;
import static ch.cpnv.timbreuse.dao.DAOUtility.preparedRequestInitialisation;

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
	
	private DAOFactory daoFactory;
	
	public DAOImplTeacher(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public void createTeacher(Teacher teacher) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Teacher findTeacher(String email) throws DAOException {
		return findTeacher(SQL_SELECT_PROF_BY_EMAIL, email);
	}

	@Override
	public void deleteTeacher(Teacher teacher) throws DAOException {
		// TODO Auto-generated method stub
		
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
