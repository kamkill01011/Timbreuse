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
import ch.cpnv.timbreuse.forms.ConnectionForm;

public class Connection extends HttpServlet {
	public static final String VIEW = "/WEB-INF/connection.jsp";
	public static final String USER_ATT = "user";
	public static final String FORM_ATT = "form";
	public static final String USER_SESSION_ATT ="userSession";
	private DAOUser daoUser;

	public void init() {
		this.daoUser = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoUser();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnectionForm connectionForm = new ConnectionForm(daoUser);
		User user = connectionForm.connectUser(request);
		HttpSession session = request.getSession();
		
		if(connectionForm.getErrors().isEmpty()) {
			session.setAttribute(USER_SESSION_ATT, user);
		} else {
			session.setAttribute(USER_SESSION_ATT, null);
		}
		
		//Stockage du form et du bean dans l'obj request
		request.setAttribute(FORM_ATT, connectionForm);
		request.setAttribute(USER_ATT, user);
		
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}