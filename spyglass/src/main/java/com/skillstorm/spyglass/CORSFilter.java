//package com.skillstorm.spyglass;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.tomcat.util.file.ConfigurationSource;
//import org.springframework.stereotype.Component;
//
//@Component
////@WebFilter(urlPatterns="/*")//Apply to every page
//public class CORSFilter implements Filter {
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		HttpServletResponse resp = (HttpServletResponse) response;
//		HttpServletRequest req = (HttpServletRequest) request;
//		resp.addHeader("Access-Control-Allow-Origin", "*"); //Allows all domains.
//		resp.addHeader("Access-Control-Allow-Methods", "*"); //Allows all HTTP methods.
//		resp.addHeader("Access-Control-Allows-Credentials", "true");
//		resp.addHeader("Access-Control-Allow-Headers", "*"); //Allows all headers.
//		if (req.getMethod().equals("OPTIONS")) {
//			resp.setStatus(202);//202 = Accepted
//		}
//		chain.doFilter(req, resp);
//	}
//	
//	@Override
//	public void init(FilterConfig filterConfig) {
//		
//	}
//	
//	@Override
//	public void destroy() {
//		
//	}
//
//}
