package mx.com.teclo.lectorhuella.base.app;

import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;

import mx.com.teclo.DigitalPersonMain;
import mx.com.teclo.lectorhuella.base.inicializar.RestClient;

public class EmisorMensajes implements Observer {
	private static Logger LOGGER = Logger.getLogger(EmisorMensajes.class);
	private DigitalPersonMain dispositivo;
	private RestClient restClient;
	
	public EmisorMensajes() {
		LOGGER.info("Iniciando EmisorMensajes");
		//restClient =  new RestClient(); //(RestClient) BeanLocator.getService("restClient");
		restClient =  new RestClient();
		restClient.setUrlRestApi(AppConfiguracion.getInstance().getUrl());
		restClient.setToken(AppConfiguracion.getInstance().getToken());
	}

	@Override
	public void update(Observable o, Object arg) {
		dispositivo = (DigitalPersonMain) o;
		if (dispositivo.getEstado() != null)
			if (dispositivo.getEstado().getSolicitud() != null)
				if (dispositivo.getEstado().getSolicitud().getIdCanal() != null)
					restClient.sendMessage(dispositivo.getEstado());
	}

}
