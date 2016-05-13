package ch.cpnv.timbreuse.dao;

import ch.cpnv.timbreuse.beans.User;

public interface DAOLogs {
	
	
	/**
	 * Ajoute un log
	 * @param user utilisateur concerné
	 */
	void addLog(User user);
}
