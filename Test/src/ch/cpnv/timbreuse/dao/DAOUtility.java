package ch.cpnv.timbreuse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Normalizer;
import java.util.Random;

import ch.cpnv.timbreuse.beans.User;
import sun.text.normalizer.UBiDiProps;


public final class DAOUtility {
	//Constructeur private => final class, utilitaire, contient uniquement des méthodes statiques
	private DAOUtility() {
		
    }
	
	//Fermetures d'objets
	public static void closeObject(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println( "Echec de la fermeture du ResultSet : " + e.getMessage());
            }
        }
    }
	
	public static void closeObject(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Echec de la fermeture du Statement : " + e.getMessage());
            }
        }
    }
	
	public static void closeObject(Connection connection) {
        if (connection != null) {
            try {
            	connection.close();
            } catch (SQLException e) {
                System.out.println("Echec de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }
	
	public static void closeObjects(Statement statement, Connection connection) {
		closeObject(statement);
		closeObject(connection);
	}
	
	public static void closeObjects(ResultSet resultSet, Statement statement, Connection connection) {
		closeObject(resultSet);
		closeObject(statement);
		closeObject(connection);
	}
	
	//Initialise la requête préparée basée sur les arguments
	public static PreparedStatement preparedRequestInitialisation(Connection connection, String sql, boolean returnGeneratedKeys, Object... objets) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        for (int i = 0; i < objets.length; i++) {
            preparedStatement.setObject(i + 1, objets[i]);
        }
        return preparedStatement;
    }
	
	/**
	 * Enlève tous les accents d'un string.
	 * @param s
	 * @return string sans accents
	 */
	private static String removeAccent(String s) {
		s=s.replaceAll("[àáä]", "a");
		s=s.replaceAll("[éèë]", "e");
		s=s.replaceAll("[íìï]", "i");
		s=s.replaceAll("[óòö]", "o");
		s=s.replaceAll("[úùü]", "u");
		s=s.replaceAll("[ç]", "c");
		return s;
	}
	
	/**
	 * Retourne un string en majuscule.
	 * @param s 
	 * @return String en majuscules
	 */
	private static String upperStringWithoutAccent(String s) {
		s = removeAccent(s);
		char[] newChars = s.toCharArray();
		for(int i=0;i<newChars.length;i++) {
			newChars[i] = Character.toUpperCase(newChars[i]);
		}
		return new String(newChars);
	}
	
	/**
	 * Retourne un string en minuscules.
	 * @param s
	 * @return String en minuscules
	 */
	private static String lowerStringWithoutAccent(String s) {
		s = removeAccent(s);
		char[] newChars = s.toCharArray();
		for(int i=0;i<newChars.length;i++) {
			newChars[i] = Character.toLowerCase(newChars[i]);
		}
		return new String(newChars);
	}
	
	/**
	 * Génère l'email dans le format CPNV (orenom.NOM@cpnv.ch) à partir du prénom et du nom.
	 * @param firstname
	 * @param lastname
	 * @return email format CPNV
	 */
	public static String generateEmail(String firstname, String lastname) {
		return removeAccent(firstname)+"."+upperStringWithoutAccent(lastname)+"@cpnv.ch";
	}
	
	/**
	 * Retourne le nom d'utilisateur depuis l'email sans majuscules.
	 * @param email
	 * @return nom d'utilisateur en minuscules depuis l'email
	 */
	public static String getUsernameFromEmail(String email) {
		String username = email.substring(0, email.lastIndexOf("@"));
		char[] usernameTab = username.toCharArray();
		for(int i=0;i<usernameTab.length;i++) {
			usernameTab[i] = Character.toLowerCase(usernameTab[i]);
		}
		return new String(usernameTab);
	}
	
	/**
	 * Génère le nom d'utilisateur sans accents à partir du prénom et du nom.
	 * @param firstname
	 * @param lastname
	 * @return nom d'utilisateur sans accents depuis le prénom et nom
	 */
	public static String generateUsername(String firstname, String lastname) {
		return lowerStringWithoutAccent(firstname+"."+lastname);
	}
	
	/**
	 * Retourne le prénom à partir du nom d'utilisateur.
	 * @param username
	 * @return prénom depuis le nom d'utilisateur
	 */
	public static String getFirstnameFromUsername(String username) {
		return username.substring(0, username.lastIndexOf("."));
	}
	
	/**
	 * Retourne le nom à partir du nom d'utilisateur.
	 * @param username
	 * @return nom depuis le nom d'utilisateur
	 */
	public static String getLastnameFromUsername(String username) {
		return username.substring(username.lastIndexOf(".")+1, username.length());
	}
	
	public static User sqlSelect(String sql, String table, Object object) {
		User user = new User();
		return user;
	}
	
	/**
	 * Génère un mot de passe aléatoire de longueur 8 pour la 1ère connexion
	 * @return Mot de passe 8 caractères
	 */
	public static String randomPassword() {
		Random r = new Random();
		String password = "";
		String alphabet="1234567890qwertzuiopasdfghjklyxcvbnm";
		for(int i=0;i<8;i++) {
			password+=alphabet.charAt(r.nextInt(alphabet.length()));
		}
		return password;
	}
	
	
}



