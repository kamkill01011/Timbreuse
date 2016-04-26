package ch.cpnv.timbreuse.dao;

import java.util.ArrayList;

import ch.cpnv.timbreuse.beans.Teacher;
import ch.cpnv.timbreuse.beans.User;

public interface DAOTeacher {
	void createTeacher(Teacher teacher) throws DAOException;
	
	Teacher findTeacher(String email) throws DAOException;
	
	void deleteTeacher(Teacher teacher) throws DAOException;
	
	Teacher findUser(String username) throws DAOException;
	
	ArrayList<User> listClass(String classe) throws DAOException;
}
