package mx.com.teclo.digitalperson.business.core;

import java.util.Observable;
import java.util.Observer;

import mx.com.teclo.DigitalPersonMain;
import mx.com.teclo.digitalperson.business.vo.EstadoVO;

/**
 * Recibe cualquier cambio dentro del lector y lo exterioriza
 * 
 * @author beatriz.orosio@unitis.com.mx
 *
 */
public class ListenerEstadoLector implements Observer {
	private LectorHuellas lector;
	private DigitalPersonMain digital;

	public ListenerEstadoLector(DigitalPersonMain digital) {
		this.digital = digital;
	}

	@Override
	public void update(Observable o, Object arg) {
		lector = (LectorHuellas) o;
		EstadoVO estado = new EstadoVO();
		estado.setConectado(lector.getEstado().isConectado());
		estado.setOcupado(lector.getEstado().isOcupado());
		estado.setMensaje(lector.getEstado().getMensaje());
		estado.setImagen(lector.getEstado().getImagen());
		estado.setTemplate(lector.getEstado().getTemplate());
		estado.setSolicitud(lector.getEstado().getSolicitud());
		digital.setEstado(estado);

	}

}
