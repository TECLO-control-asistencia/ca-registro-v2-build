package mx.com.teclo.digitalperson.business.comun;

/**
 * C\u00F3digos de respuesta usados por el lector de huellas
 * 
 * @author beatriz.orosio@unitis.com.mx
 *
 */
public enum EnumCodigoRespuesta {

	BIOMETRICO_CONECTADO("BMC01"), BIOMETRICO_DESCONECTADO("BMD02"), LECTURA_CORRECTA("LCC03"),
	LECTURA_INCORRECTA("LCI04"), CREACION_CORRECTA_HUELLAS("CCH05"), CREACION_INCORRECTA_HUELLAS("CIH06"),
	LECTURA_POR_ESCANEO("LPE06"), VERIFICACION_INICIADA("VFI07");

	private String idCodigo;

	private EnumCodigoRespuesta(String idCodigo) {
		this.idCodigo = idCodigo;

	}

	/**
	 * @return the procesoId
	 */
	public String getIdCodigo() {
		return idCodigo;
	}

	/**
	 * @param procesoId the procesoId to set
	 */
	public void setIdCodigo(String idCodigo) {
		this.idCodigo = idCodigo;
	}

	public static EnumCodigoRespuesta getArchivoTipo(String x) {
		for (EnumCodigoRespuesta currentType : EnumCodigoRespuesta.values()) {
			if (x.equals(currentType.getIdCodigo())) {
				return currentType;
			}
		}
		return null;
	}
}
