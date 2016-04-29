package ch.cpnv.timbreuse.dao;

import ch.cpnv.timbreuse.beans.Student;
import ch.cpnv.timbreuse.beans.User;

public interface DAOStudent {

    void create(Student student) throws DAOException;

    Student find(String email) throws DAOException;
    
    User findUser(String username) throws DAOException;
    
    void delete(Student student) throws DAOException;

}