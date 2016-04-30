package ch.cpnv.timbreuse.dao;

import ch.cpnv.timbreuse.beans.User;

public interface DAOUser {

    /**
     * @param user Nouvel utilisateur à créer
     * @throws DAOException ???
     */
    void create(User user) throws DAOException;
    
    /**
     * @param email Adresse mail de l'élève à trouver
     * @return L'élève correspondant à l'adresse mail
     * @throws DAOException ???
     */
    User findStudent(String email) throws DAOException;
    
    /**
     * @param username Nom d'utilisateur à trouver
     * @return L'utilisateur correspondant au nom d'utilisateur
     * @throws DAOException ???
     */
    User findUser(String username) throws DAOException;
    
    /**
     * @param user L'utilisateur à supprimer
     * @throws DAOException ???
     */
    void delete(User user) throws DAOException;
    
    /**
     * @param user L'utilisateur
     * @param newPassord Le nouveau mot de passe
     * @throws DAOException ???
     */
    void setNewPassword(User user, String newPassord) throws DAOException;

}