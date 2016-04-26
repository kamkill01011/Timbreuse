package ch.cpnv.timbreuse.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.cpnv.timbreuse.beans.Teacher;
import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOFactory;
import ch.cpnv.timbreuse.dao.DAOTeacher;
import ch.cpnv.timbreuse.dao.DAOUser;
import ch.cpnv.timbreuse.forms.CreateStudent;
import ch.cpnv.timbreuse.forms.DeleteStudent;
import ch.cpnv.timbreuse.forms.StudentResearch;

public class ManageStudents extends HttpServlet{
	public static final String VIEW = "/teacher/manageStudents.jsp";
	private DAOUser daoUser;
	private DAOTeacher daoTeacher;

	public void init() {
		this.daoUser = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoUser();
		this.daoTeacher = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoTeacher();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		Teacher teacher = daoTeacher.findTeacher(((Teacher)session.getAttribute("userSession")).getFirstname(), ((Teacher)session.getAttribute("userSession")).getLastname());
		request.setAttribute("currentTeacher", teacher);
		
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<String> classes = new ArrayList<String>();
		List<User> studentsTest = new ArrayList<User>();
		classes.add("Test");
		classes.add("S1a");
		
		User tempUser = new User();
		tempUser.setFirstname("fnasdfasdf");
		tempUser.setLastname("lasdfasdfn");
		tempUser.setEmail("easdfsad.asdfasdfasf@cpnv.ch");
		
		studentsTest.add(tempUser);
		studentsTest.add(tempUser);
		studentsTest.add(tempUser);

		request.setAttribute("classes", classes);
		request.setAttribute("Test", studentsTest);
		
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
