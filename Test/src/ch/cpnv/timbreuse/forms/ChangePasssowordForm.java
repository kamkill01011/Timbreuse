package ch.cpnv.timbreuse.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor.SetterOnlyReflection;

import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOUser;

public class ChangePasssowordForm {
	private static final String CURRENT_PWD_FIELD = "old";
	private static final String NEW_PWD_FIELD = "new";
	private static final String NEW_PWD_CONFIRM_FIELD = "confirm";
	private String result;
	private Map<String, String> errors      = new HashMap<String, String>();
	private DAOUser daoUser;
	
	public ChangePasssowordForm(DAOUser daoUser) {
		this.daoUser = daoUser;
	}
	
	public String getResult() {
		return result;
	}
	
	public Map<String, String> getErrors() {
        return errors;
    }
	
	public boolean testChangePassword(HttpServletRequest request, User user) throws Exception {
		String currentPassword = getFieldValue(request, CURRENT_PWD_FIELD);
		if(!(currentPassword.equals(user.getPassword()))) {
			setError(CURRENT_PWD_FIELD, "Mot de passe actuel erroné.");
			return false;
		}
		else if(!testNewPassword(request)) {
			setError(NEW_PWD_CONFIRM_FIELD, "Confirmation du nouveau mot de passe échouée.");
			return false;
		}
		return true;
	}
	
	private boolean testNewPassword(HttpServletRequest request) {
		if(getFieldValue(request, NEW_PWD_FIELD).equals(getFieldValue(request, NEW_PWD_CONFIRM_FIELD))) {
			return true;
		}
		return false;
	}
	
	public String getNewPassword(HttpServletRequest request) {
		return getFieldValue(request, NEW_PWD_FIELD);
	}
	
	private static String getFieldValue(HttpServletRequest request, String fieldName) {
		String value = request.getParameter(fieldName);
		if(value == null || value.trim().length() == 0) {
			return null;
		} else {
			return value;
		}
	}
	
	private void setError(String field, String message) {
        errors.put(field, message);
    }
}
