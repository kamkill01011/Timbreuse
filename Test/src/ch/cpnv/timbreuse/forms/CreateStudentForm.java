package ch.cpnv.timbreuse.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.cpnv.timbreuse.beans.Student;
import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOException;
import ch.cpnv.timbreuse.dao.DAOStudent;
import ch.cpnv.timbreuse.dao.DAOUser;

import static ch.cpnv.timbreuse.dao.DAOUtility.generateEmail;

/**
 * Formulaire pour créer un élève dans la base de données
 *
 */
public class CreateStudentForm {
	private static final String CLASS_FIELD = "addClass";
	private static final String FIRSTNAME_FIELD = "addFirstname";
	private static final String LASTNAME_FIELD = "addLastname";
	private String result;
	private Map<String, String> errors = new HashMap<String, String>();
	private DAOStudent daoStudent;
	
	public CreateStudentForm(DAOStudent daoStudent) {
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
	 * Ajoute un message correspondant au champ spécifié à la map des erreurs.
	 * @param field Nom du champ de l'erreur
	 * @param message Message de l'erreur
	 */
	private void setError(String field, String message) {
        errors.put(field, message);
    }
	
	/**
	 * @param request Requête envoyée par la servlet contenant les informations nécessaire créer un nouvel élève
	 * @return Le nouvel élève
	 */
	public Student isStudent(HttpServletRequest request) {
		Student student = new Student();
		try {
			if(errors.isEmpty()) {
				student.setClasse(getFieldValue(request, CLASS_FIELD));
				student.setFirstname(getFieldValue(request, FIRSTNAME_FIELD));
				student.setLastname(getFieldValue(request, LASTNAME_FIELD));
				student.setEmail(generateEmail(student.getFirstname(), student.getLastname()));
			}
		} catch(DAOException e) {
			result = "Echec de la création.";
			setError(LASTNAME_FIELD, e.getMessage());
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
