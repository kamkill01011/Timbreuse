package ch.cpnv.timbreuse.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOException;
import ch.cpnv.timbreuse.dao.DAOUser;

import static ch.cpnv.timbreuse.dao.DAOUtility.generateUsername;

public class CreateAdminForm {
	private static final String FIRSTNAME_FIELD ="addFirstnameAdmin";
	private static final String LASTNAME_FIELD = "addLastnameAdmin";
	private String result;
	private Map<String, String> errors = new HashMap<String, String>();
	private DAOUser daoUser;
	
	public CreateAdminForm(DAOUser daoUser) {
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
	
	public User getAdmin(HttpServletRequest request) {
		User admin = new User();
		try {
			if(errors.isEmpty()) {
				String firstname = getFieldValue(request, FIRSTNAME_FIELD);
				String lastname = getFieldValue(request, LASTNAME_FIELD);
				admin.setFirstname(firstname);
				admin.setLastname(lastname);
				admin.setUsername(generateUsername(firstname, lastname));
				admin.setPermissionLevel(1);
			}
		} catch(DAOException e) {
			result = "Echec de la création.";
			e.printStackTrace();
		}
		return admin;
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












