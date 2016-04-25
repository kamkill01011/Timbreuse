package ch.cpnv.timbreuse.dao;

import ch.cpnv.timbreuse.beans.User;

public interface DAOUser {

    void create(User user) throws DAOException;

    User find(String email) throws DAOException;
    
    User findStudent(String email) throws DAOException;
    
    User findUser(String username) throws DAOException;
    
    void delete(User user) throws DAOException;

}