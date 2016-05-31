package ch.cpnv.timbreuse.automation;

import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOUtility;

import javax.servlet.http.*;

import com.moyosoft.connector.com.ComponentObjectModelException;
import com.moyosoft.connector.exception.LibraryNotFoundException;
import com.moyosoft.connector.ms.outlook.Outlook;
import com.moyosoft.connector.ms.outlook.folder.FolderType;
import com.moyosoft.connector.ms.outlook.folder.OutlookFolder;
import com.moyosoft.connector.ms.outlook.item.ItemType;
import com.moyosoft.connector.ms.outlook.mail.OutlookMail;

import javax.servlet.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.activation.*;
import java.io.*;
import java.util.*;

import static ch.cpnv.timbreuse.dao.DAOUtility.generateEmail;

import java.util.Properties;

public class MailTo {

	private MailTo() {
	}

	public static void sendEmail(User user) {
		try {
			Outlook outlookApplication = new Outlook();
			OutlookFolder outbox = outlookApplication.getDefaultFolder(FolderType.OUTBOX);
			OutlookMail mail = (OutlookMail) outbox.createItem(ItemType.MAIL);
			mail.setSubject("Timbreuse Nouveau Mot de Passe");
			System.out.println(user.getFirstname());
			System.err.println(user.getLastname());
			mail.setTo(generateEmail(user.getFirstname(), user.getLastname()));
			mail.setBody("***************************TEST*************************** \n Voici votre nouveau mot de passe: "+DAOUtility.randomPassword()+"\n Veuillez le changer lors de votre connexion.");
			mail.send();
			outlookApplication.dispose();

		} catch(ComponentObjectModelException ex)
		{
			System.out.println("COM error has occured: ");
			ex.printStackTrace();
		}
		catch(LibraryNotFoundException ex)
		{
			// If this error occurs, verify the file 'moyocore.dll' is present
			// in java.library.path
			System.out.println("The Java Outlook Library has not been found.");
			ex.printStackTrace();
		}
	}
}