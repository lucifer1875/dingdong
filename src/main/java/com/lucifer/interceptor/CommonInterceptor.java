package com.lucifer.interceptor;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CommonInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = request.getRequestURI();

//		String context = request.getContextPath();

		return true;
	}
	
	private void printJson(HttpServletResponse response) {
    	response.setContentType("application/json;charset=UTF-8");
    	try {
			PrintWriter out = response.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			out.print("");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
