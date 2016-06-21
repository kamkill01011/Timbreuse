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

/**
 * Redirige les utilisateurs vers leur page d'accueil delon leur niveau de permission
 * @author Mathieu.JEE Kamil.AMRANI
 *
 */
public class ConnectionFilter implements Filter {
	public static final String VIEW_CONNECTION = "/connection";
	public static final String VIEW_STUDENT = "/info";
	public static final String VIEW_TEACHER = "/managestudents";
	public static final String VIEW_ADMIN = "/admin";
	public static final String VIEW_DISPLAY = "/display";
    public static final String ATT_SESSION_USER = "userSession";

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

        if(session.getAttribute(ATT_SESSION_USER) != null) {
        	User user = (User) session.getAttribute(ATT_SESSION_USER);
        	switch (user.getPermissionLevel()) {
        	case 4:
        		response.sendRedirect(request.getContextPath() + VIEW_DISPLAY);
        		break;
			case 3:
        		response.sendRedirect(request.getContextPath() + VIEW_STUDENT);
				break;
			case 2:
        		response.sendRedirect(request.getContextPath() + VIEW_TEACHER);
				break;
			case 1:
        		response.sendRedirect(request.getContextPath() + VIEW_ADMIN);
				break;
			default:
        		response.sendRedirect(request.getContextPath() + VIEW_CONNECTION);
				break;
			}
        } else {
        	response.sendRedirect(request.getContextPath() + VIEW_CONNECTION);
        }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
}
