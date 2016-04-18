package ch.cpnv.timbreuse.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;

import ch.cpnv.timbreuse.beans.Student;


public class Test extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String paramAuteur = request.getParameter("auteur");
		String message = "Transmission de variables : OK !" + paramAuteur;
		
		Student kamil = new Student();
		kamil.setName("Amrani");
		kamil.setDiff(3);
		
		List<Integer> premiereListe = new ArrayList<Integer>();
		premiereListe.add(27);
		premiereListe.add(12);
		premiereListe.add(138);
		premiereListe.add(6);
		
		DateTime dt = new DateTime();
		Integer jourDuMois = dt.getDayOfMonth();
		
		request.setAttribute("test", message);
		request.setAttribute("name", kamil);
		request.setAttribute("liste", premiereListe);
		request.setAttribute("jour", jourDuMois);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/test.jsp").forward(request, response);
	}
}