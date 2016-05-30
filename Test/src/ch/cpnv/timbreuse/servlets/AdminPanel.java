package ch.cpnv.timbreuse.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.cpnv.timbreuse.beans.Teacher;
import ch.cpnv.timbreuse.dao.DAOFactory;
import ch.cpnv.timbreuse.dao.DAOImplUser;
import ch.cpnv.timbreuse.dao.DAOTeacher;
import ch.cpnv.timbreuse.dao.DAOUser;
import ch.cpnv.timbreuse.forms.CreateAdminForm;
import ch.cpnv.timbreuse.forms.CreateTeacherForm;
import ch.cpnv.timbreuse.forms.DeleteAdminForm;
import ch.cpnv.timbreuse.forms.DeleteTeacherForm;

import static ch.cpnv.timbreuse.dao.DAOUtility.generateEmail;

@WebServlet("/admin")
public class AdminPanel extends HttpServlet {
	public static final String VIEW = "/admin/adminPanel.jsp";
	private DAOUser daoAdmin;
	private DAOUser daoUser;
	private DAOTeacher daoTeacher;
	private ArrayList<Teacher> teachers = new ArrayList<>();
	
	public void init() {
		this.daoTeacher = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoTeacher();
		this.daoAdmin = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoAdmin();
		this.daoUser = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoUser();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		teachers = ((DAOImplUser)daoUser).listTeachers(daoTeacher);
		request.setAttribute("teachers", teachers);
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//HttpSession session = request.getSession(false);
		
		//Teacher teacher = daoTeacher.findTeacher(((User)(session.getAttribute("userSession"))).getEmail());//useless?
		
		if(request.getParameter("addTeacher") != null) {
			CreateTeacherForm createForm = new CreateTeacherForm(daoTeacher);
			if(daoTeacher.findTeacher(generateEmail(createForm.getTeacher(request).getFirstname(), createForm.getTeacher(request).getLastname())) == null) {
			daoTeacher.createTeacher(createForm.getTeacher(request));
			}
		}
		
		if(request.getParameter("deleteTeacher") != null) {
			DeleteTeacherForm deleteForm = new DeleteTeacherForm(daoTeacher);
			daoTeacher.deleteTeacher(deleteForm.selectTeacherToDelete(request));
		}
		
		if(request.getParameter("addAdmin") != null) {
			CreateAdminForm createForm = new CreateAdminForm(daoAdmin);
			daoAdmin.create(createForm.getAdmin(request));
		}
		
		if(request.getParameter("deleteAdmin") != null) {
			DeleteAdminForm deleteForm = new DeleteAdminForm(daoAdmin);
			daoAdmin.delete(deleteForm.selectAdminToDelet(request));
		}

		teachers = ((DAOImplUser)daoUser).listTeachers(daoTeacher);
		request.setAttribute("teachers", teachers);
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
		//response.sendRedirect(request.getContextPath()+"/admin");
	}
}
