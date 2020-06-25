package mx.com.teclo.digitalperson.business.vo;

import java.util.Arrays;

public class EstadoVO {
	private boolean conectado = false;
	private boolean ocupado = false;
	private String mensaje;
	private byte[] imagen;
	private byte[] template;
	private SolicitudLecturaVO solicitud;

	public EstadoVO() {

	}

	
	/**
	 * Para actualizar más de cuatro parámetros
	 * @param estado
	 */
	public EstadoVO(EstadoVO estado) {
		this.conectado = estado.conectado;
		this.ocupado = estado.ocupado;
		this.mensaje = estado.mensaje;
		this.imagen = estado.imagen;
		this.template = estado.template;
		this.solicitud = estado.solicitud;

	}
	
	/**
	 * Para actualizar imagen y/o template
	 * @param mensaje
	 * @param imagen
	 * @param template
	 * @param edoAnterior
	 */
	public EstadoVO(String mensaje, byte[] imagen, byte[] template, EstadoVO edoAnterior) {
		this.conectado = edoAnterior.conectado;
		this.ocupado = edoAnterior.ocupado;
		this.mensaje = mensaje;
		this.imagen = imagen;
		this.template = template;
		this.solicitud = edoAnterior.solicitud;

	}
	
	/**
	 * Para actualizar el estado de la conexion del lector al equipo de computo
	 * 
	 * @param mensaje
	 * @param isConectado
	 * @param estado
	 */
	public EstadoVO(String mensaje, boolean isConectado, EstadoVO estado) {
		this.conectado = isConectado;
		this.ocupado = estado.ocupado;
		this.mensaje = mensaje;
		this.imagen = estado.imagen;
		this.template = estado.template;
		this.solicitud = estado.solicitud;
	}
	
	/**
	 * Para actualizar mensaje
	 * @param mensaje
	 * @param estado
	 */
	public EstadoVO(final String mensaje, EstadoVO estado) {
		this.conectado = estado.conectado;
		this.ocupado = estado.ocupado;
		this.mensaje = mensaje;
		this.imagen = estado.imagen;
		this.template = estado.template;
		this.solicitud = estado.solicitud;
	}

	public boolean isConectado() {
		return conectado;
	}

	public void setConectado(boolean conectado) {
		this.conectado = conectado;
	}

	public boolean isOcupado() {
		return ocupado;
	}

	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public byte[] getTemplate() {
		return template;
	}

	public void setTemplate(byte[] template) {
		this.template = template;
	}

	public SolicitudLecturaVO getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(SolicitudLecturaVO solicitud) {
		this.solicitud = solicitud;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (conectado ? 1231 : 1237);
		result = prime * result + Arrays.hashCode(imagen);
		result = prime * result + ((mensaje == null) ? 0 : mensaje.hashCode());
		result = prime * result + (ocupado ? 1231 : 1237);
		result = prime * result + ((solicitud == null) ? 0 : solicitud.hashCode());
		result = prime * result + Arrays.hashCode(template);
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
		EstadoVO other = (EstadoVO) obj;
		if (conectado != other.conectado)
			return false;
		if (!Arrays.equals(imagen, other.imagen))
			return false;
		if (mensaje == null) {
			if (other.mensaje != null)
				return false;
		} else if (!mensaje.equals(other.mensaje))
			return false;
		if (ocupado != other.ocupado)
			return false;
		if (solicitud == null) {
			if (other.solicitud != null)
				return false;
		} else if (!solicitud.equals(other.solicitud))
			return false;
		if (!Arrays.equals(template, other.template))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EstadoVO [conectado=" + conectado + ", ocupado=" + ocupado + ", "
				+ (mensaje != null ? "mensaje=" + mensaje + ", " : "")
				+ (solicitud != null && solicitud.getIdCanal() != null ? "canal=" + solicitud.getIdCanal() : "") + "]";
	}

}
