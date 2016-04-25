package ch.cpnv.timbreuse.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		User tempUser = new User();
		tempUser.setLastname("Lastname0");
		tempUser.setFirstname("firstname");
		List<String> classe = new ArrayList<String>();
		List<User> studentsTest = new ArrayList<User>();
		List<User> studentsS1a = new ArrayList<User>();
		classe.add("YE-Test");
		classe.add("YE-S1a");
		studentsTest.add(tempUser);
		tempUser.setLastname("Lastname1");
		studentsTest.add(tempUser);
		tempUser.setLastname("Lastname2");
		studentsTest.add(tempUser);
		tempUser.setLastname("Lastname3");
		studentsS1a.add(tempUser);
		tempUser.setLastname("Lastname4");
		studentsS1a.add(tempUser);
		tempUser.setLastname("Lastname5");
		studentsS1a.add(tempUser);
		tempUser.setLastname("Lastname6");
		studentsS1a.add(tempUser);
		tempUser.setLastname("Lastname7");
		studentsS1a.add(tempUser);

		request.setAttribute("classes", classe);
		request.setAttribute("Test", studentsTest);
		request.setAttribute("Sa1", studentsS1a);
		
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
