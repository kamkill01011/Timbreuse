package ch.cpnv.timbreuse.dao;

import static ch.cpnv.timbreuse.dao.DAOUtility.closeObjects;
import static ch.cpnv.timbreuse.dao.DAOUtility.preparedRequestInitialisation;
import static ch.cpnv.timbreuse.dao.DAOUtility.addZeroToString;
import static ch.cpnv.timbreuse.dao.DAOUtility.sortDateList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ch.cpnv.timbreuse.beans.Holyday;

public class DAOImplHolyday implements DAOHolyday {
	private static final String SQL_ADD_SINGLE_HOLYDAY = "INSERT INTO holydays(id, Date) VALUES(default, ?)";
	private static final String SQL_SELECT_ALL_HOLYDAYS = "SELECT * FROM holydays";
	private static final String SQL_SELECT_SINGLE_HOLYDAY = "SELECT Date FROM holydays WHERE Date=?";
	private static final String SQL_DELETE_SINGLE_HOLYDAY = "DELETE FROM holydays WHERE Date=?";
	
	private DAOFactory daoFactory;
	
	public DAOImplHolyday(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void addHolyday(ArrayList<String> dateList) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet autoGenValue = null;
		try {
			connection = daoFactory.getConnection();
			for (int i = 0; i < dateList.size(); i++) {
				preparedStatement = preparedRequestInitialisation(connection, SQL_ADD_SINGLE_HOLYDAY, true, addZeroToString(dateList.get(i)));
				int statut = preparedStatement.executeUpdate();	
				if(statut == 0) {
					throw new DAOException("Echec de l'ajout du jour férié.");
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(autoGenValue, preparedStatement, connection);
		}
	}

	@Override
	public ArrayList<Holyday> getAllHolydays() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Holyday> holdaydays = new ArrayList<>();
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_SELECT_ALL_HOLYDAYS, false);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				holdaydays.add(map(resultSet));
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(resultSet, preparedStatement, connection);
		}
		return sortDateList(holdaydays);
	}

	private static Holyday map(ResultSet resultSet) throws SQLException {
		Holyday holyday = new Holyday();
		holyday.setId(resultSet.getLong("id"));
		holyday.setDate(resultSet.getString("Date"));
		return holyday;
	}

	@Override
	public Holyday isHolyday(String holyday) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Holyday isHolyday = null;
		try {
			connection = daoFactory.getConnection();
			preparedStatement = preparedRequestInitialisation(connection, SQL_SELECT_SINGLE_HOLYDAY, false, holyday);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				isHolyday = new Holyday();
				isHolyday.setDate(resultSet.getString("Date"));
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(resultSet, preparedStatement, connection);
		}
		return isHolyday;
	}

	@Override
	public void deleteHolyday(ArrayList<String> dateList) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet autoGenValue = null;
		try {
			connection = daoFactory.getConnection();
			for (int i = 0; i < dateList.size(); i++) {
				preparedStatement = preparedRequestInitialisation(connection, SQL_DELETE_SINGLE_HOLYDAY, false, addZeroToString(dateList.get(i)));
				preparedStatement.executeUpdate();				
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		} finally {
			closeObjects(autoGenValue, preparedStatement, connection);
		}
	}
	
	
}
