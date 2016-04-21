package ch.cpnv.timbreuse.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOFactory;
import ch.cpnv.timbreuse.dao.DAOUser;
import ch.cpnv.timbreuse.forms.CreateStudent;
import ch.cpnv.timbreuse.forms.DeleteStudent;
import ch.cpnv.timbreuse.forms.StudentResearch;

public class ManageStudents extends HttpServlet{
	public static final String VIEW = "/teacher/manageStudents.jsp";
	private DAOUser daoUser;

	public void init() {
		this.daoUser = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoUser();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("research")!=null) {
			StudentResearch researchForm = new StudentResearch(daoUser);
			User user = researchForm.researchUser(request);
			request.setAttribute("researchStudent", user);
		}
		if(request.getParameter("add")!=null) {
			CreateStudent createForm = new CreateStudent(daoUser);
			daoUser.create(createForm.isUser(request));
		}
		if(request.getParameter("delete")!=null) {
			DeleteStudent deleteForm = new DeleteStudent(daoUser);
			daoUser.delete(deleteForm.selectUserToDelete(request));
		}
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}
