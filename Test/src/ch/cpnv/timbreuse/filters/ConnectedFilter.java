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

/**
 * Redirige les visiteurs (non-connectés) vers la page de connexion sauf les page .css
 *
 */
public class ConnectedFilter implements Filter {
	public static final String VIEW_CONNECTION = "/connection";
    public static final String ATT_SESSION_USER = "userSession";

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        //Récupération de la session depuis la requête
        HttpSession session = request.getSession();
        
        if(session.getAttribute(ATT_SESSION_USER) != null || request.getRequestURI().endsWith(".css")) {
        	chain.doFilter(request, response);
        } else {
        	request.getRequestDispatcher(VIEW_CONNECTION).forward(request, response);
        }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
}
