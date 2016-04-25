package ch.cpnv.timbreuse.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOFactory;
import ch.cpnv.timbreuse.dao.DAOUser;
import ch.cpnv.timbreuse.forms.StudentResearch;

public class StudentInfo extends HttpServlet {
	public static final String VIEW = "/student/info.jsp";
	private DAOUser daoUser;
	
	public void init() {
		this.daoUser = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoUser();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
		StudentResearch researchForm = new StudentResearch(daoUser);
		User user = researchForm.researchUser(request);
		request.setAttribute("currentStudent", user);
	}
}
