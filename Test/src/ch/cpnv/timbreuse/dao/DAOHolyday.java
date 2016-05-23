package ch.cpnv.timbreuse.dao;

import java.util.ArrayList;

import ch.cpnv.timbreuse.beans.Holyday;
import ch.cpnv.timbreuse.beans.Student;

public interface DAOHolyday {

		void addHolyday(ArrayList<Integer> date) throws DAOException;
		
		ArrayList<Holyday> getHolydays() throws DAOException;

		ArrayList<Holyday> getAllHolydays();
}
