package ch.cpnv.timbreuse.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOException;
import ch.cpnv.timbreuse.dao.DAOUser;

public class DeleteAdminForm {
	private static final String FIRSTNAME_FIELD = "deleteFirstnameAdmin";
	private static final String LASTNAME_FIELD = "deleteLastnameAdmin";
	private String result;
	private Map<String, String> errors = new HashMap<String, String>();
	private DAOUser daoUser;
	
	public DeleteAdminForm(DAOUser daoUser) {
		this.daoUser = daoUser;
	}
	
	/**
     * @return Une ma des erreurs
     */
	public Map<String, String> getErreurs() {
        return errors;
    }

    /**
     * @return Le résultat
     */
	public String getResult() {
		return result;
	}
	
	public User selectAdminToDelet(HttpServletRequest request) {
		User user = new User();
		try {
			if(errors.isEmpty()) {
				user.setFirstname(getFieldValue(request, FIRSTNAME_FIELD));
				user.setLastname(getFieldValue(request, LASTNAME_FIELD));
			}
		} catch(DAOException e) {
			result = "Echec de la suppression.";
			e.printStackTrace();
		}
		return user;
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
