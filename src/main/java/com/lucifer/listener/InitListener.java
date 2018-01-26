package com.lucifer.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 应用初始化类
 * @author yhl
 *
 */
public class InitListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {//在此进行应用的初始化操作
		//获取spring applicationContext，可以进行持久化的初始化操作，如
//		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		
		//初始化系统版本
		setVersion(event.getServletContext());
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	/**
	 * 生成系统版本号
	 * @param servletContext
	 */
	public void setVersion(ServletContext servletContext){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			servletContext.setAttribute("version",sf.parse(sf.format(new Date())).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			servletContext.setAttribute("version",new Date().getTime());
		}
	}
	
}
