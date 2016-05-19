package ch.cpnv.timbreuse.dao;

import java.util.ArrayList;

import ch.cpnv.timbreuse.beans.Holyday;

public class DAOImplHolyday implements DAOHolyday {
	
	private DAOFactory daoFactory;
	
	public DAOImplHolyday(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void addHolyday(String date) throws DAOException {
		
		
	}

	@Override
	public ArrayList<Holyday> getHolydays() throws DAOException {
		
		return null;
	}
	
	
}
