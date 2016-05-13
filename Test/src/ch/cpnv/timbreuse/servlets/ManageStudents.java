package ch.cpnv.timbreuse.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.cpnv.timbreuse.beans.Student;
import ch.cpnv.timbreuse.beans.Teacher;
import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOFactory;
import ch.cpnv.timbreuse.dao.DAOImplStudent;
import ch.cpnv.timbreuse.dao.DAOStudent;
import ch.cpnv.timbreuse.dao.DAOTeacher;
import ch.cpnv.timbreuse.forms.AddTimeStudentsForm;
import ch.cpnv.timbreuse.forms.CreateStudentForm;
import ch.cpnv.timbreuse.forms.DeleteStudentForm;
import ch.cpnv.timbreuse.forms.StudentResearchForm;

/**
 * Servlet pour les enseigants qui leur permet de gèrer leurs élèves
 *
 */
@WebServlet("/managestudents")
public class ManageStudents extends HttpServlet{
	public static final String VIEW = "/teacher/manageStudents.jsp";
	private DAOStudent daoStudent;
	private DAOTeacher daoTeacher;
	private String selectedClasse;

	public void init() {
		this.daoStudent = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoStudent();
		this.daoTeacher = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoTeacher();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//récupère l'ensaignant connecté notamment pour afficher ses classes 
		HttpSession session = request.getSession();
		Teacher teacher = daoTeacher.findTeacher(((User)(session.getAttribute("userSession"))).getUsername());
		request.setAttribute("currentTeacher", teacher);
		
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Teacher teacher = daoTeacher.findTeacher(((User)(session.getAttribute("userSession"))).getUsername());
		//addTime to Student(s)
		//change la classe sélectionnle si elle a changée
		String tempSelectedClasse = (String) request.getParameter("classe");
		if(tempSelectedClasse != null) {
			selectedClasse = tempSelectedClasse;
		}
		//recherche les élèves de la classe (si une classe est sélectionnée)
		ArrayList<Student> studentsInClass = new ArrayList<Student>();
		if(selectedClasse != null) {
			studentsInClass = daoTeacher.listClass(selectedClasse, daoStudent);
			request.setAttribute("studentsInClass", studentsInClass);
			if(request.getParameter("addTime") != null) {
				AddTimeStudentsForm addTimeForm = new AddTimeStudentsForm(daoStudent, studentsInClass);
				for (int i = 0; i < studentsInClass.size(); i++) {
					if(request.getParameter("id" + studentsInClass.get(i).getId()) != null) {
						Student student = ((DAOImplStudent)daoStudent).findStudentById(studentsInClass.get(i).getId());
						((DAOImplStudent)daoStudent).addTimeStudent(student, addTimeForm.getTimeDiffField(request));
					}
				}
			}
			if(request.getParameter("newStatus") != null) {
				for (int i = 0; i < studentsInClass.size(); i++) {
					if(request.getParameter("id" + studentsInClass.get(i).getId()) != null) {
						Student student = ((DAOImplStudent)daoStudent).findStudentById(studentsInClass.get(i).getId());
						String newStatus = ((DAOImplLogs)daoLogs).addLog(student);
						((DAOStudent)daoStudent).changeStatus(student, newStatus);
					}
				}
			}
		}
		
		//enregistre quels élèves sont sélectionnés
		ArrayList<Boolean> selectedStudents = new ArrayList<Boolean>();
		for (int i = 0; i < studentsInClass.size(); i++) {
			selectedStudents.add(request.getParameter("id" + studentsInClass.get(i).getId()) != null);
		}
		
		request.setAttribute("selectedStudents", selectedStudents);
		request.setAttribute("currentTeacher", teacher);
		request.setAttribute("selectedClasse", selectedClasse);
		
		//à supprimer ou modifier
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
		//---
		
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}
