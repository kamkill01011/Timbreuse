package ch.cpnv.timbreuse.servlets;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jni.File;

import ch.cpnv.timbreuse.automation.MailTo;
import ch.cpnv.timbreuse.automation.txtWriter;
import ch.cpnv.timbreuse.beans.Log;
import ch.cpnv.timbreuse.beans.Student;
import ch.cpnv.timbreuse.beans.Teacher;
import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOFactory;
import ch.cpnv.timbreuse.dao.DAOImplLog;
import ch.cpnv.timbreuse.dao.DAOImplStudent;
import ch.cpnv.timbreuse.dao.DAOImplUser;
import ch.cpnv.timbreuse.dao.DAOLog;
import ch.cpnv.timbreuse.dao.DAOStudent;
import ch.cpnv.timbreuse.dao.DAOTeacher;
import ch.cpnv.timbreuse.dao.DAOUser;
import ch.cpnv.timbreuse.forms.AddTimeStudentsForm;
import ch.cpnv.timbreuse.forms.CreateStudentForm;
import ch.cpnv.timbreuse.forms.DeleteStudentForm;

import static ch.cpnv.timbreuse.dao.DAOUtility.generateUsername;
import static ch.cpnv.timbreuse.dao.DAOUtility.randomPassword;

/**
 * Servlet pour les enseigants qui leur permet de gèrer leurs élèves
 *
 */
@WebServlet("/managestudents")
public class ManageStudents extends HttpServlet{
	public static final String VIEW = "/teacher/manageStudents.jsp";
	public static final String USER_SESSION_ATT ="userSession";
	private DAOStudent daoStudent;
	private DAOTeacher daoTeacher;
	private DAOLog daoLog;
	private DAOUser daoUser;
	private String selectedClasse;
	private Student selectedStudent;
	private ArrayList<Log> logs = new ArrayList<>();

	public void init() {
		this.daoStudent = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoStudent();
		this.daoTeacher = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoTeacher();
		this.daoLog 	= ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoLog();
		this.daoUser	= ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoUser();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//récupère l'ensaignant connecté notamment pour afficher ses classes 
		HttpSession session = request.getSession();
		Teacher teacher = daoTeacher.findTeacher(((User)(session.getAttribute(USER_SESSION_ATT))).getUsername());
		request.setAttribute("currentTeacher", teacher);
		
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Teacher teacher = daoTeacher.findTeacher(((User)(session.getAttribute(USER_SESSION_ATT))).getUsername());
		//addTime to Student(s)
		//change la classe sélectionnle si elle a changée
		String tempSelectedClasse = (String) request.getParameter("classe");
		if(tempSelectedClasse != null) {
			logs.clear();
			selectedStudent = null;
			selectedClasse = tempSelectedClasse;	
		}

		if(request.getParameter("listPassword") != null) {	
			BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream("P:/files/"+selectedClasse+".txt"));
			fileOut.write("".getBytes());
			fileOut.close();
		}

		//recherche les élèves de la classe (si une classe est sélectionnée)
		ArrayList<Student> studentsInClass = new ArrayList<Student>();
		if(selectedClasse != null) {
			studentsInClass = daoTeacher.listClass(selectedClasse, daoStudent);
			request.setAttribute("studentsInClass", studentsInClass);	
			for (int i = 0; i < studentsInClass.size(); i++) {
				if(request.getParameter("logs" + studentsInClass.get(i).getId()) != null) {
					selectedStudent = studentsInClass.get(i);
					break;//quitter la boucle car on affiche que les logs d'un seul élève
				}else if(request.getParameter("id" + studentsInClass.get(i).getId()) != null) {
					if(request.getParameter("addTime") != null) {
						AddTimeStudentsForm addTimeForm = new AddTimeStudentsForm(daoStudent, studentsInClass);
						Student student = ((DAOImplStudent)daoStudent).findStudentById(studentsInClass.get(i).getId());
						((DAOImplStudent)daoStudent).addTimeStudent(student, addTimeForm.getTimeDiffField(request));
						daoLog.addTimeLog(student, addTimeForm.getTimeDiffField(request));
					}
					if(request.getParameter("newStatus") != null) {
						Student student = ((DAOImplStudent)daoStudent).findStudentById(studentsInClass.get(i).getId());
						daoStudent.changeStatus(student, daoLog.addLog(student));
					}
					if(request.getParameter("sickDay") != null) {
						Student student = ((DAOImplStudent)daoStudent).findStudentById(studentsInClass.get(i).getId());
						((DAOImplStudent)daoStudent).changeStatus(student, ((DAOImplLog)daoLog).setSicknessLeaveLog(student));
					}
				}

			}

			if(request.getParameter("listPassword") != null) {		
				for (int j = 0; j < studentsInClass.size(); j++) {
					User user = ((DAOImplUser)daoUser).getDefaultPassword(studentsInClass.get(j).getFirstname(), studentsInClass.get(j).getLastname());
					if(user != null) {
						txtWriter.writeListPassword(user, selectedClasse);
						String password = randomPassword();
						MailTo.sendEmail(user, password);
						daoUser.setNewPassword(user, password);
					}
				}
				this.getServletContext().getRequestDispatcher("/files/"+selectedClasse+".txt").forward(request, response);
				return;
			}
		}

		//à supprimer ou modifier
		if(request.getParameter("add")!=null) {
			CreateStudentForm createForm = new CreateStudentForm(daoStudent);
			if(daoStudent.find(generateUsername(createForm.isStudent(request).getFirstname(), createForm.isStudent(request).getLastname())) == null) {
				daoStudent.create(createForm.isStudent(request), daoTeacher);
			}
		}
		if(request.getParameter("delete")!=null) {
			DeleteStudentForm deleteForm = new DeleteStudentForm(daoStudent);
			daoStudent.delete(deleteForm.selectStudentToDelete(request));
		}

		if(selectedStudent != null) logs = daoLog.getStudentLogs(selectedStudent);

		if(selectedClasse != null) request.setAttribute("studentsInClass", daoTeacher.listClass(selectedClasse, daoStudent));

		request.setAttribute("logs", logs);
		request.setAttribute("currentTeacher", teacher);
		request.setAttribute("selectedClasse", selectedClasse);

		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
		//response.sendRedirect(request.getContextPath()+"/managestudents");
	}
}
