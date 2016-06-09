package ch.cpnv.timbreuse.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.cpnv.timbreuse.automation.MailTo;
import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOFactory;
import ch.cpnv.timbreuse.dao.DAOUser;
import ch.cpnv.timbreuse.dao.DAOUtility;
import ch.cpnv.timbreuse.forms.PasswordForgottenForm;

@WebServlet("/passwordforgotten")
public class PasswordForgotten extends HttpServlet {
	public static final String VIEW_PASSWORDFORGOTTEN = "/WEB-INF/passwordForgotten.jsp";
	private DAOUser daoUser;
	
	public void init() {
		this.daoUser = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoUser();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VIEW_PASSWORDFORGOTTEN).forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PasswordForgottenForm passwordForgottenForm = new PasswordForgottenForm(daoUser);
		User user = daoUser.findUser(passwordForgottenForm.getUsernamePasswordForgotten(request));
		if(passwordForgottenForm.getErrors().isEmpty()) {
			String password = DAOUtility.randomPassword();
			MailTo.sendEmail(user, password);
			daoUser.setNewPassword(user, password);
		}
		//this.getServletContext().getRequestDispatcher("/logout").forward(request, response);
		response.sendRedirect(request.getContextPath()+"/logout");
	}
}
