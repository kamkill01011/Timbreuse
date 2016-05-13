package ch.cpnv.timbreuse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ch.cpnv.timbreuse.beans.User;

import static ch.cpnv.timbreuse.dao.DAOUtility.closeObjects;
import static ch.cpnv.timbreuse.dao.DAOUtility.preparedRequestInitialisation;
import static ch.cpnv.timbreuse.dao.DAOUtility.currentDate;
import static ch.cpnv.timbreuse.dao.DAOUtility.currentTime;

public class DAOImplLogs implements DAOLogs {
	private static final String SQL_INSERT_LOG = "INSERT INTO logs(id,Username,Date,Time,Statut) VALUES(default,?,?,?,?)";

	private DAOFactory daoFactory;

	public DAOImplLogs(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void addLog(User user) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet autoGenValue = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_INSERT_LOG, true, user.getUsername(), currentDate(), currentTime(), "TMP");
			int statut = preparedStatement.executeUpdate();
			if(statut == 0) {
				throw new DAOException("Echec de l'ajout de log.");
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(autoGenValue, preparedStatement, connection);
		}
	}
}
