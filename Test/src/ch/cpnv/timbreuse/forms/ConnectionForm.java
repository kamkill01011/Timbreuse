package ch.cpnv.timbreuse.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOUser;

/**
 * Formulaire pour se connecter
 *
 */
public final class ConnectionForm {
    private static final String EMAIL_FIELD = "email";
    private static final String PASSWORD_FIELD = "password";
    private String              result;
    private Map<String, String> errors      = new HashMap<String, String>();
    private DAOUser daoUser;
    
    public ConnectionForm(DAOUser daoUser) {
    	this.daoUser=daoUser;
    }
    
    /**
     * @return Le résultat
     */
    public String getResult() {
        return result;
    }

    /**
     * @return Une ma des erreurs
     */
    public Map<String, String> getErrors() {
        return errors;
    }

    /**
     * @param request Requête envoyée par la servlet contenant les informations nécessaire à la connection
     * @return L'utilisateur correspondant aux identifiants donnés
     */
    public User connectUser(HttpServletRequest request) {
        /* Récupération des champs du formulaire */
        String email = getFieldValue(request, EMAIL_FIELD);
        String password = getFieldValue(request, PASSWORD_FIELD);
        String username = email.substring(0, email.lastIndexOf("@"));
        
        User user = new User();

        /* Validation du champ username. */
        try {
            emailValidation(email);
        } catch (Exception e) {
            setError(EMAIL_FIELD, e.getMessage());
        }
        

        /* Validation du champ mot de passe. */
        try {
            passwordValidation(password);
        } catch (Exception e) {
            setError(PASSWORD_FIELD, e.getMessage());
        }
        
        try {        	
        	user = connectionValidation(username, password);
        } catch(Exception e) {
        	setError(EMAIL_FIELD, e.getMessage());
        }
        
        
        
        /* Initialisation du résultat global de la validation. */
        if (errors.isEmpty()) {
            result = "Succès de la connexion.";
        } else {
            result = "Échec de la connexion.";
        }
        return user;
    }
    
    /**
     * Valide l'adresse email saisie.
     * @param username Nom d'utilisateur (prénom.nom)
     * @throws Exception ???
     */
    private void emailValidation(String username) throws Exception {
    	if (username != null && !username.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
            throw new Exception("Merci de saisir une adresse mail valide.");
        }
    }
    
    /**
     * Valide le mot de passe saisi.
     * @param password Mot de passe
     * @throws Exception ???
     */
    private void passwordValidation(String password) throws Exception {
        if (password != null) {
            if (password.length() < 3) {
                throw new Exception("Le mot de passe doit contenir au moins 3 caractères.");
            }
        } else {
            throw new Exception("Merci de saisir votre mot de passe.");
        }
    }
    
    /**
     * Vérification de l'indentifiant/motdepasse dans la base de données
     * @param username Nom d'utilisateur
     * @param password Mot de passe
     * @return L'utilisateur correspondant aux identifiants donnés
     * @throws Exception ???
     */
    private User connectionValidation(String username, String password) throws Exception {
    	User user = new User();
        user = daoUser.findUser(username);
    	if((user.getUsername()==null) || (!(user.getUsername().equals(username) && user.getPassword().equals(password)))) {
    		throw new Exception("Identifiant ou mot de passe erroné.");
    	}
    	return user;
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
	 * Méthode utilitaire qui retourne null si un champ est vide, et son contenu sinon.
	 * @param request Requête envoyée par la servlet
	 * @param fieldName Nom du champ
	 * @return La valeur de du champ stocké dans la requête
	 */
    private static String getFieldValue(HttpServletRequest request, String fieldName) {
        String value = request.getParameter(fieldName);
        if (value == null || value.trim().length() == 0) {
            return null;
        } else {
            return value;
        }
    }
}