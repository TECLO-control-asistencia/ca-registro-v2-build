package mx.com.teclo.lectorhuella.base.inicializar;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import mx.com.teclo.lectorhuella.base.app.App;

@Component
public class InitializeComponent {
	private static Logger LOGGER = Logger.getLogger(InitializeComponent.class);	
	
	@PostConstruct
	public void inicializar() {
		LOGGER.info("-------------------INICIANDO PostConstruct----------------------------");
		App app = new App();
		app.start();
	}
	 
	
}
