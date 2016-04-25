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
	public static final String VIEW_STUDENT = "/student/info";
	public static final String VIEW_TEACHER = "/teacher/managestudents";
	public static final String VIEW_ADMIN = "/admin/connection";
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
        
        String path = request.getRequestURI().substring(request.getContextPath().length());
        if(path.startsWith("/student") || path.startsWith("/teacher") || path.startsWith("/admin")) {
        	if(session.getAttribute(ATT_SESSION_USER) != null) {
            	User user = (User) session.getAttribute(ATT_SESSION_USER);
            	if(user.getPermissionLevel() == 3){
            		response.sendRedirect(request.getContextPath() + VIEW_STUDENT);
                } else if(user.getPermissionLevel() == 2){
                	response.sendRedirect(request.getContextPath() + VIEW_TEACHER);
                } else if(user.getPermissionLevel() == 1){
                	response.sendRedirect(request.getContextPath() + VIEW_ADMIN);
                }
            }
        }
        chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
}
