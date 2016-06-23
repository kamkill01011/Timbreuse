package ch.cpnv.timbreuse.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet qui affiche la page erreur
 * @author Mathieu.JEE Kamil.AMRANI
 *
 */
@WebServlet("/error")
public class ErrorPage extends HttpServlet {
	public static final String VIEW = "/WEB-INF/errorPage.jsp";
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
}
