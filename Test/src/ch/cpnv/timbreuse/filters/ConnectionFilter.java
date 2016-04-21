package ch.cpnv.timbreuse.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.cpnv.timbreuse.beans.User;

public class ConnectionFilter implements Filter {
	public static final String VIEW_CONNECTION = "/WEB-INF/connection.jsp";
	public static final String VIEW_STUDENT = "/student/info.jsp";
	public static final String VIEW_TEACHER = "/teacher/manageStudents.jsp";
	public static final String VIEW_ADMIN = "/admin/connection.jsp";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        //ressources qui ne doivent pas être filtrées (css, ...)
        /*String path = request.getRequestURI().substring( request.getContextPath().length() );
        if(path.startsWith("/inc")) {
            chain.doFilter(request, response);
            return;
        }*/

        //Récupération de la session depuis la requête
        HttpSession session = request.getSession();
        
        if(session.getAttribute(ATT_SESSION_USER) == null) {
            request.getRequestDispatcher(VIEW_CONNECTION).forward(request, response);
        } else {
        	User user = (User) session.getAttribute(ATT_SESSION_USER);
        	if(session.getAttribute(ATT_SESSION_USER) == null) {
                request.getRequestDispatcher(VIEW_CONNECTION).forward(request, response);
            } else if(user.getPermissionLevel() == 3){
            	request.getRequestDispatcher(VIEW_STUDENT).forward(request, response);
            } else if(user.getPermissionLevel() == 2){
            	request.getRequestDispatcher(VIEW_TEACHER).forward(request, response);
            } else if(user.getPermissionLevel() == 1){
            	request.getRequestDispatcher(VIEW_ADMIN).forward(request, response);
            } else {
            	request.getRequestDispatcher(VIEW_CONNECTION).forward(request, response);
            }
        }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
}
