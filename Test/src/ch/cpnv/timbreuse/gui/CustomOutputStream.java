package ch.cpnv.timbreuse.gui;

import static ch.cpnv.timbreuse.dao.DAOUtility.currentDate;
import static ch.cpnv.timbreuse.dao.DAOUtility.currentTime;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;

import ch.cpnv.timbreuse.mathTime.SecondsPastMidnight;
/**
 * This class extends from OutputStream to redirect output to a JTextArrea
 * @author www.codejava.net
 *
 */
public class CustomOutputStream extends OutputStream {
	private JTextArea textArea;
	private boolean newLine = true;

	public CustomOutputStream(JTextArea textArea) {
		this.textArea = textArea;
	}

	@Override
	public void write(int b) throws IOException {
		// redirects data to the text area
		if(newLine) textArea.append(currentDate() + " / " + SecondsPastMidnight.toString(currentTime()) + " : " + String.valueOf((char)b));
		else textArea.append(String.valueOf((char)b));
		newLine = b == '\n';
		// scrolls the text area to the end of data
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
}
