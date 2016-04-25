package ch.cpnv.timbreuse.dao;

import ch.cpnv.timbreuse.beans.User;

public interface DAOUsername {

    void create(User user) throws DAOException;

    User findUser(String username) throws DAOException;
    
    User findStudent(User user) throws DAOException;
    
    void delete(User user) throws DAOException;

}