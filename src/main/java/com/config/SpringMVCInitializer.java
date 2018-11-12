package com.config;

import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import wuhao.tools.reader.PathUtil;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.io.File;

/*
 * 所有实现了WebApplicationInitializer接口的类都会在容器启动时自动被加载运行，用@Order注解设定加载顺序
 * 这是servlet3.0+后加入的特性，web.xml中可以不需要配置内容，都硬编码到WebApplicationInitializer的实现类中
 */
@Order(3)
//spring DispatcherServlet的配置,其它servlet和监听器等需要额外声明，用@Order注解设定启动顺序
public class SpringMVCInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {



	public void onStartup(ServletContext servletContext) throws ServletException{
		super.onStartup(servletContext);
	}

	 /*
	  * DispatcherServlet的映射路径
	  */
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /*
	  * 应用上下文，除web部分
	  */
    protected Class[] getRootConfigClasses() {

        String currentPath = PathUtil.getClassesPath();
        String sep = File.separator;
        if (!currentPath.endsWith(sep)) {
            currentPath += sep;
        }
        String LOG_PATH=PathUtil.getProjectRoot();
        System.setProperty("LOG_PATH", LOG_PATH);
        System.setProperty("APP_PATH", currentPath);
        DOMConfigurator.configure(currentPath + "/log4j.xml");
        return new Class[] {
		        RootConfig.class
        };
    }

    /*
	  * web上下文
	  */
	@Override
    protected Class[] getServletConfigClasses() {

		return new Class[] {MvcConfig.class};
    }

    /*
	  * 注册过滤器，映射路径与DispatcherServlet一致，路径不一致的过滤器需要注册到另外的WebApplicationInitializer中
	  */
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return new Filter[] {characterEncodingFilter};
    }

    /*
	  * 可以注册DispatcherServlet的初始化参数，等等
	  */
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {

    }


	/*
     * 创建一个可以在非spring管理bean中获得spring管理的相关bean的对象：CP_SpringContext.getBean(String beanName)
     */
	@Override
	protected WebApplicationContext createRootApplicationContext() {
		WebApplicationContext ctx = super.createRootApplicationContext();
		return ctx;
	}
}
