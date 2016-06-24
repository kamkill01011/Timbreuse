package ch.cpnv.timbreuse.dao;

import static ch.cpnv.timbreuse.dao.DAOUtility.closeObjects;
import static ch.cpnv.timbreuse.dao.DAOUtility.preparedRequestInitialisation;
import static ch.cpnv.timbreuse.dao.DAOUtility.generateEmail;
import static ch.cpnv.timbreuse.dao.DAOUtility.generateUsername;
import static ch.cpnv.timbreuse.dao.DAOUtility.randomPassword;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ch.cpnv.timbreuse.beans.Student;
import ch.cpnv.timbreuse.beans.Teacher;

public class DAOImplTeacher implements DAOTeacher {
	private static final String SQL_SELECT_PROF_BY_EMAIL = "SELECT * FROM profs WHERE Email=?";
	private static final String SQL_LIST_STUDENTS_BY_CLASS = "SELECT * FROM eleves WHERE Class=?";
	private static final String SQL_INSERT_TEACHER = "INSERT INTO profs(id,Firstname,Lastname,Class,Email) VALUES(default,?,?,?,?)";
	private static final String SQL_INSERT_USER = "INSERT INTO users(id,Username,Password,PermissionLevel,Firstname,Lastname) VALUES(default,?,?,2,?,?)";
	private static final String SQL_DELETE_TEACHER = "DELETE FROM profs WHERE Firstname=? AND Lastname=?";
	private static final String SQL_DELETE_USER = "DELETE FROM users WHERE Firstname=? AND Lastname=? AND PermissionLevel=2";
	private static final String SQL_SET_NEW_CLASSES = "UPDATE profs SET Class=? WHERE Email=?";
	private static final String SQL_GET_STUDENT_IN_CLASS = "SELECT * FROM eleves WHERE Class=? group by Class";
	
	
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
			preparedStatement2 = preparedRequestInitialisation(connection, SQL_INSERT_USER, true, generateUsername(firstname,lastname),generateUsername(firstname,lastname),firstname,lastname);
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
	public Teacher findTeacher(String username) throws DAOException {
		return findTeacher(SQL_SELECT_PROF_BY_EMAIL, username);
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
	
	private Teacher findTeacher(String sql, String username) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Teacher teacher = null;
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, sql, false, username+"@cpnv.ch");
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
		teacher.setClasse(resultSet.getString("Class").replaceAll("\\s+", ""));
		teacher.setEmail(resultSet.getString("Email"));
		return teacher;
	}

	@Override
	public ArrayList<Student> listClass(String classe, DAOStudent daoStudent) throws DAOException {
		ArrayList<Student> students = new ArrayList<Student>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_LIST_STUDENTS_BY_CLASS, false, classe);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				String email = resultSet.getString("Email");
				students.add(daoStudent.find(email.substring(0, email.indexOf("@"))));
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(resultSet, preparedStatement, connection);
		}
		
		//Ordre les élèves dans l'ordre alphabetique 
		Collections.sort(students, new Comparator<Student>() {
	        @Override
	        public int compare(Student Student1, Student Student2) {
	            return  Student1.getLastname().compareTo(Student2.getLastname());
	        }
	    });
		
		return students;
	}

	@Override
	public void setNewClasses(Teacher teacher, String newClasses) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_SET_NEW_CLASSES, false, newClasses, teacher.getEmail());
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

	public String[] getClasseTimeTable(String classe, DAOStudent daoStudent) throws DAOException {
		String[] timeTable = new String[8];
		Student student = new Student();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_GET_STUDENT_IN_CLASS, false, classe);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				String email = resultSet.getString("Email");
				student = daoStudent.find(email.substring(0, email.indexOf("@")));
				timeTable[0] = classe;
				timeTable[1] = student.getMonday();
				timeTable[2] = student.getTuesday();
				timeTable[3] = student.getWednesday();
				timeTable[4] = student.getThursday();
				timeTable[5] = student.getFriday();
				timeTable[6] = student.getSaturday();
				timeTable[7] = student.getSunday();
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(resultSet, preparedStatement, connection);
		}
		return timeTable;
	}
}
