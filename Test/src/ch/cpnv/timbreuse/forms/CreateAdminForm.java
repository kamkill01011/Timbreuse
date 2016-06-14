package ch.cpnv.timbreuse.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOException;

import static ch.cpnv.timbreuse.dao.DAOUtility.generateUsername;

/**
 * @author Mathieu.JEE, Kamil.AMRANI
 * Gère le formulaire de création d'un admin
 */
public class CreateAdminForm {
	private static final String FIRSTNAME_FIELD ="addFirstnameAdmin";	//champ ajout admin (prénom)
	private static final String LASTNAME_FIELD = "addLastnameAdmin";	//champ ajout admin (nom)
	private String result;
	private Map<String, String> errors = new HashMap<String, String>();
	
	public CreateAdminForm() { //Constructeur privé par défaut vide => rend la classe non instantiable
	}
	
	/**
     * @return Une map des erreurs
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
	
	/**
	 * Crée un objet user avec le niveau de permission admin (1)
	 * @param request HttpServletRequest
	 * @return un admin (User)
	 */
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