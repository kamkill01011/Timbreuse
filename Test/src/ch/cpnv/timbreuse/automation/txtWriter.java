package ch.cpnv.timbreuse.automation;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ch.cpnv.timbreuse.beans.User;

/**
 * Pas utilisé
 * @author Mathieu.JEE Kamil.AMRANI
 *
 */
@Deprecated
public class txtWriter {

	private txtWriter() {
	}

	/**
	 * @param user Nom de l'utilisateur
	 * @param selectedClass Nom de la classe de l'utilisateur (aussi nom du fichier)
	 */
	public static void writeListPassword(User user, String selectedClass) {
		BufferedOutputStream fileOut = null;
		try {
			fileOut = new BufferedOutputStream(new FileOutputStream("P:/files/"+selectedClass+".txt", true));
			fileOut.write("\r\n".getBytes());
			fileOut.write("Adresse: http://172.16.104.176:8080/Timbreuse/connection".getBytes());
			fileOut.write("\r\n".getBytes());
			fileOut.write(("Nom d'utilisateur: "+user.getUsername()).getBytes());
			fileOut.write("\r\n".getBytes());
			fileOut.write(("Mot de pass: "+user.getPassword()).getBytes());
			fileOut.write("\r\n".getBytes());
			fileOut.write("Veuillez changer ce mot de passe lors de votre première connexion.".getBytes());
			fileOut.write("\r\n".getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(fileOut!=null) {
					fileOut.flush();
					fileOut.close();	
				}    		
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
