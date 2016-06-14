package ch.cpnv.timbreuse.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.cpnv.timbreuse.dao.DAOException;
import ch.cpnv.timbreuse.mathTime.SecondsPastMidnight;

/**
 * @author Mathieu.JEE, Kamil.AMRANI
 * Gère le formulaire pour ajouter ou soustraire du temps à un élève
 */
public class AddTimeStudentsForm {
	
	private static final String ADD_TIME_FIELD = "modifyTimeDiff"; //champ du formulaire
	private Map<String, String> errors = new HashMap<String, String>();
	private String result;

	public AddTimeStudentsForm () { //Constructeur privé par défaut vide => rend la classe non instantiable
		
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
	 * Récupère et retourne l'entier dans le champ ADD_TIME_FIELD du formulaire
	 * @param request HttpServletRequest
	 * @return temps en int
	 */
	public int getTimeDiffField(HttpServletRequest request) {
		int time = 0;
		try {
			if(errors.isEmpty()) {
				String timeString = getFieldValue(request, ADD_TIME_FIELD);
				timeString = timeString.replaceAll("\\s+", ""); //supprime les espaces
				String hours, minutes, seconds;
				//Modifie légèrement l'affichage si l'entier est négatif. (-HH:-mm:-ss devient -HH:mm:ss)
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
