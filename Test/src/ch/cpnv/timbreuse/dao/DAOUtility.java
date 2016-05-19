package ch.cpnv.timbreuse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.mathTime.SecondsPastMidnight;


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
	
	/**
	 * Initialise la requête préparée basée sur les arguments
	 * @param connection Connexion à la DataBase?
	 * @param sql Requête SQL
	 * @param returnGeneratedKeys ???
	 * @param objets ???
	 * @return Requête préparée
	 * @throws SQLException ???
	 */
	public static PreparedStatement preparedRequestInitialisation(Connection connection, String sql, boolean returnGeneratedKeys, Object... objets) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        for (int i = 0; i < objets.length; i++) {
            preparedStatement.setObject(i + 1, objets[i]);
        }
        return preparedStatement;
    }
	
	/**
	 * Enlève tous les accents d'un string.
	 * @param s Chaîne de caractères à modifier
	 * @return string sans accents
	 */
	private static String removeAccent(String s) {
		s=s.replaceAll("[âàáä]", "a");
		s=s.replaceAll("[êéèë]", "e");
		s=s.replaceAll("[îíìï]", "i");
		s=s.replaceAll("[ôóòö]", "o");
		s=s.replaceAll("[ûúùü]", "u");
		s=s.replaceAll("[ç]", "c");
		return s;
	}
	
	/**
	 * Retourne un string en majuscule.
	 * @param s Chaîne de caractères à modifier
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
	 * @param s Chaîne de caractères à modifier
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
	 * @param firstname Prénom
	 * @param lastname Nom
	 * @return email format CPNV
	 */
	public static String generateEmail(String firstname, String lastname) {
		return (removeAccent(firstname)+"."+upperStringWithoutAccent(lastname)+"@cpnv.ch").replaceAll("\\s+", "");
	}
	
	/**
	 * Retourne le nom d'utilisateur depuis l'email sans majuscules.
	 * @param email Adresse e-mail de l'utilisateur
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
	 * @param firstname Prénom
	 * @param lastname Nom
	 * @return nom d'utilisateur sans accents depuis le prénom et nom
	 */
	public static String generateUsername(String firstname, String lastname) {
		return (lowerStringWithoutAccent(firstname+"."+lastname)).replaceAll("\\s+", "");
	}
	
	/**
	 * Retourne le prénom à partir du nom d'utilisateur.
	 * @param username Nom de l'utilisateur
	 * @return prénom depuis le nom d'utilisateur
	 */
	public static String getFirstnameFromUsername(String username) {
		return username.substring(0, username.lastIndexOf("."));
	}
	
	/**
	 * Retourne le nom à partir du nom d'utilisateur.
	 * @param username Nom de l'utilisateur
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
	
	/**
	 * Additione l'ancien temps et le nouveau (timeDiff).
	 * @param newTime nouveau temps
	 * @param oldTime ancien temps
	 * @return somme nouveau temps + ancien temps
	 */
	public static int addTime(int newTime, int oldTime) {
		return newTime + oldTime;
	}	
	
	/**
	 * Modifie une chaîne de caractères en enlevant les éventuels accents et imposant une majuscule pour la première lettre.
	 * @param s chaîne de caractères à modifier
	 * @return chaîne de caractères sans accents et première lettre majuscule
	 */
	public static String upperFirstLetter(String s) {
		s = removeAccent(s);
		char[] newChars = s.toCharArray();
		newChars[0] = Character.toUpperCase(newChars[0]);
		for(int i=1;i<newChars.length;i++) {
			newChars[i] = Character.toLowerCase(newChars[i]);
		}
		return new String(newChars);
	}
	
	/**
	 * Retourne la date actuelle (jj-MM-aaaa)
	 * @return date actuelle
	 */
	public static String currentDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Calendar calendar = Calendar.getInstance();
		return dateFormat.format(calendar.getTime());
	}
	
	/**
	 * Retourne le temps actuel (HH:mm:ss)
	 * @return temps actuel
	 */
	public static int currentTime() {
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		String time = timeFormat.format(calendar.getTime());
		return SecondsPastMidnight.stringToInt(time);
	}
}