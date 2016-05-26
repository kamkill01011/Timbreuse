package ch.cpnv.timbreuse.gui;

import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CustomConsole {
	
	public static void init() {
		
		JTextArea textArea = new JTextArea(50, 10);
		textArea.setEditable(false);

		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
		System.setOut(printStream);
		System.setErr(printStream);

		JFrame frame = new JFrame("\"Console\"");
		JScrollPane sp = new JScrollPane(textArea);
		frame.add(sp);
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}