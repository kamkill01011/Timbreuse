package ch.cpnv.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;

import ch.cpnv.beans.BeanTest;


public class ManageEleve extends HttpServlet {
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		String paramAuteur = request.getParameter("auteur");
		String message = "Transmission de variables : OK !" + paramAuteur;
		
		BeanTest kamil = new BeanTest();
		kamil.setNom("Amrani");
		kamil.setDiff(3);
		
		List<Integer> premiereListe = new ArrayList<Integer>();
		premiereListe.add( 27 );
		premiereListe.add( 12 );
		premiereListe.add( 138 );
		premiereListe.add( 6 );
		
		DateTime dt = new DateTime();
		Integer jourDuMois = dt.getDayOfMonth();
		
		request.setAttribute( "test", message );
		request.setAttribute( "name", kamil );
		request.setAttribute( "liste", premiereListe );
		request.setAttribute( "jour", jourDuMois );
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/manageEleve.jsp").forward( request, response );
	}
}