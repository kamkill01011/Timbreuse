package ch.cpnv.timbreuse.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOFactory;
import ch.cpnv.timbreuse.dao.DAOUser;
import ch.cpnv.timbreuse.dao.DAOUsername;
import ch.cpnv.timbreuse.forms.ConnectionForm;

public class Connection extends HttpServlet {
	public static final String VIEW_CONNECTION = "/WEB-INF/connection.jsp";
	public static final String VIEW_STUDENT = "/student/info.jsp";
	public static final String VIEW_TEACHER = "/managestudents";
	public static final String VIEW_ADMIN = "/admin/adminPanel.jsp";
	public static final String USER_ATT = "user";
	public static final String FORM_ATT = "form";
	public static final String USER_SESSION_ATT ="userSession";
	private DAOUsername daoUser;

	public void init() {
		this.daoUser = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoUsername();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		this.getServletContext().getRequestDispatcher(VIEW_CONNECTION).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnectionForm connectionForm = new ConnectionForm(daoUser);
		User user = connectionForm.connectUser(request);
		HttpSession session = request.getSession();
		System.out.println(user.getUsername());
		System.out.println(user.getPermissionLevel());
		
		//Stockage du form et du bean dans l'obj request
		request.setAttribute(FORM_ATT, connectionForm);
		request.setAttribute(USER_ATT, user);//useless
		
		if(connectionForm.getErrors().isEmpty()) {
			session.setAttribute(USER_SESSION_ATT, user);
			response.sendRedirect(request.getContextPath() + "/connecting.jsp");
			//this.getServletContext().getRequestDispatcher(VIEW_CONNECTION).forward(request, response);//useless
			///Timbreuse/connecting.jsp
			/*if(userTemp.getPermissionLevel() == 3) {
				response.sendRedirect(request.getContextPath() + VIEW_STUDENT);
			} else if(userTemp.getPermissionLevel() == 2) {
				response.sendRedirect(request.getContextPath() + VIEW_TEACHER);
			} else if(userTemp.getPermissionLevel() == 1) {
				response.sendRedirect(request.getContextPath() + VIEW_ADMIN);
			} else {
				this.getServletContext().getRequestDispatcher(VIEW_CONNECTION).forward(request, response);
			}*/
		} else {
			session.setAttribute(USER_SESSION_ATT, null);
			this.getServletContext().getRequestDispatcher(VIEW_CONNECTION).forward(request, response);
		}
	}
}
