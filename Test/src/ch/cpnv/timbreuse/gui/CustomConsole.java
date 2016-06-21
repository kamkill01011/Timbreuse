package ch.cpnv.timbreuse.gui;

import java.io.PrintStream;

import javax.servlet.http.HttpServletResponse;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Console pour l'application
 * @author Mathieu.JEE Kamil.AMRANI
 *
 */
public class CustomConsole {
	
	private JTextArea textArea = new JTextArea(50, 10);
	HttpServletResponse response;
	/**
	 * Initialise la console
	 */
	public void init() {
		
		textArea.setEditable(false);

		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
		System.setOut(printStream);
		System.setErr(printStream);
		JFrame frame = new JFrame("Console");
		JScrollPane sp = new JScrollPane(textArea);
		
		frame.add(sp);
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//si on ferme la console l'application s'arrête
		frame.setVisible(true);
	}
}
