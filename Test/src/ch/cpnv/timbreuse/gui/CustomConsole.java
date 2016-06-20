package ch.cpnv.timbreuse.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

/**
 * Console pour l'application
 * @author Mathieu.JEE Kamil.AMRANI
 *
 */
public class CustomConsole {
	private JTextArea textArea = new JTextArea(50, 10);
	
	/**
	 * Initialise la console
	 */
	public void init() {
		textArea.setEditable(true);

		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
		System.setOut(printStream);
		System.setErr(printStream);
		JFrame frame = new JFrame("\"Console\"");
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
