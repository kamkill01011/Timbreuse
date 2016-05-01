package ch.cpnv.timbreuse.dao;

import ch.cpnv.timbreuse.beans.Student;
import ch.cpnv.timbreuse.beans.User;

public interface DAOStudent {

    /**
     * @param student Nouvel élève à créer
     * @throws DAOException ???
     */
    void create(Student student) throws DAOException;

    /**
     * @param email Adresse mail de l'élève à trouver
     * @return L'élève correspondant à l'adresse mail
     * @throws DAOException ???
     */
    Student find(String email) throws DAOException;

    /**
     * @param student L'élève à supprimer
     * @throws DAOException ???
     */
    void delete(Student student) throws DAOException;

}