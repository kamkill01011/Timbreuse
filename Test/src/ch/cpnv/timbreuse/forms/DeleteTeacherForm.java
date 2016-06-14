package ch.cpnv.timbreuse.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.cpnv.timbreuse.beans.Teacher;
import ch.cpnv.timbreuse.dao.DAOException;

/**
 * @author Mathieu.JEE, Kamil.AMRANI
 * Gère le formulaire de suppression d'un professeur
 */
public class DeleteTeacherForm {
	
	private static final String FIRSTNAME_FIELD = "deleteFirstnameTeacher"; //champ prénom
	private static final String LASTNAME_FIELD = "deleteLastnameTeacher";	//champ nom
	private String result;
	private Map<String, String> errors = new HashMap<String, String>();
	
	public DeleteTeacherForm() { //Constructeur privé par défaut vide => rend la classe non instantiable
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
	 * Génère le professeur à supprimer
	 * @param request HttpServletRequest
	 * @return professeur à supprimer (Teacher)
	 */
	public Teacher selectTeacherToDelete(HttpServletRequest request) {
		Teacher teacher = new Teacher();
		try {
			if(errors.isEmpty()) {
				teacher.setFirstname(getFieldValue(request, FIRSTNAME_FIELD));
				teacher.setLastname(getFieldValue(request, LASTNAME_FIELD));
			}
		} catch(DAOException e) {
			result = "Echec de la suppression.";
			e.printStackTrace();
		}
		return teacher;
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