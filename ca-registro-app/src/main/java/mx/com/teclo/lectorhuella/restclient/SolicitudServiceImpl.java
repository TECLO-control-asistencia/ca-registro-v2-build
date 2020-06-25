package mx.com.teclo.lectorhuella.restclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.teclo.digitalperson.business.vo.SolicitudLecturaVO;
import mx.com.teclo.lectorhuella.base.inicializar.RestClient;

@Service
public class SolicitudServiceImpl implements SolicitudService {

	@Autowired
	private RestClient restClient;

	@Override
	public SolicitudLecturaVO getUltimaSolicitud(Long idLector) {
		return restClient.getUltimaSolicitud(idLector);
	}

}
