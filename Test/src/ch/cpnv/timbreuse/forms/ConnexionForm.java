package ch.cpnv.timbreuse.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOException;
import ch.cpnv.timbreuse.dao.DAOUser;

public class ConnexionForm {
	private static final String EMAIL_FIELD = "email";
	private static final String PASSWORD_FIELD = "password";
	private String result;
	private Map<String, String> errors = new HashMap<String, String>();
	private DAOUser daoUser;
	
	public ConnexionForm(DAOUser daoUser) {
		this.daoUser = daoUser;
	}
	
	public Map<String, String> getErreurs() {
        return errors;
    }
	
	public String getResult() {
		return result;
	}
	
	public User userConnexion(HttpServletRequest request) {
		String email = getFieldValue(request, EMAIL_FIELD);
		String password = getFieldValue(request, PASSWORD_FIELD);
		User user = new User();
		try {
			if(errors.isEmpty()) {
				//vérifier les identifiants
			}
		} catch(DAOException e) {
			result = "Echec de la création.";
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
