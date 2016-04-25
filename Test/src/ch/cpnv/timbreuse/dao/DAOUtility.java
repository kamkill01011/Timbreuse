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
	
	public static String upperWithoutAccent(String x) {
		final String accents = "àäáéèëïìíöóòüúùç";
		x=x.replaceAll("[àáä]", "a");
		x=x.replaceAll("[éèë]", "e");
		x=x.replaceAll("[íìï]", "i");
		x=x.replaceAll("[óòö]", "o");
		x=x.replaceAll("[úùü]", "u");
		x=x.replaceAll("[ç]", "c");
		char[] newChars = x.toCharArray();
		for(int i=0;i<newChars.length;i++) {
			newChars[i] = Character.toUpperCase(newChars[i]);
		}
		return new String(newChars);
	}
	
	public static User sqlSelect(String sql, String table, Object object) {
		User user = new User();
		return user;
	}
	
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



