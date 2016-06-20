package ch.cpnv.timbreuse.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ch.cpnv.timbreuse.beans.Student;
import ch.cpnv.timbreuse.dao.DAOStudent;

public class PopUpFromOptionPane {

    public void changeStudentTagPopUp(DAOStudent daoStudent, Student student) {
    	JFrame parent = new JFrame();
        parent.pack();
        parent.setVisible(true);
    	String newTag = JOptionPane.showInputDialog(parent, "What is your name?", null);
    	System.out.println(newTag);
    	daoStudent.changeTag(student, newTag);
    	parent.setVisible(false);
    	parent.
    }
}