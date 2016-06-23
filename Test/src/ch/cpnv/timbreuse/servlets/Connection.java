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
import ch.cpnv.timbreuse.forms.ConnectionForm;

/**
 * Servlet qui permet aux utilisateurs de se connecter
 * @author Mathieu.JEE Kamil.AMRANI
 *
 */
@WebServlet("/connection")
public class Connection extends HttpServlet {
	public static final String VIEW_CONNECTION = "/WEB-INF/connection.jsp";
	public static final String CONNECTING = "/connecting.jsp";
	public static final String VIEW_STUDENT = "/info";
	public static final String VIEW_TEACHER = "/managestudents";
	public static final String VIEW_ADMIN = "/admin/adminPanel.jsp";
	public static final String USER_ATT = "user";
	public static final String FORM_ATT = "form";
	public static final String USER_SESSION_ATT ="userSession";
	public static final String VIEW_ERR = "/error";
	private DAOUser daoUser;

	@Override
	public void init() {
		this.daoUser = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoUser();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		this.getServletContext().getRequestDispatcher(VIEW_CONNECTION).forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ConnectionForm connectionForm = new ConnectionForm(daoUser);
			User user = connectionForm.connectUser(request);
			HttpSession session = request.getSession();	
			request.setAttribute(FORM_ATT, connectionForm);

			if(connectionForm.getErrors().isEmpty()) {
				System.out.println(user.getUsername() + " connecting");
				session.setAttribute(USER_SESSION_ATT, user);
				response.sendRedirect(request.getContextPath() + CONNECTING);
			} else {
				session.setAttribute(USER_SESSION_ATT, null);
				this.getServletContext().getRequestDispatcher(VIEW_CONNECTION).forward(request, response);
			}
		} catch(Exception e) {
			response.sendRedirect(request.getContextPath() + VIEW_ERR);
		}
	}
}
