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
import ch.cpnv.timbreuse.forms.ChangePasssowordForm;

/**
 * Servlet de changement de mot de passe
 * @author Mathieu.JEE Kamil.AMRANI
 *
 */
@WebServlet("/changepassword")
public class ChangePassword extends HttpServlet {
	public static final String VIEW_CHANGEPASSWORD = "/WEB-INF/changePassword.jsp";
	public static final String CONNECTING = "/connecting.jsp";
	public static final String USER_ATT = "user";
	public static final String FORM_ATT = "form";
	public static final String USER_SESSION_ATT ="userSession";
	private DAOUser daoUser;

	@Override
	public void init() {
		this.daoUser = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoUser();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		this.getServletContext().getRequestDispatcher(VIEW_CHANGEPASSWORD).forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ChangePasssowordForm passwordForm = new ChangePasssowordForm();
		HttpSession session = request.getSession();
		User user = daoUser.findUser(((User)session.getAttribute("userSession")).getUsername());
		try {
			passwordForm.testChangePassword(request, user);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute(FORM_ATT, passwordForm);
		//change le mot de passe si les informations sont correctes
		if(passwordForm.getErrors().isEmpty()) {
			daoUser.setNewPassword(user, passwordForm.getNewPassword(request));
			response.sendRedirect(request.getContextPath() + CONNECTING);
		} else {
			this.getServletContext().getRequestDispatcher(VIEW_CHANGEPASSWORD).forward(request, response);
		}
	}
}
