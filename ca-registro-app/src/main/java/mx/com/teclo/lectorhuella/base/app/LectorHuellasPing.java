package mx.com.teclo.lectorhuella.base.app;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import mx.com.teclo.lectorhuella.base.inicializar.RestClient;

public class LectorHuellasPing {
	private static Logger LOGGER = Logger.getLogger(EmisorMensajes.class);
	private final ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
	private RestClient restClient;
	private Long idLector;

	public LectorHuellasPing() {
		LOGGER.info("Iniciando LectorHuellasPing");
		//restClient = (RestClient) BeanLocator.getService("restClient");
		restClient = new RestClient();
		restClient.setUrlRestApi(AppConfiguracion.getInstance().getUrl());
		restClient.setToken(AppConfiguracion.getInstance().getToken());
		this.idLector = AppConfiguracion.getInstance().getIdLector();
	}

	public void iniciar() {
		exec.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					restClient.sendPing(idLector);
				} catch (Exception e) {
					LOGGER.error("No fue posible enviar ping del lector #" + idLector);
				}

			}
		}, 0, 1, TimeUnit.MINUTES);
	}

}
