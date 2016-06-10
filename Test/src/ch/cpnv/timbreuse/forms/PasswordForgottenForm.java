package ch.cpnv.timbreuse.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.cpnv.timbreuse.dao.DAOUtility;

public class PasswordForgottenForm {
	private static final String USERNAME_FIELD = "usernamePasswordForgotten";
	private String result;
	private Map<String, String> errors      = new HashMap<String, String>();
	
	public PasswordForgottenForm() {
	}
	
	/**
	 * @return Le résultat
	 */
	public String getResult() {
		return result;
	}
	
	/**
	 * @return Une map des erreurs
	 */
	public Map<String, String> getErrors() {
        return errors;
    }
	
	public String getUsernamePasswordForgotten(HttpServletRequest request) {
		String usernameUnchanged = getFieldValue(request, USERNAME_FIELD);
		String firstname = DAOUtility.getFirstnameFromUsername(usernameUnchanged);
		String lastname = DAOUtility.getLastnameFromUsername(usernameUnchanged);
		return DAOUtility.generateUsername(firstname, lastname);
	}
	
	
	/**
	 * Méthode utilitaire qui retourne null si un champ est vide, et son contenu sinon.
	 * @param request Requête envoyée par la servlet
	 * @param fieldName Nom du champ
	 * @return La valeur de du champ stocké dans la requête
	 */
	private static String getFieldValue(HttpServletRequest request, String fieldName) {
		String value = request.getParameter(fieldName);
		if(value == null || value.trim().length() == 0) {
			return null;
		} else {
			return value;
		}
	}
}
