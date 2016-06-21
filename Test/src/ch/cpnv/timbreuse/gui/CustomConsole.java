package ch.cpnv.timbreuse.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.PrintStream;

import javax.servlet.http.HttpServletResponse;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import ch.cpnv.timbreuse.dao.DAOLog;
import ch.cpnv.timbreuse.dao.DAOStudent;

import static ch.cpnv.timbreuse.automation.Automation.checkoutStudent;

/**
 * Console pour l'application
 * @author Mathieu.JEE Kamil.AMRANI
 *
 */
public class CustomConsole {
	
	private JTextArea textArea = new JTextArea(50, 10);
	private DAOStudent daoStudent;
	private DAOLog daoLog;
	HttpServletResponse response;
	/**
	 * Initialise la console
	 */
	public void init(DAOStudent daoStudent, DAOLog daoLog) {
		this.daoStudent = daoStudent;
		this.daoLog = daoLog;
		
		textArea.setEditable(true);

		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
		System.setOut(printStream);
		System.setErr(printStream);
		JFrame frame = new JFrame("Console");
		JScrollPane sp = new JScrollPane(textArea);
		
		textArea.addKeyListener(new CustomKeyListener());
		
		frame.add(sp);
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//si on ferme la console l'application s'arrête
		frame.setVisible(true);
	}
	
	private class CustomKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent event) {
			if(event.getKeyCode() == KeyEvent.VK_ENTER) {
				int last  = textArea.getLineCount() - 1;
				int start = 0;
				int end = 0;
				try {
					start = textArea.getLineStartOffset(last);
					end = textArea.getLineEndOffset(last);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				String lastLine = textArea.getText().substring(start, end);
				checkoutStudent(lastLine, daoStudent, daoLog);
				try {
					response.sendRedirect("/Timbreuse/logout");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		@Override
		public void keyReleased(KeyEvent event) {
		}

		@Override
		public void keyTyped(KeyEvent event) {
		}
	}
}
