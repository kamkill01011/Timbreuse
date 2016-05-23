package ch.cpnv.timbreuse.dao;

import static ch.cpnv.timbreuse.dao.DAOUtility.closeObjects;
import static ch.cpnv.timbreuse.dao.DAOUtility.preparedRequestInitialisation;
import static ch.cpnv.timbreuse.mathTime.Date.fixedToDate;
import static ch.cpnv.timbreuse.mathTime.Date.stringToDate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.crypto.CipherInputStream;

import ch.cpnv.timbreuse.beans.Holyday;
import ch.cpnv.timbreuse.beans.Student;
import javafx.scene.shape.CircleBuilder;

public class DAOImplHolyday implements DAOHolyday {
	private static final String SQL_ADD_SINGLE_HOLYDAY = "INSERT INTO holydays(id, Date) VALUES(default, ?)";
	private static final String SQL_SELECT_ALL_HOLYDAYS = "SELECT * FROM eleves";
	
	private DAOFactory daoFactory;
	
	public DAOImplHolyday(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void addHolyday(ArrayList<Integer> dateList) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet autoGenValue = null;
		try {
			connection = daoFactory.getConnection();
			for (int i = 0; i < dateList.size(); i++) {
				preparedStatement = preparedRequestInitialisation(connection, SQL_ADD_SINGLE_HOLYDAY, true, fixedToDate(dateList.get(i)).toString());
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
		return holdaydays;
	}

	private static Holyday map(ResultSet resultSet) throws SQLException {
		Holyday holyday = new Holyday();
		holyday.setId(resultSet.getLong("id"));
		holyday.setDate(stringToDate(resultSet.getString("Date")).fixed());
		return holyday;
	}

	@Override
	public ArrayList<Holyday> getHolydays() throws DAOException {
		
		return null;
	}
	
	
}
