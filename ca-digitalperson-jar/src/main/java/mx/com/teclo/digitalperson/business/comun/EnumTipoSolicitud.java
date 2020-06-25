package mx.com.teclo.digitalperson.business.comun;

/**
 * Tipos de solicitud de lecturas de huella
 * 
 * @author beatriz.orosio@unitis.com.mx
 * 
 *
 */
public enum EnumTipoSolicitud {
	VERIFICACION(0), ENROLAMIENTO(1);
	private int id;

	EnumTipoSolicitud(final int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static EnumTipoSolicitud getById(int id) {
		for (EnumTipoSolicitud e : values()) {
			if (e.id == id)
				return e;
		}
		throw new IllegalArgumentException("No existe tipo de solicitud");
	}
}
