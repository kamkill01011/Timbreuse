package ch.cpnv.timbreuse.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOFactory;
import ch.cpnv.timbreuse.dao.DAOUser;
import ch.cpnv.timbreuse.forms.ConnexionForm;

public class Connexion extends HttpServlet {
	public static final String VIEW = "/WEB-INF/connexion.jsp";
	private DAOUser daoUser;

	public void init() {
		this.daoUser = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoUser();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnexionForm connexionForm = new ConnexionForm(daoUser);
		User user = connexionForm.userConnexion(request);
		
		
		/*
		StudentResearch researchForm = new StudentResearch(daoUser);
		User user = researchForm.researchUser(request);
		request.setAttribute("researchStudent", user);
		
		CreateStudent createForm = new CreateStudent(daoUser);
		daoUser.create(createForm.isUser(request));*/
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}
