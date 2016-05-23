package ch.cpnv.timbreuse.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.cpnv.timbreuse.dao.DAOHolyday;
import ch.cpnv.timbreuse.mathTime.Date;

import static ch.cpnv.timbreuse.mathTime.Date.stringToDate;

public class SetHolydaysForm {
	private static final String ADD_SINGLE_HOLYDAY = "addSingleHolyday";
	private static final String ADD_HOLYDAYS_GAP_A = "addHolydaysGapA";
	private static final String ADD_HOLYDAYS_GAP_B = "addHolydaysGapB";
	private String result;
	private Map<String, String> errors = new HashMap<String, String>();
	private DAOHolyday daoHolyday;
	
	public SetHolydaysForm(DAOHolyday daoHolyday) {
		this.daoHolyday = daoHolyday;
	}
	
	/**
	 * @return Le résultat
	 */
	public String getResult() {
		return result;
	}
	
	/**
	 * @return Une map des erreurs
	 */
	public Map<String, String> getErrors() {
        return errors;
    }
	
	/**
	 * Retourne le jour ferié en int
	 * @param request
	 * @return jour ferié (int)
	 */
	public ArrayList<String> getSingleHolyday(HttpServletRequest request) {
		ArrayList<String> singleHolyday = new ArrayList<String>();
		singleHolyday.add(stringToDate(getFieldValue(request, ADD_SINGLE_HOLYDAY)).toString());
		return singleHolyday;
	}
	
	/**
	 * Retourne un arryList de la plage de jours feriés.
	 * @param request
	 * @return arrayList plage de jours feriés
	 */
	public ArrayList<String> getHolydaysGap(HttpServletRequest request) {
		ArrayList<String> holydaysGap = new ArrayList<String>();
		Date dateA = stringToDate(getFieldValue(request, ADD_HOLYDAYS_GAP_A));
		Date dateB = stringToDate(getFieldValue(request, ADD_HOLYDAYS_GAP_B));
		try {
			checkHolydayGap(dateA.fixed(), dateB.fixed());
		} catch(Exception e) {
			setError(ADD_HOLYDAYS_GAP_B, e.getMessage()); //vérifie si l'intervalle entré est possible
		}
		
		for (int i = dateA.fixed(); i <= dateB.fixed(); i++) {
			holydaysGap.add(Date.fixedToDate(i).toString());
		}
		return holydaysGap;
	}
	
	/**
	 * Vérifie si l'intervalle entré est correct. Sinon, lance une exception.
	 * @param A Borne A intervalle
	 * @param B Borbe B intervalle
	 */
	private void checkHolydayGap(int A, int B) throws Exception {
		if(B-A<=0) {
			throw new Exception("Intervalle incorrect.");
		}
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
	
	/**
	 * Ajoute un message correspondant au champ spécifié à la map des erreurs.
	 * @param field Nom du champ de l'erreur
	 * @param message Message de l'erreur
	 */
	private void setError(String field, String message) {
        errors.put(field, message);
    }
}