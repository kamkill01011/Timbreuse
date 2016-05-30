package ch.cpnv.timbreuse.servlets;

import static ch.cpnv.timbreuse.dao.DAOUtility.currentDate;
import static ch.cpnv.timbreuse.dao.DAOUtility.currentTime;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.cpnv.timbreuse.beans.User;
import ch.cpnv.timbreuse.mathTime.SecondsPastMidnight;

/**
 * Servlet de déconnexion
 *
 */
@WebServlet("/logout")
public class Logout extends HttpServlet {
	public static final String VIEW_CONNECTION = "/connection";
	public static final String USER_SESSION_ATT ="userSession";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Récupération et destruction de la session en cours */
        HttpSession session = request.getSession();
        
        User user = (User) session.getAttribute(USER_SESSION_ATT);
		System.out.println(user.getUsername() + " logged out");
        session.invalidate();
        
        response.sendRedirect(request.getContextPath() + VIEW_CONNECTION);
    }
}