package ch.cpnv.timbreuse.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ch.cpnv.timbreuse.beans.Log;
import ch.cpnv.timbreuse.beans.Student;
import ch.cpnv.timbreuse.beans.User;

import static ch.cpnv.timbreuse.dao.DAOUtility.closeObjects;
import static ch.cpnv.timbreuse.dao.DAOUtility.preparedRequestInitialisation;
import static ch.cpnv.timbreuse.dao.DAOUtility.currentDate;
import static ch.cpnv.timbreuse.dao.DAOUtility.currentTime;
import static ch.cpnv.timbreuse.dao.DAOUtility.generateUsername;

public class DAOImplLog implements DAOLog {
	private static final String SQL_INSERT_LOG = "INSERT INTO logs(id,Username,Date,Time,Status) VALUES(default,?,?,?,?)";
	private static final String SQL_SELECT_STATUS = "SELECT Status FROM eleves WHERE Firstname=? AND Lastname=?";
	private static final String SQL_SELECT_STUDENT_LOGS = "SELECT id,Username,Date,Time,Status FROM logs WHERE Username=?";

	private DAOFactory daoFactory;

	public DAOImplLog(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public String addLog(Student student) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet autoGenValue = null;
		String firstname = student.getFirstname();
		String lastname = student.getLastname();
		String newStatus = getNewStatus(student);
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_INSERT_LOG, true, generateUsername(firstname, lastname), currentDate(), currentTime(), newStatus);
			int statut = preparedStatement.executeUpdate();
			if(statut == 0) {
				throw new DAOException("Echec de l'ajout de log.");
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(autoGenValue, preparedStatement, connection);
		}
		return newStatus;
	}

	@Override
	public  String getNewStatus(Student student) {
		String firstname = student.getFirstname();
		String lastname = student.getLastname();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String status = null;
		
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_SELECT_STATUS, false, firstname, lastname);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				status = resultSet.getString("Status");
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(resultSet, preparedStatement, connection);
		}
		return inverseStatus(status);
	}
	
	private String inverseStatus(String actualStatus) {
		if(actualStatus.equals("DEP")) {
			return "ARR";
		} else if(actualStatus.equals("ARR")) {
			return "DEP";
		}
		return "ERR";
	}
	
	@Override
	public ArrayList<Log> getStudentLogs(Student student) {
		String firstname = student.getFirstname();
		String lastname = student.getLastname();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Log> logs = new ArrayList<>();
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_SELECT_STUDENT_LOGS, false, generateUsername(firstname, lastname));
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				logs.add(map(resultSet));
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(resultSet, preparedStatement, connection);
		}
		return logs;
	}

	private static Log map(ResultSet resultSet) throws SQLException {
		Log log = new Log();
		log.setId(resultSet.getLong("id"));
		log.setUsername(resultSet.getString("Username"));
		log.setDate(resultSet.getString("Date"));
		log.setTime(resultSet.getInt("Time"));
		log.setStatus(resultSet.getString("Status"));
		return log;
	}
}
