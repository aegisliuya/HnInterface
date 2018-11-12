package com.config;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SystemConfig {

	public static Log log = LogFactory.getLog(SystemConfig.class);

	public static boolean debug = true;
	public static ApplicationContext context;


	public static void configInit() {
		try {
			if (context == null) {
//				context = new ClassPathXmlApplicationContext(
//						"/applicationContext.xml");
				context = new AnnotationConfigApplicationContext(RootConfig.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("config初始化完成");
	}


	public static void main(String[] args) {
		SystemConfig.configInit();
//		SockpuppetDAO sockpuppetDAO = SystemConfig.context.getBean(SockpuppetDAO.class);
//		List<Sockpuppet> list = sockpuppetDAO.find(Inquiry.forClass(Sockpuppet.class));
//		System.out.println(list);
//		for (SubInfo subInfo : list) {
//			long s1 = System.currentTimeMillis();
//			Article article = new Article(subInfo, ArticleType.MainArticle);
//			long s2 = System.currentTimeMillis();
//			System.out.println(s2 - s1);
//		}
	}
}
