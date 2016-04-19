package ch.cpnv.timbreuse.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOException;
import ch.cpnv.timbreuse.dao.DAOUser;

public final class StudentResearch {
	private static final String LASTNAME_FIELD = "researchLastname";
	private String result;
	private Map<String, String> errors = new HashMap<String, String>();
	private DAOUser daoUser;
	
	public StudentResearch(DAOUser daoUser) {
		this.daoUser = daoUser;
	}
	
	public Map<String, String> getErreurs() {
        return errors;
    }
	
	public String getResult() {
		return result;
	}
	
	public User researchUser(HttpServletRequest request) {
		String lastname = getFieldValue(request, LASTNAME_FIELD);
		User user = new User();
		try {
			if(errors.isEmpty()) {
				user = daoUser.find(lastname);
				result = "Succès de la recherche.";
			} else {
				result = "Echec de la recherche.";
			}
		} catch(DAOException e) {
			result = "Echec de la recherche: une erreur est survenue, merci de réessayer dans quelques instans.";
			e.printStackTrace();
		}	
		return user;
	}
	
	private static String getFieldValue(HttpServletRequest request, String fieldName) {
		String value = request.getParameter(fieldName);
		if(value == null || value.trim().length() == 0) {
			return null;
		} else {
			return value;
		}
	}
}
