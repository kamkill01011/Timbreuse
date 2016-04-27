package ch.cpnv.timbreuse.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthSpinnerUI;

import ch.cpnv.timbreuse.beans.Student;
import ch.cpnv.timbreuse.beans.Teacher;
import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOFactory;
import ch.cpnv.timbreuse.dao.DAOStudent;
import ch.cpnv.timbreuse.dao.DAOTeacher;
import ch.cpnv.timbreuse.dao.DAOUser;
import ch.cpnv.timbreuse.forms.CreateStudentForm;
import ch.cpnv.timbreuse.forms.DeleteStudentForm;
import ch.cpnv.timbreuse.forms.StudentResearchForm;

/**
 * Servlet pour les enseigants qui leur permet de gèrer leurs élèves
 *
 */
public class ManageStudents extends HttpServlet{
	public static final String VIEW = "/teacher/manageStudents.jsp";
	private DAOStudent daoStudent;
	private DAOTeacher daoTeacher;

	public void init() {
		this.daoStudent = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoStudent();
		this.daoTeacher = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoTeacher();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		Teacher teacher = daoTeacher.findTeacher(((User)(session.getAttribute("userSession"))).getUsername());
		request.setAttribute("currentTeacher", teacher);
		
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Teacher teacher = daoTeacher.findTeacher(((User)(session.getAttribute("userSession"))).getUsername());
		String selectedClasse = (String) request.getParameter("classe");
		
		ArrayList<User> studentsInClass = new ArrayList<User>();
		
		if(request.getParameter("classe") != null) {
			studentsInClass = daoTeacher.listClass(selectedClasse);
			request.setAttribute("studentsInClass", studentsInClass);
		}
		request.setAttribute("currentTeacher", teacher);
		request.setAttribute("selectedClasse", selectedClasse);
		
		if(request.getParameter("research")!=null) {
			StudentResearchForm researchForm = new StudentResearchForm(daoStudent);
			Student student = researchForm.researchUser(request);
			request.setAttribute("researchStudent", student);
		}
		if(request.getParameter("add")!=null) {
			CreateStudentForm createForm = new CreateStudentForm(daoStudent);
			daoStudent.create(createForm.isStudent(request));
		}
		if(request.getParameter("delete")!=null) {
			DeleteStudentForm deleteForm = new DeleteStudentForm(daoStudent);
			daoStudent.delete(deleteForm.selectStudentToDelete(request));
		}
		
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}
