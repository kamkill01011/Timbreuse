package ch.cpnv.timbreuse.forms;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.cpnv.timbreuse.beans.Student;
import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOException;
import ch.cpnv.timbreuse.dao.DAOStudent;
import ch.cpnv.timbreuse.mathTime.SecondsPastMidnight;

public class AddTimeStudentsForm {
	private static final String ADD_TIME_FIELD = "modifyTimeDiff";
	private ArrayList<Student> classe;
	private Map<String, String> errors = new HashMap<String, String>();
	private String result;
	private DAOStudent daoStudent;

	public AddTimeStudentsForm (DAOStudent daoStudent, ArrayList<Student> classe) {
		this.classe = classe;
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

	public int getTimeDiffField(HttpServletRequest request) {
		int time = 0;
		try {
			if(errors.isEmpty()) {
				String timeString = getFieldValue(request, ADD_TIME_FIELD);
				timeString = timeString.replaceAll("\\s+", ""); //supprime les espaces
				String hours, minutes, seconds;
				if(timeString.contains("-")) {
					hours = timeString.substring(1, timeString.indexOf(":"));
					minutes = timeString.substring(timeString.indexOf(":")+1, timeString.lastIndexOf(":"));
					seconds = timeString.substring(timeString.lastIndexOf(":")+1, timeString.length());
					time = SecondsPastMidnight.fromHMS(Integer.parseInt(hours)*(-1), Integer.parseInt(minutes)*(-1), Integer.parseInt(seconds)*(-1));
				} else {		
					hours = timeString.substring(0, timeString.indexOf(":"));
					minutes = timeString.substring(timeString.indexOf(":")+1, timeString.lastIndexOf(":"));
					seconds = timeString.substring(timeString.lastIndexOf(":")+1, timeString.length());
					time = SecondsPastMidnight.fromHMS(Integer.parseInt(hours), Integer.parseInt(minutes), Integer.parseInt(seconds));
				}
			}
		} catch(DAOException e) {
			result = "Echec de la création.";
			e.printStackTrace();
		}
		return time;
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
