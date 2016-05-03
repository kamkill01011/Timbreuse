package ch.cpnv.timbreuse.dao;

import java.sql.Time;

import ch.cpnv.timbreuse.beans.Student;
import ch.cpnv.timbreuse.beans.User;

public interface DAOStudent {

    /**
     * @param student Nouvel élève à créer
     */
    void create(Student student) throws DAOException;

    /**
     * @param email Adresse mail de l'élève à trouver
     * @return L'élève correspondant à l'adresse mail
     */
    Student find(String email) throws DAOException;

    /**
     * @param student L'élève à supprimer
     */
    void delete(Student student) throws DAOException;

    
    /**
     * @param id de l'élève en base de données
     * @param addedTime, nouveau temps à ajouter
     */
   // void addTimeStudent(int id, Time addedTime) throws DAOException;
}