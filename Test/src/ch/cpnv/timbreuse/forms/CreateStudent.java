package ch.cpnv.timbreuse.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOException;
import ch.cpnv.timbreuse.dao.DAOUser;

public class CreateStudent {
	private static final String CLASS_FIELD = "addClass";
	private static final String FIRSTNAME_FIELD = "addFirstname";
	private static final String LASTNAME_FIELD = "addLastname";
	private String result;
	private Map<String, String> errors = new HashMap<String, String>();
	private DAOUser daoUser;
	
	public CreateStudent(DAOUser daoUser) {
		this.daoUser = daoUser;
	}
	
	public Map<String, String> getErreurs() {
        return errors;
    }
	
	public String getResult() {
		return result;
	}
	
	public User isUser(HttpServletRequest request) {
		String lastname = getFieldValue(request, LASTNAME_FIELD);
		User user = new User();
		try {
			if(errors.isEmpty()) {
				user.setClasse(getFieldValue(request, CLASS_FIELD));
				user.setFirstname(getFieldValue(request, FIRSTNAME_FIELD));
				user.setLastname(getFieldValue(request, LASTNAME_FIELD));
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
