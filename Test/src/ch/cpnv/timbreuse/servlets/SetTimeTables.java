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
import ch.cpnv.timbreuse.dao.DAOStudent;
import ch.cpnv.timbreuse.dao.DAOTeacher;
import ch.cpnv.timbreuse.mathTime.SecondsPastMidnight;

import static ch.cpnv.timbreuse.dao.DAOUtility.classes;

/**
 * Servlet pour les enseigants qui leur permet de gèrer leurs élèves
 *
 */
@WebServlet("/settimetables")
public class SetTimeTables extends HttpServlet{
	public static final String VIEW = "/teacher/setTimeTables.jsp";
	public static final String USER_SESSION_ATT ="userSession";
	private DAOTeacher daoTeacher;
	private DAOStudent daoStudent;
	
	public void init() {
		this.daoTeacher = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoTeacher();
		this.daoStudent = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoStudent();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Teacher teacher = daoTeacher.findTeacher(((User)(session.getAttribute(USER_SESSION_ATT))).getUsername());
		ArrayList<String> classes = classes(teacher.getClasse());
		ArrayList<String[]> timeTables = new ArrayList<>();
		for (int i = 0; i < classes.size(); i++) {
			timeTables.add(daoTeacher.getClasseTimeTable(classes.get(i), daoStudent));
		}
		request.setAttribute("timeTables", timeTables);
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Teacher teacher = daoTeacher.findTeacher(((User)(session.getAttribute(USER_SESSION_ATT))).getUsername());
		ArrayList<String> classes = classes(teacher.getClasse());
		ArrayList<String[]> timeTables = new ArrayList<>();
		for (int i = 0; i < classes.size(); i++) {
			if(request.getParameter(classes.get(i)) != null) {
				int[] newTimeTable = new int[7];
				for (int j = 0; j < newTimeTable.length; j++) {
					newTimeTable[j] = SecondsPastMidnight.stringToInt(request.getParameter(classes.get(i) + (j+1)));
					//System.out.println(request.getParameter(classes.get(i) + (j+1)));
				}
				ArrayList<Student> students = daoTeacher.listClass(classes.get(i), daoStudent);
				for (int j = 0; j < students.size(); j++) {
					daoStudent.changeTimeTables(newTimeTable, students.get(j));
				}
			}
			timeTables.add(daoTeacher.getClasseTimeTable(classes.get(i), daoStudent));
		}
		request.setAttribute("timeTables", timeTables);
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}
