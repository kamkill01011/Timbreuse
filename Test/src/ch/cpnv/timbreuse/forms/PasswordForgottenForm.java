package ch.cpnv.timbreuse.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.cpnv.timbreuse.dao.DAOUtility;

/**
 * @author Mathieu.JEE, Kamil.AMRANI
 * Gère le formulaire d'oubli de mot de passe
 */
public class PasswordForgottenForm {
	
	private static final String USERNAME_FIELD = "usernamePasswordForgotten";	//champ nom d'utilisateur ayant oublié son mot de passe
	private String result;
	private Map<String, String> errors = new HashMap<String, String>();
	
	public PasswordForgottenForm() { //Constructeur privé par défaut vide => rend la classe non instantiable
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
	
	/**
	 * Génère un nom d'utilisateur dans le format adéquat à partir du champ nom d'utilisateur
	 * @param request HttpServletRequest
	 * @return nom d'ulisateur en String dans le bon format
	 */
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