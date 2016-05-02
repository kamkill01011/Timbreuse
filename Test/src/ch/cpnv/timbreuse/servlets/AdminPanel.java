package ch.cpnv.timbreuse.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.cpnv.timbreuse.beans.Teacher;
import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.dao.DAOFactory;
import ch.cpnv.timbreuse.dao.DAOTeacher;
import ch.cpnv.timbreuse.dao.DAOUser;
import ch.cpnv.timbreuse.forms.CreateAdminForm;
import ch.cpnv.timbreuse.forms.CreateTeacherForm;
import ch.cpnv.timbreuse.forms.DeleteAdminForm;
import ch.cpnv.timbreuse.forms.DeleteTeacherForm;

@WebServlet("/admin")
public class AdminPanel extends HttpServlet {
	public static final String VIEW = "/admin/adminPanel.jsp";
	private DAOUser daoUser;
	private DAOTeacher daoTeacher;
	
	public void init() {
		this.daoTeacher = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoTeacher();
		this.daoUser = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoAdmin();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//HttpSession session = request.getSession();
		//Teacher teacher = daoTeacher.findTeacher(((User)(session.getAttribute("userSession"))).getEmail());//useless?
		
		if(request.getParameter("addTeacher") != null) {
			CreateTeacherForm createForm = new CreateTeacherForm(daoTeacher);
			daoTeacher.createTeacher(createForm.getTeacher(request));
		}
		
		if(request.getParameter("deleteTeacher") != null) {
			DeleteTeacherForm deleteForm = new DeleteTeacherForm(daoTeacher);
			daoTeacher.deleteTeacher(deleteForm.selectTeacherToDelete(request));
		}
		
		if(request.getParameter("addAdmin") != null) {
			CreateAdminForm createForm = new CreateAdminForm(daoUser);
			daoUser.create(createForm.getAdmin(request));
		}
		
		if(request.getParameter("deleteAdmin") != null) {
			DeleteAdminForm deleteForm = new DeleteAdminForm(daoUser);
			daoUser.delete(deleteForm.selectAdminToDelet(request));
		}
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}
