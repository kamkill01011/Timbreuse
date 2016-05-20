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
import ch.cpnv.timbreuse.dao.DAOHolyday;
import ch.cpnv.timbreuse.dao.DAOTeacher;
import ch.cpnv.timbreuse.dao.DAOUser;
import ch.cpnv.timbreuse.forms.SetHolydaysForm;

@WebServlet("/setholydays")
public class SetHolydays extends HttpServlet {
	public static final String VIEW_SETHOLYDAYS = "/teacher/setHolydays.jsp";
	public static final String CONNECTING = "/connecting.jsp";
	public static final String USER_ATT = "user";
	public static final String FORM_ATT = "form";
	public static final String USER_SESSION_ATT ="userSession";
	private DAOHolyday daoHolyday;

	public void init() {
		this.daoHolyday = ((DAOFactory)getServletContext().getAttribute("daofactory")).getDaoHolyday();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		this.getServletContext().getRequestDispatcher(VIEW_SETHOLYDAYS).forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(request.getParameter("addSingleHolydayButton")!=null) {
			SetHolydaysForm holydaysForm = new SetHolydaysForm(daoHolyday);
			daoHolyday.addHolyday(holydaysForm.getSingleHolyday(request));
		}
		if(request.getParameter("addHolydayGapButton")!=null) {
			SetHolydaysForm holydaysForm = new SetHolydaysForm(daoHolyday);
			daoHolyday.addHolyday(holydaysForm.getHolydaysGap(request));
		}
		
		this.getServletContext().getRequestDispatcher(VIEW_SETHOLYDAYS).forward(request, response);
	}
}
