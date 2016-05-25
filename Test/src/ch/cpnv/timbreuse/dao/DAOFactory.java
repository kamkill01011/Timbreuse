package ch.cpnv.timbreuse.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class DAOFactory {
	private static final String PROPERTIES_FILE   = "/ch/cpnv/timbreuse/dao/dao.properties";
	private static final String PROPERTY_URL      = "url";
	private static final String PROPERTY_DRIVER   = "driver";
	private static final String PROPERTY_USERNAME = "username";
	private static final String PROPERTY_PASSWORD = "password";

	BoneCP connectionPool = null;
	
	public DAOFactory(BoneCP connectionPool) {
		this.connectionPool = connectionPool;
	}

	//récupère info de connexion à la DB, Charge driver, retourne instance de la Factory
	public static DAOFactory getInstance() throws DAOException {
		Properties properties = new Properties();
		String url;
		String driver;
		String username;
		String password;
		BoneCP connectionPool = null;
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
		
		try {
			//Création + config du pool de connexions via l'objet BoneCPConfig
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl(url);
			config.setUsername(username);
			config.setPassword(password);
			//Paramétrage taille du pool
			config.setMinConnectionsPerPartition(5);
			config.setMaxConnectionsPerPartition(15);
			config.setPartitionCount(2);
			//Création du pool avec les configs
			connectionPool = new BoneCP(config);
		} catch ( SQLException e ) {
            e.printStackTrace();
            throw new DAOException( "Erreur de configuration du pool de connexions.", e );
        }
		
		DAOFactory instance = new DAOFactory(connectionPool);
		return instance;
	}
	
	//Fournit une connexion à la DB
	Connection getConnection() throws SQLException {
		return connectionPool.getConnection();
	}
	
	//Récupèration de l'implémentation des différentes DAO
	public DAOStudent getDaoStudent() {
		return new DAOImplStudent(this);
	}
	public DAOUser getDaoUser() {
		return new DAOImplUser(this);
	}
	public DAOTeacher getDaoTeacher() {
		return new DAOImplTeacher(this);
	}
	
	public DAOUser getDaoAdmin() {
		return new DAOImplAdmin(this);
	}
	
	public DAOLog getDaoLog() {
		return new DAOImplLog(this);
	}
	
	public DAOHolyday getDaoHolyday() {
		return new DAOImplHolyday(this);
	}
}
