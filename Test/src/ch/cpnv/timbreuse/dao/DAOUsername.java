package ch.cpnv.timbreuse.dao;

import ch.cpnv.timbreuse.beans.User;

public interface DAOUsername {

    void create(User user) throws DAOException;

    User find(String email) throws DAOException;
    
    void delete(User user) throws DAOException;

}