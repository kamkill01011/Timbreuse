package ch.cpnv.timbreuse.dao;

import static ch.cpnv.timbreuse.dao.DAOUtility.closeObjects;
import static ch.cpnv.timbreuse.dao.DAOUtility.preparedRequestInitialisation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.crypto.CipherInputStream;

import ch.cpnv.timbreuse.beans.Holyday;
import javafx.scene.shape.CircleBuilder;

public class DAOImplHolyday implements DAOHolyday {
	private static final String SQL_ADD_SINGLE_HOLYDAY = "INSERT INTO holydays(id, Date) VALUES(default, ?)";
	
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
				preparedStatement = preparedRequestInitialisation(connection, SQL_ADD_SINGLE_HOLYDAY, true, dateList.get(i));
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
	public ArrayList<Holyday> getHolydays() throws DAOException {
		
		return null;
	}
	
	
}
