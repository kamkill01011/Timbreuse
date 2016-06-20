package ch.cpnv.timbreuse.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ch.cpnv.timbreuse.beans.Student;
import ch.cpnv.timbreuse.dao.DAOStudent;

public class PopUpFromOptionPane {

    public void changeStudentTagPopUp(DAOStudent daoStudent, Student student) {
    	JFrame parent = new JFrame();
        String oldTag = "";
        if(student.getTag() != null) oldTag = student.getTag();
        parent.setVisible(true);
    	String newTag = JOptionPane.showInputDialog(parent, "Nouveau Tag : ", oldTag);
    	if(newTag != null) {
        	if(daoStudent.findByTag(newTag) == null) {
        		System.out.println(student.getLastname() + " <= " + newTag);
        		daoStudent.changeTag(student, newTag);
        	}
    	}
    	parent.dispose();
    }
}