package ch.cpnv.timbreuse.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class RestrictionFilter implements Filter{
	public static final String PUBLIC_ACCESS = "/connection.jsp";
	public static final String USER_SESSION_ATT ="userSession";
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
	
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		
		
	}

	@Override
	public void destroy() {
		
		
	}
}
