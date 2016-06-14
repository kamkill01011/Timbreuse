package ch.cpnv.timbreuse.automation;

import ch.cpnv.timbreuse.beans.User;

import com.moyosoft.connector.com.ComponentObjectModelException;
import com.moyosoft.connector.exception.LibraryNotFoundException;
import com.moyosoft.connector.ms.outlook.Outlook;
import com.moyosoft.connector.ms.outlook.folder.FolderType;
import com.moyosoft.connector.ms.outlook.folder.OutlookFolder;
import com.moyosoft.connector.ms.outlook.item.ItemType;
import com.moyosoft.connector.ms.outlook.mail.OutlookMail;

import static ch.cpnv.timbreuse.dao.DAOUtility.generateEmail;


/**
 * Envoie un mail avec un nouveau mot de passe avec le compte Outlook de la session du demandeur
 * @author Mathieu.JEE Kamil.AMRANI
 *
 */
public class MailTo {

	private MailTo() {
	}

	/**
	 * @param user Nom de l'utilisateur
	 * @param password Mot de passe de l'utilisateur
	 */
	public static void sendEmail(User user, String password) {
		try {
			Outlook outlookApplication = new Outlook();
			OutlookFolder outbox = outlookApplication.getDefaultFolder(FolderType.OUTBOX);
			OutlookMail mail = (OutlookMail) outbox.createItem(ItemType.MAIL);
			mail.setSubject("Timbreuse Nouveau Mot de Passe");
			mail.setTo(generateEmail(user.getFirstname(), user.getLastname()));
			mail.setBody("Voici votre nouveau mot de passe: "+password+"\nVeuillez le changer lors de votre connexion.");
			mail.send();
			outlookApplication.dispose();

		} catch(ComponentObjectModelException ex) {
			System.out.println("COM error has occured: ");
			ex.printStackTrace();
		} catch(LibraryNotFoundException ex) {
			// If this error occurs, verify the file 'moyocore.dll' is present in java.library.path
			System.out.println("The Java Outlook Library has not been found.");
			ex.printStackTrace();
		}
	}
}