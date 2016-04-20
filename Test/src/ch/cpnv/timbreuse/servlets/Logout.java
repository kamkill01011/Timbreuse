package ch.cpnv.timbreuse.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Logout extends HttpServlet {
    public static final String URL_REDIRECTION = "http://www.cpnv.ch";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Récupération et destruction de la session en cours */
    	System.out.println("SA PASSE");
        HttpSession session = request.getSession();
        session.invalidate();
        /* Redirection vers le Site du Zéro ! */
        response.sendRedirect(URL_REDIRECTION);
    }
}