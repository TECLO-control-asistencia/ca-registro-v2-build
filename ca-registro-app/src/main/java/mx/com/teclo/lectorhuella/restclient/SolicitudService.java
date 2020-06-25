package mx.com.teclo.lectorhuella.restclient;

import mx.com.teclo.digitalperson.business.vo.SolicitudLecturaVO;

public interface SolicitudService {
	SolicitudLecturaVO getUltimaSolicitud(Long idLector);

}
