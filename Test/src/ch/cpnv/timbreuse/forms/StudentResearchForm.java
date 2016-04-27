package ch.cpnv.timbreuse.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.cpnv.timbreuse.beans.Student;
import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOException;
import ch.cpnv.timbreuse.dao.DAOStudent;
import ch.cpnv.timbreuse.dao.DAOUser;

/**
 * Formulaire pour supprimer un élève dans la base de données
 *
 */
public final class StudentResearchForm {
	private static final String LASTNAME_FIELD = "researchLastname";
	private String result;
	private Map<String, String> errors = new HashMap<String, String>();
	private DAOStudent daoStudent;
	
	public StudentResearchForm(DAOStudent daoStudent) {
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
	 * @param request Requête envoyée par la servlet contenant les informations nécessaire pour rechercher un élève
	 * @return L'élève trouvé
	 */
	public Student researchUser(HttpServletRequest request) {
		String lastname = getFieldValue(request, LASTNAME_FIELD);
		Student student = new Student(); //table eleves
		User user = new User(); //table users
		try {
			if(errors.isEmpty()) {
				student = daoStudent.find(lastname);
				
				result = "Succès de la recherche.";
			} else {
				result = "Echec de la recherche.";
			}
		} catch(DAOException e) {
			result = "Echec de la recherche: une erreur est survenue, merci de réessayer dans quelques instans.";
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
