package mx.com.teclo.digitalperson.business.vo;

import java.util.List;

public class SolicitudLecturaVO {
	private Long idSolicitud;
	private Long idEmpleado;
	private int tipoOperacion;
	private boolean respondido;
	private Long tiempoAtencion;
	private String idCanal;
	private List<HuellasVO> huellas;

	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public int getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(int tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public boolean isRespondido() {
		return respondido;
	}

	public void setRespondido(boolean respondido) {
		this.respondido = respondido;
	}

	public Long getTiempoAtencion() {
		return tiempoAtencion;
	}

	public void setTiempoAtencion(Long tiempoAtencion) {
		this.tiempoAtencion = tiempoAtencion;
	}

	
	public String getIdCanal() {
		return idCanal;
	}

	public void setIdCanal(String idCanal) {
		this.idCanal = idCanal;
	}

	public List<HuellasVO> getHuellas() {
		return huellas;
	}

	public void setHuellas(List<HuellasVO> huellas) {
		this.huellas = huellas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idSolicitud == null) ? 0 : idSolicitud.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SolicitudLecturaVO other = (SolicitudLecturaVO) obj;
		if (idSolicitud == null) {
			if (other.idSolicitud != null)
				return false;
		} else if (!idSolicitud.equals(other.idSolicitud))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SolicitudLecturaVO [idSolicitud=" + idSolicitud + ", idEmpleado=" + idEmpleado + ", "
				+ (idCanal != null ? "idCanal=" + idCanal + ", " : "")	
				+ "]";
	}

}
