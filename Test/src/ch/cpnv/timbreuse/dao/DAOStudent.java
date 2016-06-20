package ch.cpnv.timbreuse.dao;

import java.util.ArrayList;

import ch.cpnv.timbreuse.beans.Student;

public interface DAOStudent {

    /**
     * @param student Nouvel élève à créer
     */
    void create(Student student, DAOTeacher daoTeacher) throws DAOException;

    /**
     * @param username Nom d'utilisateur de l'élève à trouver
     * @return L'élève correspondant à l'adresse mail
     */
    Student find(String username) throws DAOException;

    /**
     * @param ID du tag de l'élève à trouver
     * @return L'élève correspondant à l'adresse mail
     */
    Student findByTag(String tag) throws DAOException;
    
    /**
     * @param ID de l'élève à trouver
     * @return L'élève correspondant à l'adresse mail
     */
    Student findStudentById(Long id);
    
    /**
     * @param student L'élève à supprimer
     */
    void delete(Student student) throws DAOException;

    /**
     * @param student L'élève à modifier
     * @param addedTime nouveau temps à ajouter
     */
	void addTimeStudent(Student student, int addedTime) throws DAOException;// Pourquoi pas dans linterface ?

    /**
     * Modifie la colonne statut. (DEP => ARR, ARR => DEP)
     * @param student eleve concerné
     */
    void changeStatus(Student student, String newStatus) throws DAOException;
    
    /**
     * Modifie la l'ID du tag de l'élève
     * @param student eleve concerné
     */
    void changeTag(Student student, String newTag) throws DAOException;

	ArrayList<Student>getNotCheckedOutStudents();

	void resetTodayTime(Student student);

	int setTimeDiff(Student student);

	void changeTimeTables(int[] newTImeTable, Student student);

}