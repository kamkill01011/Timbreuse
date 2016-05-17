package ch.cpnv.timbreuse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import org.joda.time.LocalTime;

import ch.cpnv.timbreuse.beans.Student;
import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.mathTime.SecondsPastMidnight;

import static ch.cpnv.timbreuse.dao.DAOUtility.preparedRequestInitialisation;
import static ch.cpnv.timbreuse.dao.DAOUtility.closeObjects;
import static ch.cpnv.timbreuse.dao.DAOUtility.randomPassword;
import static ch.cpnv.timbreuse.dao.DAOUtility.addTime;

public class DAOImplStudent implements DAOStudent {

	private static final String SQL_SELECT_STUDENT_BY_LASTNAME = "SELECT id, Class, Lastname, Firstname, Email, TimeDiff FROM eleves WHERE Lastname =?";
	private static final String SQL_STUDENT_INSERT = "INSERT INTO eleves(id,Class,Lastname,Firstname,TimeDiff,TodayTime,Status,LastCheckDate,LastCheckTime,StartDate,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday,Email) VALUES (default,?,?,?,'0','0',default,null,null,null,'0','0','0','0','0','0','0',?)";
	private static final String SQL_STUDENT_DELETE = "DELETE FROM eleves WHERE Firstname=? AND Lastname=?";
	private static final String SQL_USER_DELETE = "DELETE FROM users WHERE Lastname=? AND Firstname=?";
	private static final String SQL_USER_INSERT = "INSERT INTO users(id,Username,Password,PermissionLevel,Lastname,Firstname) VALUES(default,?,?,3,?,?)"; //PermissionLevel: 1=Admin, 2=profs, 3=eleves
	private static final String SQL_SELECT_STUDENT_BY_EMAIL = "SELECT * FROM eleves WHERE Email=?";
	private static final String SQL_ADD_TIME_STUDENTS = "UPDATE eleves SET TimeDiff=? WHERE id=?";
	private static final String SQL_SELECT_STUDENT_BY_ID = "SELECT * FROM eleves WHERE id=?";
	private static final String SQL_UPDATE_STUDENT_STATUS = "UPDATE eleves SET Status=? WHERE Firstname=? AND Lastname=?";

	private DAOFactory daoFactory;

	public DAOImplStudent(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public Student find(String username) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Student student = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_SELECT_STUDENT_BY_EMAIL, false, username+"@cpnv.ch");
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				student = map(resultSet);
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(resultSet, preparedStatement, connection);
		}

		return student;
	}

	public Student findStudentById(Long id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Student student = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_SELECT_STUDENT_BY_ID, false, id);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				student = map(resultSet);
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(resultSet, preparedStatement, connection);
		}
		return student;
	}

	@Override
	public void create(Student student) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		ResultSet autoGenValue = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_STUDENT_INSERT, true, student.getClasse(),student.getLastname(),student.getFirstname(),student.getEmail());
			preparedStatement2 = preparedRequestInitialisation(connection, SQL_USER_INSERT, true, (student.getFirstname()+"."+student.getLastname()).toLowerCase(),randomPassword(),student.getLastname(),student.getFirstname());
			int statut = preparedStatement.executeUpdate();
			int statut2 = preparedStatement2.executeUpdate();
			if(statut == 0 || statut2 == 0) {
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
	public void delete(Student student) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		ResultSet autoGenValue = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_STUDENT_DELETE, false, student.getFirstname(), student.getLastname());
			preparedStatement2 = preparedRequestInitialisation(connection, SQL_USER_DELETE, false, student.getLastname(),student.getFirstname());
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

	private static Student map(ResultSet resultSet) throws SQLException {
		Student student = new Student();
		student.setId(resultSet.getLong("id"));
		student.setClasse(resultSet.getString("Class"));
		student.setLastname(resultSet.getString("Lastname"));
		student.setFirstname(resultSet.getString("Firstname"));
		student.setTimeDiff(resultSet.getInt("TimeDiff"));
		student.setTodayTime(resultSet.getInt("TodayTime"));
		student.setStatus(resultSet.getString("Status"));
		student.setLastCheckDate(resultSet.getString("LastCheckDate"));
		student.setLastCheckTime(resultSet.getInt("LastCheckTime"));
		student.setStartDate(resultSet.getString("StartDate"));
		student.setMonday(resultSet.getInt("Monday"));
		student.setTuesday(resultSet.getInt("Tuesday"));
		student.setWednesday(resultSet.getInt("Wednesday"));
		student.setThursday(resultSet.getInt("Thursday"));
		student.setFriday(resultSet.getInt("Friday"));
		student.setSaturday(resultSet.getInt("Saturday"));
		student.setSunday(resultSet.getInt("Sunday"));
		student.setEmail(resultSet.getString("Email"));
		return student;
	}

	public void addTimeStudent(Student student, int addedTime) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet autoGenValue = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_ADD_TIME_STUDENTS, false, addTime(addedTime, SecondsPastMidnight.stringToInt(student.getTimeDiff())), student.getId()); //getTImeDIff mod local time
			preparedStatement.executeUpdate();
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(autoGenValue, preparedStatement, connection);

		}
	}

	@Override
	public void changeStatus(Student student, String newStatus) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet autoGenValue = null;
		String firstname = student.getFirstname();
		String lastname = student.getLastname();
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_UPDATE_STUDENT_STATUS, false, newStatus, firstname, lastname);
			preparedStatement.executeUpdate();
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(autoGenValue, preparedStatement, connection);
		}
	}
}