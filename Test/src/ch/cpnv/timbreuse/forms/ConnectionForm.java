package ch.cpnv.timbreuse.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOUser;

public final class ConnectionForm {
    private static final String EMAIL_FIELD = "email";
    private static final String PASSWORD_FIELD = "password";
    private String              result;
    private Map<String, String> errors      = new HashMap<String, String>();
    private DAOUser daoUser;
    
    public ConnectionForm(DAOUser daoUser) {
    	this.daoUser=daoUser;
    }
    
    public String getResult() {
        return result;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

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
        	user = daoUser.findStudent(connectionValidation(username, password));
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
     */
    private void emailValidation(String username) throws Exception {
    	if (username != null && !username.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
            throw new Exception("Merci de saisir une adresse mail valide.");
        }
    }

    /**
     * Valide le mot de passe saisi.
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

    //Vérification de l'indentifiant/motdepasse dans la base de données
    private User connectionValidation(String username, String password) throws Exception {
    	User user = new User();
        user = daoUser.findUser(username);
    	if((user.getUsername()==null) || (!(user.getUsername().equals(username) && user.getPassword().equals(password)))) {
    		throw new Exception("Identifiant ou mot de passe erroné.");
    	}
    	return user;
    }
    
    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setError(String field, String message) {
        errors.put(field, message);
    }

    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
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