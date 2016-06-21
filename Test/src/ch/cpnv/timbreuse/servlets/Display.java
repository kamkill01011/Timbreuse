package ch.cpnv.timbreuse.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.cpnv.timbreuse.beans.Student;
import ch.cpnv.timbreuse.dao.DAOFactory;
import ch.cpnv.timbreuse.dao.DAOLog;
import ch.cpnv.timbreuse.dao.DAOStudent;

import static ch.cpnv.timbreuse.automation.Automation.checkoutStudent;
/**
 * Servlet affichage infos sur l'ecran timbreuse
 * @author Mathieu.JEE Kamil.AMRANI
 *
 */
@WebServlet("/display")
public class Display extends HttpServlet {
	public static final String WELCOME_VIEW = "/display/welcomeDisplay.jsp";
	public static final String SHOW_VIEW = "/display/showDisplay.jsp";
	public static final String USER_SESSION_ATT ="userSession";
	private DAOStudent daoStudent;
	private DAOLog daoLog;

	@Override
	public void init() {
		this.daoStudent = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoStudent();
		this.daoLog = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoLog();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(-1);
		String tag = request.getParameter("tagText");
		try {
			checkoutStudent(tag, daoStudent, daoLog);
			Student student = daoStudent.findByTag(tag);
			request.setAttribute("taggedStudent", student);
		} catch(Exception e) {
			request.setAttribute("taggedStudent", new Student());
		}
		finally {
			if(tag == null) {
				this.getServletContext().getRequestDispatcher(WELCOME_VIEW).forward(request, response);
			} else {
				this.getServletContext().getRequestDispatcher(SHOW_VIEW).forward(request, response);
			}
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(-1);
		Student student = new Student();
		student.setStatus("INI");
		request.setAttribute("taggedStudent", student);
		this.getServletContext().getRequestDispatcher(SHOW_VIEW).forward(request, response);
	}
}




