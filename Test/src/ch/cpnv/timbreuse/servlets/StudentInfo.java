package ch.cpnv.timbreuse.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOFactory;
import ch.cpnv.timbreuse.dao.DAOUser;

/**
 * Servlet qui affiche les information d'un élève
 *
 */
@WebServlet("/info")
public class StudentInfo extends HttpServlet {
	public static final String VIEW = "/student/info.jsp";
	private DAOUser daoUser;
	
	public void init() {
		this.daoUser = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoUsername();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		
		User user = daoUser.findStudent(((User)session.getAttribute("userSession")).getUsername());
		
		request.setAttribute("currentStudent", user);
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}
