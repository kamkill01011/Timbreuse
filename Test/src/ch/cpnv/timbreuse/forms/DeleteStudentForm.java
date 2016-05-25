package ch.cpnv.timbreuse.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.cpnv.timbreuse.beans.Student;
import ch.cpnv.timbreuse.dao.DAOException;
import ch.cpnv.timbreuse.dao.DAOStudent;

/**
 * Formulaire pour supprimer un élève dans la base de données
 *
 */
public class DeleteStudentForm {
	private static final String FIRSTNAME_FIELD = "deletFirstname";
	private static final String LASTNAME_FIELD = "deletLastname";
	private String result;
	private Map<String, String> errors = new HashMap<String, String>();
	private DAOStudent daoStudent;
	
	public DeleteStudentForm(DAOStudent daoStudent) {
		this.daoStudent = daoStudent;
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
	
	/**
	 * @param request Requête envoyée par la servlet contenant les informations nécessaire supprimer un élève
	 * @return L'élève supprimé
	 */
	public Student selectStudentToDelete(HttpServletRequest request) {
		Student student = new Student();
		try {
			if(errors.isEmpty()) {
				student.setFirstname(getFieldValue(request, FIRSTNAME_FIELD));
				student.setLastname(getFieldValue(request, LASTNAME_FIELD));
			}
		} catch(DAOException e) {
			result = "Echec de la suppression.";
			e.printStackTrace();
		}
		return student;
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
