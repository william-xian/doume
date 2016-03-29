package com.doume.max.web.filter;

import static com.doume.max.cons.CommonConstant.USER_ME;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.doume.max.entity.User;

public class ForumFilter implements Filter {
	private static final String FILTERED_REQUEST="@@session_context_filtered_request";
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if(request==null){
			return;
		}
		if(request.getAttribute(FILTERED_REQUEST)!=null){
			chain.doFilter(request, response);
		}else{
			request.setAttribute(FILTERED_REQUEST, Boolean.TRUE);
			HttpServletRequest httpRequest = (HttpServletRequest)request;
			User userContext = getSessionUser(httpRequest);
			if(userContext != null || isPublic(httpRequest.getRequestURI())){
				chain.doFilter(request, response);
			}else {
				if(httpRequest.getRequestURI().matches("/doume/admin/.*")){
					request.getRequestDispatcher("/admin/getLogin").forward(request, response);
				}else if(httpRequest.getRequestURI().matches("/doume/business/.*")){
					request.getRequestDispatcher("/business/getLogin").forward(request, response);
				}else if(httpRequest.getRequestURI().matches("/doume/customer/.*")){
					request.getRequestDispatcher("/customer/getLogin").forward(request, response);
				}else{
					chain.doFilter(request, response);
				}
			}
		}
	}
	private static final String CUSTOMER_PUBLIC_URI_PATTERNS[] = new String[]{
		"/doume/admin/getLogin","/doume/business/getLogin","/doume/customer/getLogin",
		"/doume/admin/login","/doume/business/login","/doume/customer/login",
		"/doume/customer/getRegister","/doume/customer/register",
		"/doume/customer/getHome","/doume/customer/search.*","/doume/customer/getMore"
	};
	private static boolean isPublic(String uri){
		for(String u:CUSTOMER_PUBLIC_URI_PATTERNS){
			if(uri.matches(u)){return true;}
		}
		return false;
	}
	private User getSessionUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(USER_ME);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

}
