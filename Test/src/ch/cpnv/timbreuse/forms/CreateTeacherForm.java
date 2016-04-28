package ch.cpnv.timbreuse.forms;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import ch.cpnv.timbreuse.beans.Teacher;
import ch.cpnv.timbreuse.dao.DAOException;
import ch.cpnv.timbreuse.dao.DAOTeacher;

import static ch.cpnv.timbreuse.dao.DAOUtility.generateEmail;

public class CreateTeacherForm {
	private static final String FIRSTNAME_FIELD ="addFirstnameTeacher";
	private static final String LASTNAME_FIELD = "addLastnameTeacher";
	private static final String CLASSES_FIELD = "addClasseTeacher";
	private String result;
	private Map<String, String> errors = new HashMap<String, String>();
	private DAOTeacher daoTeacher;
	
	public CreateTeacherForm(DAOTeacher daoTeacher) {
		this.daoTeacher = daoTeacher;
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
	 * Associe un nouvel enseignant au champs du formulaire.
	 * @param request
	 * @return nouveau teacher
	 */
	public Teacher getTeacher(HttpServletRequest request) {
		Teacher teacher = new Teacher();
		try {
			if(errors.isEmpty()) {
				teacher.setFirstname(getFieldValue(request, FIRSTNAME_FIELD));
				teacher.setLastname(getFieldValue(request, LASTNAME_FIELD));
				teacher.setClasse(getFieldValue(request, CLASSES_FIELD));
				teacher.setEmail(generateEmail(teacher.getFirstname(), teacher.getLastname()));
			}
		} catch(DAOException e) {
			result = "Echec de la création.";
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
