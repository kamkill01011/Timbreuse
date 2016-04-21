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

public class RestrictionFilter implements Filter{
	public static final String CONNECTION_ACCESS = "/connection.jsp";
	public static final String USER_SESSION_ATT ="userSession";
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		String path = request.getRequestURI().substring(request.getContextPath().length());
		if(path.startsWith("/teacher")) {
			chain.doFilter(request, response);
			return;
		}
		
		HttpSession session = request.getSession();
		
		//Si l'user n'existe pas dans la session en cours, alors l'user n'est pas connecté
		if(session.getAttribute(USER_SESSION_ATT) == null) {
			request.getRequestDispatcher(CONNECTION_ACCESS).forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
	}
}
