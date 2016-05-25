package ch.cpnv.timbreuse.dao;

import java.util.ArrayList;

import ch.cpnv.timbreuse.beans.Holyday;

public interface DAOHolyday {

		void addHolyday(ArrayList<String> date) throws DAOException;
		
		Holyday isHolyday(String holyday) throws DAOException;

		ArrayList<Holyday> getAllHolydays() throws DAOException;
		
		void deleteHolyday(ArrayList<String> holyday) throws DAOException;
}
