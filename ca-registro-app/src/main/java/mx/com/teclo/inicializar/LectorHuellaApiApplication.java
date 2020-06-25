package mx.com.teclo.inicializar;


import java.util.Collections;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import mx.com.teclo.lectorhuella.base.app.AppConfiguracion;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
@ComponentScan( basePackages = {"mx.com.teclo.lectorhuella.base.inicializar", "mx.com.teclo.inicializar"})
public class LectorHuellaApiApplication{

	private static Logger LOGGER = Logger.getLogger(LectorHuellaApiApplication.class);
	private static int ARG_0_LECTOR = 0;
	private static int ARG_1_URL = 1;
	private static int ARG_2_VISIBLE = 2;
	private static int ARG_3_TOKEN = 3;
	private static int ARG_4_PORT = 4;
	
	public static void main(String[] args) {
		AppConfiguracion.configInstance(
				new Long(args[ARG_0_LECTOR]), 
				args[ARG_1_URL], 
				new Boolean(args[ARG_2_VISIBLE]),
				args[ARG_3_TOKEN]);
		LOGGER.info("-------- Iniciando App ID " + args[ARG_0_LECTOR] + "----------------");
		SpringApplication app = new SpringApplication(LectorHuellaApiApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", (Object) args[ARG_4_PORT]));
        app.run(args);;
	}
}
