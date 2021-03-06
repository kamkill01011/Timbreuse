package ch.cpnv.timbreuse.dao;

import java.util.ArrayList;

import ch.cpnv.timbreuse.beans.Log;
import ch.cpnv.timbreuse.beans.Student;

public interface DAOLog {
	
	
	/**
	 * Ajoute un log et retourne le nouveau status (DEP, ARR)
	 * @param student eleve concerné
	 */
	String addLog(Student student) throws DAOException;
	
	/**
	 * Retourne le statut de l'eleve
	 * @param student eleve concerné
	 * @return statut de l'utilisateur
	 */
	String getNewStatus(Student student) throws DAOException;

	ArrayList<Log> getStudentLogs(Student student);

	String addTimeLog(Student student, int time);
}
