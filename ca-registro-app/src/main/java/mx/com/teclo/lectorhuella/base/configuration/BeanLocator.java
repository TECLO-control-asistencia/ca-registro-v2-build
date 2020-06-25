package mx.com.teclo.lectorhuella.base.configuration;

import org.springframework.context.ApplicationContext;

public final class BeanLocator {
	 
	private static ApplicationContext appContext;

	private BeanLocator () { }
	
	private static Object lookupService(String serviceBeanName) {
		return appContext.getBean(serviceBeanName);
	}
	
	public static Object getService(String serviceName) {
		appContext = Context.getApplicationContext();
		return lookupService(serviceName);
	}
	
}