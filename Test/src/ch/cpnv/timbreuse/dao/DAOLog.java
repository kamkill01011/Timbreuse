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

	/**
	 * @param student L'élève
	 * @return Tout les logs de l'élève
	 */
	ArrayList<Log> getStudentLogs(Student student);

	/**
	 * @param student L'élève
	 * @param time Temps à ajouter
	 */
	void addTimeLog(Student student, int time);

	/**
	 * @param student L'élève
	 * @param SubTime Nombre d'heures que l'élève doit effectuer et qui lui seront sourtraies
	 */
	void endDayLog(Student student, String SubTime);
}
