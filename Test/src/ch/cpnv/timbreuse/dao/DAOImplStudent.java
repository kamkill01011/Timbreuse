package ch.cpnv.timbreuse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ch.cpnv.timbreuse.beans.Student;
import ch.cpnv.timbreuse.mathTime.SecondsPastMidnight;

import static ch.cpnv.timbreuse.dao.DAOUtility.preparedRequestInitialisation;
import static ch.cpnv.timbreuse.dao.DAOUtility.closeObjects;
import static ch.cpnv.timbreuse.dao.DAOUtility.randomPassword;
import static ch.cpnv.timbreuse.dao.DAOUtility.addTime;
import static ch.cpnv.timbreuse.dao.DAOUtility.currentDate;
import static ch.cpnv.timbreuse.dao.DAOUtility.currentTime;
import static ch.cpnv.timbreuse.dao.DAOUtility.generateUsername;
import static ch.cpnv.timbreuse.mathTime.Date.stringToDate;
import static ch.cpnv.timbreuse.automation.Automation.updateTime;

public class DAOImplStudent implements DAOStudent {

	private static final String SQL_STUDENT_INSERT = "INSERT INTO eleves(id,Class,Lastname,Firstname,TimeDiff,TodayTime,Status,LastCheckDate,LastCheckTime,StartDate,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday,Email) VALUES (default,?,?,?,'0','0',default,null,null,null,'25200','25200','0','25200','14400','0','0',?)";
	private static final String SQL_STUDENT_DELETE = "DELETE FROM eleves WHERE Firstname=? AND Lastname=?";
	private static final String SQL_USER_DELETE = "DELETE FROM users WHERE Lastname=? AND Firstname=?";
	private static final String SQL_USER_INSERT = "INSERT INTO users(id,Username,Password,PermissionLevel,Lastname,Firstname) VALUES(default,?,?,3,?,?)"; //PermissionLevel: 1=Admin, 2=profs, 3=eleves
	private static final String SQL_SELECT_STUDENT_BY_EMAIL = "SELECT * FROM eleves WHERE Email=?";
	private static final String SQL_ADD_TIME_STUDENTS = "UPDATE eleves SET TimeDiff=? WHERE id=?";
	private static final String SQL_SELECT_STUDENT_BY_ID = "SELECT * FROM eleves WHERE id=?";
	private static final String SQL_UPDATE_LASTCHECK_STATUS_STUDENT = "UPDATE eleves SET Status=?, LastCheckDate=?, LastCheckTime=? WHERE Firstname=? AND Lastname=?";
	private static final String SQL_UPDATE_LASTCHECK_STATUS_STUDENT_DEP = "UPDATE eleves SET Status=?, LastCheckDate=?, LastCheckTime=?, TimeDiff=?, TodayTime=? WHERE Firstname=? AND Lastname=?";
	private static final String SQL_FRACTION_SELECT_DAY_OF_WEEK_TIME = " FROM eleves WHERE Firstname=? AND Lastname=?";
	private static final String SQL_SELECT_ALL_STUDENTS = "SELECT * FROM eleves";
	private static final String SQL_RESET_TODAYTIME = "UPDATE eleves SET TodayTime='0' WHERE id=?";
	private static final String SQL_SET_TIMEDIFF = "UPDATE eleves SET TimeDiff=? WHERE id=?";

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
			preparedStatement2 = preparedRequestInitialisation(connection, SQL_USER_INSERT, true, generateUsername(student.getFirstname(),student.getLastname()),randomPassword(),student.getLastname(),student.getFirstname());
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
		String firstname = student.getFirstname();//avec ID et pas nom + prénom
		String lastname = student.getLastname();
		int[] updatedTime = {0, 0};
		if(newStatus.equals("DEP")) {
			updatedTime = updateTime(student.getTimeDiff(), student.getTodayTime(), student.getLastCheckTime(), currentTime());
		}
		try {
			connection = daoFactory.getConnection();
			if(newStatus.equals("DEP")) {
				preparedStatement = preparedRequestInitialisation(connection, SQL_UPDATE_LASTCHECK_STATUS_STUDENT_DEP, false, newStatus, currentDate(), currentTime(), updatedTime[0], updatedTime[1], firstname, lastname);
			} else {
				preparedStatement = preparedRequestInitialisation(connection, SQL_UPDATE_LASTCHECK_STATUS_STUDENT, false, newStatus, currentDate(), currentTime(), firstname, lastname);
			}
			preparedStatement.executeUpdate();
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(autoGenValue, preparedStatement, connection);
		}
	}
	
	public int getDayOfWeekTimetable(Student student) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int result = 0;
		String firstname = student.getFirstname();
		String lastname = student.getLastname();
		String dayOfWeek = stringToDate(currentDate()).dayOfWeek();
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, "SELECT "+dayOfWeek+SQL_FRACTION_SELECT_DAY_OF_WEEK_TIME, false, firstname, lastname);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				result = resultSet.getInt(dayOfWeek);
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(resultSet, preparedStatement, connection);
		}
		return result;
	}

	@Override
	public ArrayList<Student> getNotCheckedOutStudents() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Student> students = new ArrayList<>();
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_SELECT_ALL_STUDENTS, false);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				students.add(map(resultSet));
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(resultSet, preparedStatement, connection);
		}
		return students;
	}
	
	@Override
	public void resetTodayTime(Student student) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet autoGenValue = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_RESET_TODAYTIME, false, student.getId());
			preparedStatement.executeUpdate();
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(autoGenValue, preparedStatement, connection);

		}
	}
	
	@Override
	public void setTimeDiff(Student student) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet autoGenValue = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_SET_TIMEDIFF, false, SecondsPastMidnight.stringToInt(student.getTimeDiff()) - getDayOfWeekTimetable(student), student.getId());
			preparedStatement.executeUpdate();
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(autoGenValue, preparedStatement, connection);
		}
	}
}
