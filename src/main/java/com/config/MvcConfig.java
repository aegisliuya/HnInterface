package com.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import wuhao.tools.spring.DateConverter;
import wuhao.tools.spring.FormModelMethodArgumentResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 41401 on 2016/4/17.
 */
@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan(basePackages = "com.controller")
public class MvcConfig extends WebMvcConfigurationSupport {
	private static final Logger logger = Logger
			.getLogger(MvcConfig.class);




	@Bean
	public MessageSource messageSource(){
		ReloadableResourceBundleMessageSource messageSource =
				new ReloadableResourceBundleMessageSource();
		messageSource.setCacheSeconds(-1);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setBasename("/i18n/errors");
		return messageSource;
	}

	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean(){
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}

	/**
	 * 注册试图处理器
	 * @return
	 */
	@Bean(name = "viewResolver")
	public ViewResolver viewResolver() {
		logger.info("ViewResolver");
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/jsp/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(2);
		return viewResolver;
	}

	/**
	 * 描述 : <注册servlet适配器>.
	 * <只需要在自定义的servlet上用@Controller("映射路径")标注即可>
	 *
	 * @return
	 */
	@Bean
	public HandlerAdapter servletHandlerAdapter() {
		logger.info("HandlerAdapter");
		return new SimpleServletHandlerAdapter();
	}

	/**
	 * 描述 : <本地化拦截器>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 *
	 * @return
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		logger.info("LocaleChangeInterceptor");
		return new LocaleChangeInterceptor();
	}

	/**
	 * 描述 : <基于cookie的本地化资源处理器>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 *
	 * @return
	 */
	@Bean(name = "localeResolver")
	public CookieLocaleResolver cookieLocaleResolver() {
		return new CookieLocaleResolver();
	}

	@Bean(name = "defaultJsonView")
	public MappingJackson2JsonView jsonViewResolver() {
		return new MappingJackson2JsonView();
	}


	/**
	 * 描述 : <添加拦截器>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 *
	 * @param registry
	 */
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		logger.info("addInterceptors start");
		logger.info("addInterceptors end");
	}

	/**
	 * 描述 : <HandlerMapping需要显示声明，否则不能注册资源访问处理器>. <br>
	 * <p>
	 * <这个比较奇怪,理论上应该是不需要的>
	 * </p>
	 *
	 * @return
	 */
	@Bean
	public HandlerMapping resourceHandlerMapping() {
		logger.info("HandlerMapping");
		return super.resourceHandlerMapping();
	}

	/**
	 * 描述 : <资源访问处理器>. <br>
	 * <p>
	 * <可以在jsp中使用/static/**的方式访问/WEB-INF/static/下的内容>
	 * </p>
	 *
	 * @param registry
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		logger.info("addResourceHandlers");
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
		registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");
		registry.addResourceHandler("/homepage_data/**").addResourceLocations("/homepage_data/");
	}

	/**
	 * 描述 : <文件上传处理器>. <br>
	 *
	 * @return
	 */
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver commonsMultipartResolver() {
		logger.info("CommonsMultipartResolver");
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setMaxInMemorySize(Integer.MAX_VALUE);
		resolver.setDefaultEncoding("UTF-8");
		return resolver;
	}


	/**
	 * 描述 : <RequestMappingHandlerAdapter需要显示声明，否则不能注册通用属性编辑器>. <br>
	 * <p>
	 * <这个比较奇怪,理论上应该是不需要的>
	 * </p>
	 *
	 * @return
	 */
	@Bean
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		logger.info("RequestMappingHandlerAdapter");
		RequestMappingHandlerAdapter adapter = super.requestMappingHandlerAdapter();
		List list = new ArrayList<>();
		list.add(new FormModelMethodArgumentResolver());
		adapter.setInitBinderArgumentResolvers(list);
		return adapter;
	}

	protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new FormModelMethodArgumentResolver());
	}


	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		converter.setObjectMapper(objectMapper);
		converters.add(converter);
	}

	@Bean
	public FormattingConversionService mvcConversionService() {
		FormattingConversionService service = super.mvcConversionService();
		service.addConverter(new DateConverter());
		return service;
	}

}
