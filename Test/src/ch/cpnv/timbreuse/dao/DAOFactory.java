package ch.cpnv.timbreuse.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {
	private static final String PROPERTIES_FILE   = "/ch/cpnv/timbreuse/dao/dao.properties";
	private static final String PROPERTY_URL      = "url";
	private static final String PROPERTY_DRIVER   = "driver";
	private static final String PROPERTY_USERNAME = "username";
	private static final String PROPERTY_PASSWORD = "password";
	private String url;
	private String username;
	private String password;

	public DAOFactory(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	//récupère info de connexion à la DB, Charge driver, retourne instance de la Factory
	public static DAOFactory getInstance() throws DAOException {
		Properties properties = new Properties();
		String url;
		String driver;
		String username;
		String password;

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);

		if(propertiesFile == null) {
			throw new DAOException("Le fichier properties "+PROPERTIES_FILE+" est introuvable.");
		}

		try {
			properties.load(propertiesFile);
			url = properties.getProperty(PROPERTY_URL);
			driver = properties.getProperty(PROPERTY_DRIVER);
			username = properties.getProperty(PROPERTY_USERNAME);
			password = properties.getProperty(PROPERTY_PASSWORD);
		} catch (IOException e) {
			throw new DAOException("Impossible de charger le fichier properties "+PROPERTIES_FILE, e);
		} 
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new DAOException("Le driver est introuvable dans le classpath.", e);
		}
		
		DAOFactory instance = new DAOFactory(url, username, password);
		return instance;
	}
	
	//Fournit une connexion à la DB
	Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
	
	//Récupèration de l'implémentation des différentes DAO
	public DAOUser getDaoUser() {
		return new DAOImplUser(this);
	}
	public DAOUser getDaoUsername() {
		return new DAOImplUsername(this);
	}
}
