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

/**
 * Servlet pour les administrateurs qui leur permet de gérer les autres administrateurs et les enseigants
 * @author Mathieu.JEE Kamil.AMRANI
 *
 */
@WebServlet("/admin")
public class AdminPanel extends HttpServlet {
	public static final String VIEW = "/admin/adminPanel.jsp";
	public static final String VIEW_ERR = "/error";
	private DAOUser daoAdmin;
	private DAOUser daoUser;
	private DAOTeacher daoTeacher;
	private ArrayList<Teacher> teachers = new ArrayList<>();

	@Override
	public void init() {
		this.daoTeacher = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoTeacher();
		this.daoAdmin = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoAdmin();
		this.daoUser = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoUser();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		teachers = ((DAOImplUser)daoUser).listTeachers(daoTeacher);
		request.setAttribute("teachers", teachers);
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if(request.getParameter("addTeacher") != null) {
				CreateTeacherForm createForm = new CreateTeacherForm();
				if(daoTeacher.findTeacher(generateEmail(createForm.getTeacher(request).getFirstname(), createForm.getTeacher(request).getLastname())) == null) {
					daoTeacher.createTeacher(createForm.getTeacher(request));
				}
			}

			if(request.getParameter("deleteTeacher") != null) {
				DeleteTeacherForm deleteForm = new DeleteTeacherForm();
				daoTeacher.deleteTeacher(deleteForm.selectTeacherToDelete(request));
			}
			for (int i = 0; i < teachers.size(); i++) {
				if(request.getParameter("" + teachers.get(i).getId()) != null) {
					daoTeacher.setNewClasses(teachers.get(i), request.getParameter("classes" + teachers.get(i).getId()));
					break;
				}
			}

			if(request.getParameter("addAdmin") != null) {
				CreateAdminForm createForm = new CreateAdminForm();
				daoAdmin.create(createForm.getAdmin(request));
			}

			if(request.getParameter("deleteAdmin") != null) {
				DeleteAdminForm deleteForm = new DeleteAdminForm();
				daoAdmin.delete(deleteForm.selectAdminToDelet(request));
			}

			teachers = ((DAOImplUser)daoUser).listTeachers(daoTeacher);
			request.setAttribute("teachers", teachers);
			this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
		} catch(Exception e) {
			response.sendRedirect(request.getContextPath() + VIEW_ERR);
		}
	}
}
