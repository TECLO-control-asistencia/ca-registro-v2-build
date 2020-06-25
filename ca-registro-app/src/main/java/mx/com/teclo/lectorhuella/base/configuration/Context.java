package mx.com.teclo.lectorhuella.base.configuration;

import org.hibernate.validator.internal.metadata.raw.BeanConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public final class Context {
	
	private static ApplicationContext applicationContext;
	
	private Context () {}
	
	public static ApplicationContext getApplicationContext(){
		if (applicationContext == null){
//			applicationContext = new ClassPathXmlApplicationContext("core-spring-context.xml");
			applicationContext = new AnnotationConfigApplicationContext(BeanConfiguration.class);
		}
		return applicationContext;
	}
	
}