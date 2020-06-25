package mx.com.teclo.lectorhuella.base.app;

import org.apache.log4j.Logger;

import mx.com.teclo.DigitalPersonMain;

/**
 * Obtiene las solicitudes y lanza el evento para enviar al front los mensajes
 * generados dentro del lector
 * 
 * @author beatriz.orosio@unitis.com.mx
 *
 */
public class LectorHuellasComponent {
	private static Logger LOGGER = Logger.getLogger(LectorHuellasComponent.class);
	private static DigitalPersonMain lector;
	private DespachadorSolicitud despachador;
	private EmisorMensajes emisor;
	private LectorHuellasGUI gui;
	private LectorHuellasPing ping;

	public LectorHuellasComponent() {
		lector = new DigitalPersonMain();
		despachador = new DespachadorSolicitud();
		emisor = new EmisorMensajes();
		ping = new LectorHuellasPing();
		if (AppConfiguracion.getInstance().getAppVisible()) {
			gui = new LectorHuellasGUI();
			gui.mostrarInterfaz();
			lector.addObserver(gui);
		}
		lector.addObserver(despachador);
		lector.addObserver(emisor);
	}

	public void iniciar() {
		LOGGER.info("INICIANDO LectorHuellasComponent ---------");
		lector.iniciar();
		ping.iniciar();
	}

}
