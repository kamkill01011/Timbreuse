package ch.cpnv.timbreuse.dao;

import java.util.ArrayList;

import ch.cpnv.timbreuse.beans.Student;
import ch.cpnv.timbreuse.beans.Teacher;

public interface DAOTeacher {
	
	/**
	 * @param teacher Nouvel enseignat à créer
	 * @throws DAOException ???
	 */
	void createTeacher(Teacher teacher) throws DAOException;
	
	/**
	 * @param email Adresse mail de l'enseignant à trouver
	 * @return L'enseigant correspondant à l'adresse mail
	 * @throws DAOException ???
	 */
	Teacher findTeacher(String email) throws DAOException;
	
	/**
	 * @param teacher l'enseignant à supprimer
	 * @throws DAOException ???
	 */
	void deleteTeacher(Teacher teacher) throws DAOException;
	
	/**
	 * @param username Nom d'utilisateur de l'ensaignant à trouver
	 * @return L'ensaignant correspondant au nom d'utilisateur
	 * @throws DAOException ???
	 */
	Teacher findUser(String username) throws DAOException;
	
	/**
	 * @param classe Nom de la classe à lister
	 * @return Liste de tous les élèves de la classe
	 * @throws DAOException ???
	 */
	ArrayList<Student> listClass(String classe, DAOStudent daoStudent) throws DAOException;
}
