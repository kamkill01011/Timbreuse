package ch.cpnv.timbreuse.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOUser;

/**
 * Formulaire pour changer un mot de passe 
 *
 */
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
	 * @param request Requête envoyée par la servlet contenant l'ancien mot de passe, le nouveau et la confimation
	 * @param user Utilisateur à qui il faut changer le mot de passe
	 * @return Si les informations sont correctes et que le mot de passe peut être changé
	 * @throws Exception ???
	 */
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
	
	/**
	 * @param request Requête envoyée par la servlet contenant l'ancien mot de passe, le nouveau et la confimation
	 * @return Si le nouveau mot de passe et le même que la confirmation
	 */
	private boolean testNewPassword(HttpServletRequest request) {
		if(getFieldValue(request, NEW_PWD_FIELD).equals(getFieldValue(request, NEW_PWD_CONFIRM_FIELD))) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param request Requête envoyée par la servlet contenant l'ancien mot de passe, le nouveau et la confimation
	 * @return le nouveau mot de passe
	 */
	public String getNewPassword(HttpServletRequest request) {
		return getFieldValue(request, NEW_PWD_FIELD);
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
	
	/**
	 * Ajoute un message correspondant au champ spécifié à la map des erreurs.
	 * @param field Nom du champ de l'erreur
	 * @param message Message de l'erreur
	 */
	private void setError(String field, String message) {
        errors.put(field, message);
    }
}
