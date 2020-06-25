package mx.com.teclo;

import java.util.Observable;

import mx.com.teclo.digitalperson.business.core.LectorHuellas;
import mx.com.teclo.digitalperson.business.core.ListenerEstadoLector;
import mx.com.teclo.digitalperson.business.vo.EstadoVO;
import mx.com.teclo.digitalperson.business.vo.SolicitudLecturaVO;

/**
 * Clase principal del lector de huellas
 * 
 * @author beatriz.orosio@unitis.com.mx
 *
 */
public class DigitalPersonMain extends Observable {
	private final LectorHuellas lector;
	private final ListenerEstadoLector listenerEstado;
	private EstadoVO estado;

	public DigitalPersonMain() {
		estado = new EstadoVO();
		listenerEstado = new ListenerEstadoLector(this);
		lector = new LectorHuellas();
		lector.addObserver(listenerEstado);
	}

	public void iniciar() {
		lector.iniciarDispositivo();
	}

	public void procesar(SolicitudLecturaVO solicitud) {
		lector.procesar(solicitud);
	}

	public EstadoVO getEstado() {
		return estado;
	}

	public void setEstado(EstadoVO estado) {
		synchronized (estado) {
			this.estado = estado;
		}
		setChanged();
		notifyObservers();
	}

}
