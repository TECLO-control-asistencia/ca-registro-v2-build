package mx.com.teclo.lectorhuella.base.app;

import org.apache.log4j.Logger;

/**
 * Aplicación de escritorio para leer huellas
 * 
 * @author beatriz.orosio@unitis.com.mx
 *
 */
public class App {
	private static Logger LOGGER = Logger.getLogger(App.class);

	public void start() {
		LOGGER.info("____________________iniciando_________________________");
		LectorHuellasComponent despachadorComponente = new LectorHuellasComponent();
		despachadorComponente.iniciar();
	}

}
