package ch.cpnv.timbreuse.servlets;

import java.io.IOException;
import java.rmi.ServerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOFactory;
import ch.cpnv.timbreuse.dao.DAOUser;
import ch.cpnv.timbreuse.forms.StudentResearch;

public class ManageStudents extends HttpServlet{
	public static final String VIEW = "/WEB-INF/manageStudents.jsp";
	private DAOUser daoUser;
	
	public void init() {
		this.daoUser = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoUser();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StudentResearch form = new StudentResearch(daoUser);
		User user = form.researchUser(request);
		System.out.println("MaganeStudent "+user.getLastname());
		request.setAttribute("researchStudent", user);
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
	
}
