package mx.com.teclo.lectorhuella.base.app;

import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;

import mx.com.teclo.DigitalPersonMain;
import mx.com.teclo.digitalperson.business.core.LectorHuellas;
import mx.com.teclo.digitalperson.business.vo.SolicitudLecturaVO;
import mx.com.teclo.lectorhuella.base.inicializar.RestClient;

public class DespachadorSolicitud implements Observer {
	private static Logger LOGGER = Logger.getLogger(DespachadorSolicitud.class);
	private RestClient restClient;
	private DigitalPersonMain lector;
	private Long idLector;
	private boolean detener = false;

	public DespachadorSolicitud() {
		LOGGER.info("Iniciando Despachador Solicitud");
		this.idLector = AppConfiguracion.getInstance().getIdLector();
		restClient = new RestClient();
		restClient.setUrlRestApi(AppConfiguracion.getInstance().getUrl());
		restClient.setToken(AppConfiguracion.getInstance().getToken());

	}

	public SolicitudLecturaVO getUltimaSolicitud() {
		return restClient.getUltimaSolicitud(idLector);
	}

	@Override
	public void update(Observable o, Object arg) {
		lector = (DigitalPersonMain) o;
		if (!lector.getEstado().isOcupado() && lector.getEstado().getMensaje().equals(LectorHuellas.MSG_TRIGGER_PARA_BUSCAR_SOLICITUD)) {
			lector.procesar(getUltimaSolicitud());
		}

	}

	public void detener() {
		detener = true;
	}

	private boolean continuar() {
		return this.detener == false;
	}

	public void despachar() {
		while (continuar()) {
			try {
				Thread.sleep(1L * 1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lector.procesar(getUltimaSolicitud());
		}
	}

}
