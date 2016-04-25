package ch.cpnv.timbreuse.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOUser;
import ch.cpnv.timbreuse.dao.DAOUsername;

public final class ConnectionForm {
    private static final String USERNAME_FIELD = "email";
    private static final String PASSWORD_FIELD = "password";
    private String              result;
    private Map<String, String> errors      = new HashMap<String, String>();
    private DAOUsername daoUsername;
    
    public ConnectionForm(DAOUsername daoUsername) {
    	this.daoUsername=daoUsername;
    }
    
    public String getResult() {
        return result;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public User connectUser(HttpServletRequest request) {
        /* Récupération des champs du formulaire */
        String email = getFieldValue(request, USERNAME_FIELD);
        String password = getFieldValue(request, PASSWORD_FIELD);

        User user = new User();

        /* Validation du champ username. */
        try {
            usernameValidation(email);
        } catch (Exception e) {
            setError(USERNAME_FIELD, e.getMessage());
        }
        user.setEmail(email);

        /* Validation du champ mot de passe. */
        try {
            passwordValidation(password);
        } catch (Exception e) {
            setError(PASSWORD_FIELD, e.getMessage());
        }
        user.setPassword(password);

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
    private void usernameValidation(String username) throws Exception {
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
    private void connectionValidation(String username, String password) throws Exception {
    	User user = new User();
        user = daoUsername.find(username);
        System.out.println(user.getUsername());
    	if(true) {
    		throw new Exception("Identifiant ou mot de passe erroné.");
    	}
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