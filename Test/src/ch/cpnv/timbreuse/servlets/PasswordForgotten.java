package ch.cpnv.timbreuse.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.cpnv.timbreuse.automation.MailTo;
import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOFactory;
import ch.cpnv.timbreuse.dao.DAOUser;
import ch.cpnv.timbreuse.dao.DAOUtility;
import ch.cpnv.timbreuse.forms.PasswordForgottenForm;

/**
 * Servlet qui permet aux utilisateurs de récupérer leurs mots de passe
 * @author Mathieu.JEE Kamil.AMRANI
 *
 */
@WebServlet("/passwordforgotten")
public class PasswordForgotten extends HttpServlet {
	public static final String VIEW_PASSWORDFORGOTTEN = "/WEB-INF/passwordForgotten.jsp";
	public static final String VIEW_ERR = "/error";
	private DAOUser daoUser;

	@Override
	public void init() {
		this.daoUser = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoUser();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VIEW_PASSWORDFORGOTTEN).forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			PasswordForgottenForm passwordForgottenForm = new PasswordForgottenForm();
			User user = daoUser.findUser(passwordForgottenForm.getUsernamePasswordForgotten(request));
			if(passwordForgottenForm.getErrors().isEmpty()) {
				String password = DAOUtility.randomPassword();
				//MailTo.sendEmail(user, password);
				daoUser.setNewPassword(user, password, false);
			}
			response.sendRedirect(request.getContextPath()+"/logout");
		} catch(Exception e) {
			response.sendRedirect(request.getContextPath() + VIEW_ERR);
		}
	}
}
