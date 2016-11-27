package com.ibm.app.web.frmwk;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class Loginfilter
 */
@WebFilter(dispatcherTypes = {
				DispatcherType.REQUEST, 
				DispatcherType.FORWARD, 
				DispatcherType.INCLUDE, 
				DispatcherType.ERROR
		}
					, servletNames = { "MobileFrameworkDispatcher" },urlPatterns={"*.wss"})
public class Loginfilter implements Filter {

    /**
     * Default constructor. 
     */
    public Loginfilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		boolean redirectToLoginPage = true;
		chain.doFilter(request, response);
		
		/*if(request instanceof HttpServletRequest)
		{
			HttpServletRequest servletReq = (HttpServletRequest)request;
			String userRole = (String)servletReq.getSession().getAttribute("user_role");
			if(userRole!=null)
			{
				chain.doFilter(request, response);
				redirectToLoginPage = false;
			}
			else
			{
				//If login is called 
				String requestURI = servletReq.getRequestURI();
				int position = requestURI.lastIndexOf("/");
				String actionName = requestURI.substring(position + 1);
				if(actionName.equalsIgnoreCase("login.wss"))
				{
					chain.doFilter(request, response);
					redirectToLoginPage = false;
				}
			}
		}
		// pass the request along the filter chain
		if(redirectToLoginPage)
		{
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}*/
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
